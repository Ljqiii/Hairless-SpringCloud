package com.ljqiii.hairlessdockerjudge.comsumer;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.constants.JudgeStepConstants;
import com.ljqiii.hairlesscommon.constants.LangConstants;
import com.ljqiii.hairlesscommon.constants.NameConstants;
import com.ljqiii.hairlesscommon.constants.WsDestinationConstants;
import com.ljqiii.hairlesscommon.vo.wsvo.JudgeStepMessage;
import com.ljqiii.hairlesscommon.vo.wsvo.JudgeLogsMessage;
import com.ljqiii.hairlessdockerjudge.handler.ContainerLogsOutputHandler;
import com.ljqiii.hairlesscommon.domain.amqpdomain.SubmitedProblemItem;
import com.ljqiii.hairlessdockerjudge.interceptor.ContainerInterceptor;
import com.ljqiii.hairlessdockerjudge.service.DockerClientService;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;


@Slf4j
@Component
@RabbitListener(queues = "SubmitedProblem")
public class UserInfoComsumer {


    @Autowired
    DockerClientService dockerClientService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    //更新最后登录时间，增加登录积分
    @RabbitHandler
    public void process(SubmitedProblemItem submitedProblemItem) {
        log.info("Mq process SubmitedProblem, submitId:{}", submitedProblemItem.getSubmitId());
        System.out.println(JSONObject.toJSONString(submitedProblemItem));

        try {
            //发送ws消息，等待获得虚拟机
            simpMessagingTemplate.convertAndSendToUser(submitedProblemItem.getUsername(),
                    WsDestinationConstants.JudgeLifeCycle,
                    JudgeStepMessage.builder().event(JudgeStepConstants.AssigningDockerVirtualMachine).submitId(submitedProblemItem.getSubmitId()).flag("ok").build());
            judge(submitedProblemItem);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Mq process SubmitedProblem faild, SubmitedProblemItem:{}", JSONObject.toJSONString(submitedProblemItem));
        }
    }

    public void judge(SubmitedProblemItem s) {
        dockerClientService.execProblemWithContainer(s.getImageName(),
                MessageFormat.format(NameConstants.Container_Name, s.getImageName().replace(":", "_").replace("/", "-"), s.getUsername(), s.getSubmitId()),
                s.getProblemId(), s.getProblemCode(),
                s.getCmdList(),
                getContainerLogsOutputHandlerByLang(s.getLang(), s.getUsername(), s.getSubmitId()),
                getContainerInterceptor(s.getLang(), s.getUsername(), s.getSubmitId()),
                MessageFormat.format(NameConstants.CodeDir, s.getUsername(), String.valueOf(s.getProblemId()), String.valueOf(s.getSubmitId())),
                MessageFormat.format(NameConstants.CodeDir, s.getUsername(), String.valueOf(s.getProblemId()), String.valueOf(s.getSubmitId())),
                s.isRemoveAfterExit());
    }

    /**
     * @param lang
     * @param username
     * @param submitId
     * @return
     */
    ContainerLogsOutputHandler getContainerLogsOutputHandlerByLang(String lang, String username, int submitId) {

        ContainerLogsOutputHandler commonContainerLogsOutputHandler = new ContainerLogsOutputHandler() {

            @Override
            public void handle(String logsline) {
                System.out.println(logsline);

                simpMessagingTemplate.convertAndSendToUser(username,
                        WsDestinationConstants.JudgeLogs,
                        JudgeLogsMessage.builder().oneLineLog(logsline).submitId(submitId).build());
            }
        };

        if (lang.equals(LangConstants.JAVA8_MAVEN) || lang.equals(LangConstants.JAVA11_MAVEN)) {
            return commonContainerLogsOutputHandler;
        } else {
            //根据语言不同，选择不同日志处理
            return commonContainerLogsOutputHandler;
        }
    }

    /**
     * @param lang
     * @param username
     * @param submitId
     * @return
     */
    ContainerInterceptor getContainerInterceptor(String lang, String username, int submitId) {


        ContainerInterceptor commonContainerInterceptor = new ContainerInterceptor() {
            @Override
            public void beforeContainerConfigBuild(ContainerConfig.Builder builder) {

            }

            @Override
            public void beforeContainerCreate(DockerClient dockerClient) {
                //发送ws消息，获得虚拟机成功
                simpMessagingTemplate.convertAndSendToUser(username,
                        WsDestinationConstants.JudgeLifeCycle,
                        JudgeStepMessage.builder().event(JudgeStepConstants.AssignDockerVirtualMachineSuccessful).submitId(submitId).flag("ok").build());
            }

            @Override
            public void beforeContainerStart(DockerClient dockerClient, String containerId) {
                //发送ws消息，开始执行
                simpMessagingTemplate.convertAndSendToUser(username,
                        WsDestinationConstants.JudgeLifeCycle,
                        JudgeStepMessage.builder().event(JudgeStepConstants.Executing).submitId(submitId).flag("ok").build());
            }

            @Override
            public void containerEnd(DockerClient dockerClient, String containerId, int exitCode) {

                //发送ws消息，执行结束
                JudgeStepMessage.JudgeStepMessageBuilder builder = JudgeStepMessage.builder();
                builder.submitId(submitId);
                if (exitCode == 0) {
                    builder.flag("ok");
                    builder.event(JudgeStepConstants.ExecuteEndWithSuccessfulResult);
                } else {
                    builder.flag("error");
                    builder.event(JudgeStepConstants.ExecuteEndWithFailResult);
                }
                JudgeStepMessage payload = builder.build();

                simpMessagingTemplate.convertAndSendToUser(username,
                        WsDestinationConstants.JudgeLifeCycle,
                        payload);
            }
        };

        return commonContainerInterceptor;

        //根据不同语言
    }

}


