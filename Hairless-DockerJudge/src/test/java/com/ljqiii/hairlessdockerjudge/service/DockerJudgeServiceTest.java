package com.ljqiii.hairlessdockerjudge.service;

import com.ljqiii.hairlesscommon.domain.amqpdomain.SubmitedProblemItem;
import com.ljqiii.hairlessdockerjudge.comsumer.UserInfoComsumer;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestExcludeConsumer.class)
class DockerJudgeServiceTest {

    @MockBean
//    @Autowired
            UserInfoComsumer userInfoComsumer;

    @Autowired
    DockerJudgeService dockerJudgeService;

    @Autowired
    AmqpTemplate amqpTemplate;


    @BeforeEach
    void setUp() {

    }

    @Test
    void testNotConsumer() {

        for (int i = 0; i < 500; i++) {
            int finalI = i;
            amqpTemplate.convertAndSend("SubmitedProblemExchange",
                    "SubmitedProblem.problem", SubmitedProblemItem.builder().submitId(i % 2 == 0 ? 1 : 2).build()
                    ,
                    new MessagePostProcessor() {
                        @Override
                        public Message postProcessMessage(Message message) throws AmqpException {
                            message.getMessageProperties().setPriority(finalI % 2 == 0 ? 1 : 2);
                            return message;
                        }
                    }
                    );
        }

    }

    @Test
    void assertUserInfoNull() {
        Assert.assertNotNull(dockerJudgeService);
    }

    @Test
    void submitCode() {
        Assert.assertNotNull(userInfoComsumer);
    }


}
