package com.ljqiii.hairlessdockerjudge.service.impl;

import com.ljqiii.hairlesscommon.domain.ProblemCode;
import com.ljqiii.hairlessdockerjudge.service.DockerClientService;
import com.ljqiii.hairlessdockerjudge.utils.ProblemCodeUtil;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerExit;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class DockerClientServiceImpl implements DockerClientService {

    public DockerClient getDockerClient() {
        DockerClient docker = new DefaultDockerClient("unix:///var/run/docker.sock");
        return docker;
    }

    @Override
    public String execProblemWithContainer(String imageName,
                                           String containerName,
                                           ProblemCode problemCode,
                                           String[] cmdList,
                                           String problemCodePath,
                                           String workingDir,
                                           String cacheKey,
                                           boolean removeAfterExit) {

        ContainerConfig.Builder builder = ContainerConfig.builder();
        builder.image(imageName);
        builder.cmd(cmdList);
        builder.workingDir(workingDir);

        ContainerConfig containerConfig = builder.build();
        DockerClient dockerClient = null;
        String containerId = null;
        LogStream logs = null;
        try {
            dockerClient = getDockerClient();
            ContainerCreation container = dockerClient.createContainer(containerConfig, containerName);
            containerId = container.id();
            dockerClient.copyToContainer(ProblemCodeUtil.convertProblemCodeToInputStream(problemCode, problemCodePath), containerId, problemCodePath);

            //开始container
            dockerClient.startContainer(containerId);
            logs = dockerClient.logs(containerId, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr());
            ByteArrayOutputStream stdoutOutputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream stderrOutputStream = new ByteArrayOutputStream();
            logs.attach(stdoutOutputStream, stderrOutputStream);

            ContainerExit containerExit = dockerClient.waitContainer(containerId);
            Long exitCode = containerExit.statusCode();

            return container.id();
        } catch (DockerException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            String fullLogs = logs.readFully();

            //删除Container
            if (removeAfterExit && dockerClient != null && containerId != null) {
                try {
                    dockerClient.removeContainer(containerId);
                } catch (DockerException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String execProblemWithContainer(String imageName, String containerName, ProblemCode problemCode, String[] cmdList, String problemCodePath, String cacheKey, boolean removeAfterExit) {
        return execProblemWithContainer(imageName, containerName, problemCode, cmdList, problemCodePath, problemCodePath, cacheKey, removeAfterExit);
    }


}

