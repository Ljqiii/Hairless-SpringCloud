package com.ljqiii.hairlessdockerjudge.congig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AmqpConfig {

    @Bean
    public TopicExchange submitedProblemItemExchange() {
        return new TopicExchange("SubmitedProblemExchange", true, false);
    }

    @Bean
    public Queue submitedProblemItemQueue() {
        Map<String, Object> property = new HashMap<>();
        property.put("x-max-priority", 10);
        return new Queue("SubmitedProblem", true, false, false, property);
    }


    @Bean
    public Binding loginInfoBinding() {
        return BindingBuilder.bind(submitedProblemItemQueue()).to(submitedProblemItemExchange()).with("SubmitedProblem.#");

    }

}
