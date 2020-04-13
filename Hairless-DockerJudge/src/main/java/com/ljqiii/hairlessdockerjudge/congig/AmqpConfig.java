package com.ljqiii.hairlessdockerjudge.congig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Bean
    public TopicExchange SubmitedProblemItemExchange() {
        return new TopicExchange("SubmitedProblemExchange", true, false);
    }

    @Bean
    public Queue SubmitedProblemItemQueue() {
        return new Queue("SubmitedProblem", true, false, false);
    }


    @Bean
    public Binding loginInfoBinding() {
        Binding binding = BindingBuilder.bind(SubmitedProblemItemQueue()).to(SubmitedProblemItemExchange()).with("SubmitedProblem.#");
        return binding;
    }

}
