package com.ljqiii.hairlessdockerjudge.service;


import com.ljqiii.hairlesscommon.domain.ProblemCode;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerCreation;
import org.springframework.stereotype.Service;

public interface DockerClientService {


    String execProblemWithContainer(
            String imageName,
            String containerName,
            ProblemCode problemCode,
            String cmdList[],
            String problemCodePath,
            String workingDir,
            String cacheKey,
            boolean removeAfterExit);

    String execProblemWithContainer(
            String imageName,
            String containerName,
            ProblemCode problemCode,
            String cmdList[],
            String problemCodePath,
            String cacheKey,
            boolean removeAfterExit);









}
