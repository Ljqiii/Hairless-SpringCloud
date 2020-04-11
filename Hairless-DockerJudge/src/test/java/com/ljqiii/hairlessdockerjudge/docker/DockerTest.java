package com.ljqiii.hairlessdockerjudge.docker;

import com.ljqiii.hairlessdockerjudge.utils.TarUtil;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerExit;
import org.apache.commons.compress.archivers.ArchiveException;
import org.junit.Before;
import org.junit.Test;


import java.io.*;

public class DockerTest {

    DockerClient docker;

    @Before
    public void setUp() {
        docker = new DefaultDockerClient("unix:///var/run/docker.sock");

    }


    //    docker commit 7c1f2ce992bd
//    docker run -it 6bc2da7440811f006589155d414be7ffac11bf3cae7306ad19e0f892e62dd93c bash
    @Test
    public void container() throws DockerException, InterruptedException, IOException {

        ContainerConfig build = ContainerConfig.builder()
                .image("ubuntu:latest")
                .cmd("echo", "")
                .workingDir("/")
                .build();

        ContainerCreation container = docker.createContainer(build);

        docker.copyToContainer(new FileInputStream(new File("/Users/ljq/IdeaProjects/Hairless-SpringCloud/Hairless-DockerJudge/src/test/resources/a.tar")),
                container.id(), "/");


        String containerId = container.id();


        docker.startContainer(containerId);

        LogStream logs = docker.logs(containerId, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        logs.attach(outputStream, null);

        ContainerExit containerExit = docker.waitContainer(containerId);
        Long statusCode = containerExit.statusCode();


        docker.removeContainer(containerId);
    }

    @Test
    public void testLog() throws DockerException, InterruptedException, IOException {

        ContainerConfig build = ContainerConfig.builder()
                .image("maven:3-jdk-8")
                .cmd("mvn clean test >/a.txt 2>&1".split(" "))
                .workingDir("/")
                .build();

        ContainerCreation container = docker.createContainer(build);

//        docker.copyToContainer(new FileInputStream(new File("/Users/ljq/IdeaProjects/Hairless-SpringCloud/Hairless-DockerJudge/src/test/resources/a.tar")),
//                container.id(), "/");


        String containerId = container.id();


        docker.startContainer(containerId);

        LogStream logs = docker.logs(containerId, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream erroutputStream = new ByteArrayOutputStream();
        logs.attach(outputStream, erroutputStream);


        ContainerExit containerExit = docker.waitContainer(containerId);
        Long statusCode = containerExit.statusCode();


        docker.removeContainer(containerId);
    }


    @Test
    public void testPushTarFilesByTarUtils() throws DockerException, InterruptedException, IOException {
        ContainerConfig build = ContainerConfig.builder()
                .image("ubuntu:latest")
                .cmd("cat", "/a/bbb")
                .workingDir("/")
                .build();

        ContainerCreation container = docker.createContainer(build);

        ByteArrayInputStream inputStream = null;

        try {
            inputStream = TarUtil.TarOutStreamBuilder
                    .builder()
                    .addFileWithStringContent("aaa", "aaaa")
                    .addFileWithStringContent("a/bbb", "test")
                    .buildInputStream();
        } catch (ArchiveException e) {
            e.printStackTrace();
        }

        docker.copyToContainer(inputStream,
                container.id(), "/");


        String containerId = container.id();


        docker.startContainer(containerId);

        LogStream logs = docker.logs(containerId, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        logs.attach(outputStream, null);
        String s = logs.readFully();


        ContainerExit containerExit = docker.waitContainer(containerId);
        Long statusCode = containerExit.statusCode();


        docker.removeContainer(containerId);
    }

    public static void main(String[] args) {

    }

}
