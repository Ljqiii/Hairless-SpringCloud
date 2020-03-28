package com.ljqiii.hairlessaccount.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Bean
    public TopicExchange loginInfoExchange() {
        return new TopicExchange("logininfo", true, false);
    }

    @Bean
    public Queue toLoginTimeQueue() {
        return new Queue("logininfoTime", true, false, false);
    }


    @Bean
    public Binding loginInfoBinding() {
        Binding binding = BindingBuilder.bind(toLoginTimeQueue()).to(loginInfoExchange()).with("logininfo.#");
        return binding;
    }

}
