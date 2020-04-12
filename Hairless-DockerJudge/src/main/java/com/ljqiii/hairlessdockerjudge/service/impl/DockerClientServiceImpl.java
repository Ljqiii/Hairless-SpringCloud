package com.ljqiii.hairlessdockerjudge.service.impl;

import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.domain.ProblemCode;
import com.ljqiii.hairlessdockerjudge.dao.ProblemMapper;
import com.ljqiii.hairlessdockerjudge.handler.ContainerLogsOutputHandler;
import com.ljqiii.hairlessdockerjudge.interceptor.ContainerInterceptor;
import com.ljqiii.hairlessdockerjudge.service.DockerClientService;
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
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class DockerClientServiceImpl implements DockerClientService {

    @Autowired
    ProblemMapper problemMapper;

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

            //缓存
            if (dockerCacheDir != null) {
                dockerClient.copyToContainer(TarUtil.buildEmptyDir(dockerCacheDir), containerId, "/");//创建WorkIngDir
            }


            dockerClient.copyToContainer(TarUtil.buildEmptyDir(workingDir), containerId, "/");//创建WorkIngDir
            dockerClient.copyToContainer(TarUtil.buildEmptyDir(problemCodeDir), containerId, "/");//创建代码dir


            ByteArrayInputStream problemCodeTarStream = ProblemCodeUtil.convertProblemCodeToInputStream(problemCode, problemCodeDir);//代码tar文件
            dockerClient.copyToContainer(problemCodeTarStream, containerId, problemCodeDir);//复制代码
            log.info("Copy problem code to container");

            //拦截
            if (containerInterceptor != null) {
                containerInterceptor.beforeContainerStart(dockerClient, containerId);
            }

            //开始container
            dockerClient.startContainer(containerId);
            log.info("container [{}] started", containerId);
            logs = dockerClient.logs(containerId, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr(), DockerClient.LogsParam.follow());

            //接收log
            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

            //线程名字
            String threadName = "Container [" + containerName + "]" + "-LogsStreamThread";

            thread = new Thread(() -> {
                bufferedReader.set(new BufferedReader(new InputStreamReader(pipedInputStream)));
                while (true) {
                    try {
                        String oneLineLog = bufferedReader.get().readLine();
                        if (containerLogsOutputHandler != null) {
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

            ContainerExit containerExit = dockerClient.waitContainer(containerId);
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

            try {
                bufferedReader.get().close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //删除Container
            if (removeAfterExit && dockerClient != null && containerId != null) {
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
    }


    void resotreCache() {

    }


}

