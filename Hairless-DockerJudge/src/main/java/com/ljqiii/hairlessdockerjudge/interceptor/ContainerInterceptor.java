package com.ljqiii.hairlessdockerjudge.interceptor;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerConfig;

public interface ContainerInterceptor {

    void beforeContainerConfigBuild(ContainerConfig.Builder builder);

    void beforeContainerCreate(DockerClient dockerClient);

    void beforeContainerStart(DockerClient dockerClient, String containerId);

    void containerEnd(DockerClient dockerClient, String containerId, int exitCode);

}
