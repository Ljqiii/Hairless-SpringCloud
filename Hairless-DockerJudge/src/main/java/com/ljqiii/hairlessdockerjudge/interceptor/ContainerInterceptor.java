package com.ljqiii.hairlessdockerjudge.interceptor;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerConfig;

public interface ContainerInterceptor {

    void beforeContainerConfigBuild(ContainerConfig.Builder builder);

    void beforeContainerStart(DockerClient dockerClient, String containerId);

}
