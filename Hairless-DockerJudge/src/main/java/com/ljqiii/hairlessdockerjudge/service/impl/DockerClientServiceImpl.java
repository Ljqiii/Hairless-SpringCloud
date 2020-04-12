package com.ljqiii.hairlessdockerjudge.service.impl;

import com.ljqiii.hairlesscommon.constants.NameConstants;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.domain.ProblemCode;
import com.ljqiii.hairlessdockerjudge.dao.ProblemMapper;
import com.ljqiii.hairlessdockerjudge.handler.ContainerLogsOutputHandler;
import com.ljqiii.hairlessdockerjudge.interceptor.ContainerInterceptor;
import com.ljqiii.hairlessdockerjudge.service.DockerClientService;
import com.ljqiii.hairlessdockerjudge.service.TarCacheService;
import com.ljqiii.hairlessdockerjudge.utils.ProblemCodeUtil;
import com.ljqiii.hairlessdockerjudge.utils.TarUtil;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerExit;
import com.spotify.docker.client.messages.HostConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class DockerClientServiceImpl implements DockerClientService {

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    TarCacheService tarCacheService;


    public DockerClient getDockerClient() {
        DockerClient docker = new DefaultDockerClient("unix:///var/run/docker.sock");
        return docker;
    }

    @Override
    public Long execProblemWithContainer(String imageName,
                                         String containerName,
                                         int problemId,
                                         ProblemCode problemCode,
                                         String[] cmdList,
                                         ContainerLogsOutputHandler containerLogsOutputHandler,
                                         ContainerInterceptor containerInterceptor,
                                         String problemCodeDir,
                                         String workingDir,
                                         String cacheKey,
                                         boolean removeAfterExit) {

        Problem problem = problemMapper.selectProblemById(problemId);
        Integer memoryLimit = problem.getMemoryLimit();
        String dockerCacheDir = problem.getDockerCacheDir();
        if (!dockerCacheDir.endsWith("/")) {
            dockerCacheDir = dockerCacheDir + "/";
        }
        String redisCacheKey =MessageFormat.format(NameConstants.Docker_CacheDir_Key, String.valueOf(problemId), dockerCacheDir);//redis缓存的key

        ContainerConfig.Builder builder = ContainerConfig.builder();
        builder.image(imageName);
        builder.cmd(cmdList);
        builder.workingDir(workingDir);

        HostConfig.Builder hostConfigBuilder = HostConfig.builder();

        //设置最大内存
        if (memoryLimit != null) {
            hostConfigBuilder.memory(memoryLimit < 4 ? 4 : memoryLimit * 1024L * 1024L);
        }

        builder.hostConfig(hostConfigBuilder.build());

        //拦截Builder
        if (containerInterceptor != null) {
            containerInterceptor.beforeContainerConfigBuild(builder);
        }
        ContainerConfig containerConfig = builder.build();

        DockerClient dockerClient = null;
        String containerId = null;
        LogStream logs = null;
        Thread thread = null;

        AtomicReference<BufferedReader> bufferedReader = new AtomicReference<>();

        try {
            dockerClient = getDockerClient();
            ContainerCreation container = dockerClient.createContainer(containerConfig, containerName);
            containerId = container.id();
            log.info("Docker container created, id: {}", containerId);

            //恢复缓存
            restoreCache(dockerClient, containerId, dockerCacheDir, redisCacheKey);

            dockerClient.copyToContainer(TarUtil.buildEmptyDir(workingDir), containerId, "/");//创建WorkIngDir
            dockerClient.copyToContainer(TarUtil.buildEmptyDir(problemCodeDir), containerId, "/");//创建代码dir


            ByteArrayInputStream problemCodeTarStream = ProblemCodeUtil.convertProblemCodeToInputStream(problemCode, problemCodeDir);//代码tar文件
            dockerClient.copyToContainer(problemCodeTarStream, containerId, problemCodeDir);//复制代码
            log.info("Copy problem code to container");

            //拦截
            if (containerInterceptor != null) containerInterceptor.beforeContainerStart(dockerClient, containerId);

            //开始container
            dockerClient.startContainer(containerId);
            log.info("container [{}] started", containerId);

            createLogThread(dockerClient, containerId, containerLogsOutputHandler, bufferedReader);

            ContainerExit containerExit = dockerClient.waitContainer(containerId);//等待退出
            Long exitCode = containerExit.statusCode();
            log.info("Container [{}] exit with exitCode {}", containerId, exitCode);

            return exitCode;
        } catch (DockerException e) {
            log.error("docker exception", e);
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            log.error("IO exception", e);
            e.printStackTrace();
            return null;
        } finally {
            //设置缓存
            setDirCache(dockerClient, containerId, dockerCacheDir, redisCacheKey);

            //关闭bufferedReader，结束while循环，结束线程
            try {
                bufferedReader.get().close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //删除Container
            if (removeAfterExit) removeContainer(dockerClient, containerId);
        }
    }

    /**
     * 恢复缓存
     *
     * @param dockerClient
     * @param containerId
     * @param dockerCacheDir
     * @param redisCacheKey
     */
    void restoreCache(DockerClient dockerClient, String containerId, String dockerCacheDir, String redisCacheKey) {
        if (dockerCacheDir != null && tarCacheService.hasCache(String.valueOf(redisCacheKey))) {
            log.info("Container[{}] resotring Cache, cacheKey:{}, dir:{}", containerId, redisCacheKey, dockerCacheDir);

            if (!(dockerCacheDir.lastIndexOf('/') == 0)) {
                String temp = dockerCacheDir.substring(0, dockerCacheDir.length() - 1);
                dockerCacheDir = temp.substring(0, temp.lastIndexOf('/')) + "/";
            }

            try {
                dockerClient.copyToContainer(TarUtil.buildEmptyDir(dockerCacheDir), containerId, "/");//创建缓存dir
                ByteArrayInputStream cachedTar = tarCacheService.get(String.valueOf(redisCacheKey));//获得cache的tarInputStream
                dockerClient.copyToContainer(cachedTar, containerId, dockerCacheDir);//复制到docker
            } catch (DockerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 设置缓存
     *
     * @param dockerClient
     * @param containerId
     * @param dockerCacheDir
     * @param redisCacheKey
     */
    void setDirCache(DockerClient dockerClient, String containerId, String dockerCacheDir, String redisCacheKey) {
        //设置缓存
        if (!tarCacheService.hasCache(String.valueOf(redisCacheKey))) {
            log.info("Container[{}] saving Cache, cacheKey:{}, dir:{}", containerId, redisCacheKey, dockerCacheDir);
            try {

                tarCacheService.set(String.valueOf(redisCacheKey), dockerClient.archiveContainer(containerId, dockerCacheDir));
            } catch (DockerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 删除container
     * @param dockerClient
     * @param containerId
     */
    void removeContainer(DockerClient dockerClient, String containerId) {
        if (dockerClient != null && containerId != null) {
            try {
                dockerClient.removeContainer(containerId);
                log.info("container [{}] removed", containerId);
            } catch (DockerException e) {
                e.printStackTrace();
                log.error("container [{}] remove faild", containerId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理日志
     *
     * @param containerLogsOutputHandler
     */
    void createLogThread(DockerClient dockerClient, String containerId, ContainerLogsOutputHandler containerLogsOutputHandler, AtomicReference<BufferedReader> bufferedReader) {
        String threadName = MessageFormat.format(NameConstants.Log_Handler_Thread_Name, containerId);
        LogStream logs = null;
        Thread thread = null;

        try {
            logs = dockerClient.logs(containerId, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr(), DockerClient.LogsParam.follow());
            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
            thread = new Thread(() -> {
                bufferedReader.set(new BufferedReader(new InputStreamReader(pipedInputStream)));
                while (true) {
                    try {
                        String oneLineLog = bufferedReader.get().readLine();
                        if (containerLogsOutputHandler != null && oneLineLog != null) {
                            containerLogsOutputHandler.handle(oneLineLog);
                        }
                    } catch (IOException e) {
                        log.info(threadName + " finished");
                        break;
                    }
                }
            });
            thread.setName(threadName);
            thread.start();
            logs.attach(pipedOutputStream, pipedOutputStream);
        } catch (DockerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

