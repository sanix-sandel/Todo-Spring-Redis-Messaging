package com.sanix.todoRedis.config;

import com.sanix.todoRedis.ToDoConsumer;
import com.sanix.todoRedis.domain.ToDo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class ToDoConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter toDoListenerAdapter,
                                                   @Value("${todoRedis.redis.topic}")String topic){
        RedisMessageListenerContainer container=new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(toDoListenerAdapter, new PatternTopic(topic));
        return container;
    }

    @Bean
    MessageListenerAdapter toDoListenerAdapter(ToDoConsumer consumer){

        MessageListenerAdapter messageListenerAdapter=new MessageListenerAdapter(consumer);
        messageListenerAdapter.setSerializer(new Jackson2JsonRedisSerializer<>(ToDo.class));
        return messageListenerAdapter;
    }

    @Bean
    RedisTemplate<String, ToDo> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, ToDo> redisTemplate=new RedisTemplate<String, ToDo>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(ToDo.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
