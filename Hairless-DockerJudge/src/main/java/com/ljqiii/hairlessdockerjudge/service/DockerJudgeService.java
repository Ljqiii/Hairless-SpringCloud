package com.ljqiii.hairlessdockerjudge.service;


import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.constants.JudgeStepConstants;
import com.ljqiii.hairlesscommon.constants.WsDestinationConstants;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.domain.ProblemCode;
import com.ljqiii.hairlesscommon.domain.Submit;
import com.ljqiii.hairlesscommon.enums.JudgePriorityEnum;
import com.ljqiii.hairlesscommon.vo.wsvo.JudgeStepMessage;
import com.ljqiii.hairlessdockerjudge.dao.ProblemMapper;
import com.ljqiii.hairlessdockerjudge.dao.SubmitMapper;
import com.ljqiii.hairlesscommon.domain.amqpdomain.SubmitedProblemItem;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.ljqiii.hairlessdockerjudge.utils.ProblemCodeUtil.verifyProblemCode;

@Service
public class DockerJudgeService {

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    SubmitMapper submitMapper;

    @Autowired
    DockerClientService dockerClientService;

    @Autowired
    AmqpTemplate amqpTemplate;


    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


    /**
     * 提交代码，返回提交id
     *
     * @param username
     * @param problemId
     * @param problemCode
     */
    public int submitCode(String username, int problemId, ProblemCode problemCode, JudgePriorityEnum priorityEnum) {
        if (!verifyProblemCode(problemId, problemCode)) {
            throw new IllegalArgumentException("代码非法");
        }

        Problem problem = problemMapper.selectProblemById(problemId);

        Submit submit = Submit.builder()
                .username(username)
                .problemid(problemId)
                .submitedCode(JSONObject.toJSONString(problemCode))
                .build();
        int i = submitMapper.insertSubmit(submit);


        SubmitedProblemItem submitedProblemItem = SubmitedProblemItem.builder()
                .submitId(submit.getId())
                .username(username)
                .problemCode(problemCode)
                .problemId(problemId)
                .imageName(problem.getDockerImage())
                .cmdList(problem.getCmd().split(" "))
                .lang(problem.getLang())
                .removeAfterExit(true)
                .build();

        amqpTemplate.convertAndSend("SubmitedProblemExchange", "SubmitedProblem.problem", submitedProblemItem, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setPriority(priorityEnum.getPriority());
                return message;
            }
        });

        //发送ws提交成功消息
        simpMessagingTemplate.convertAndSendToUser(username,
                WsDestinationConstants.JudgeLifeCycle,
                JudgeStepMessage.builder().event(JudgeStepConstants.Submited).submitId(submit.getId()).flag("ok").build());

        return submit.getId();
    }

    /**
     * 是否只有vip Only
     *
     * @param problemid
     * @return
     */
    public boolean ifProblemVipOnly(int problemid) {
        boolean result = problemMapper.selectIfOnlyVipByProblemId(problemid);
        return result;
    }

}
