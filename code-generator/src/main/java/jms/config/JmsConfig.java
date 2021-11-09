package jms.config;

import jms.consts.MyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

@Configuration
@Slf4j
public class JmsConfig implements MyConstants {

    @Bean
    public Queue generateCodeQueue() {
        return QueueBuilder.nonDurable(GENERATE_CODE_QUEUE).build();
    }

    @Bean
    public Queue checkCodeQueue() {
        return QueueBuilder.nonDurable(ACTIVATE_USER_QUEUE).build();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingGenerate() {
        return BindingBuilder.bind(generateCodeQueue()).to(exchange()).with(ROUTING_KEY_GENERATE);
    }

    @Bean
    public Binding bindingCheck() {
        return BindingBuilder.bind(checkCodeQueue()).to(exchange()).with(ROUTING_KEY_ACTIVATE);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(producerJackson2MessageConverter());
        factory.setErrorHandler(new ErrorHandler() {
            @Override
            public void handleError(Throwable t) {
                log.error(t.getMessage(), t);
                throw new AmqpRejectAndDontRequeueException("Converted to fatal.", t);
            }
        });
        return factory;
    }
}
