package com.ljqiii.hairlessdockerjudge.service;


import com.ljqiii.hairlesscommon.domain.ProblemCode;
import com.ljqiii.hairlessdockerjudge.handler.ContainerLogsOutputHandler;
import com.ljqiii.hairlessdockerjudge.interceptor.ContainerInterceptor;

public interface DockerClientService {


    Long execProblemWithContainer(
            String imageName,//镜像名
            String containerName,//生成的container名
            int problemId,
            ProblemCode problemCode,
            String cmdList[],
            ContainerLogsOutputHandler containerLogsOutputHandler,//日志处理
            ContainerInterceptor containerInterceptor,//container拦截器
            String problemCodeDir,
            String workingDir,
            boolean removeAfterExit);//退出时删除Container

}
