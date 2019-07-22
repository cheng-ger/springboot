package com.cyl.it.practice.config;

import com.cyl.it.practice.commconstant.RabbitMQConstant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chengyuanliang
 * @desc  Exchange（交换机）:
 *              topic(设置模糊的绑定方式，“*”操作符将“.”视为分隔符，匹配单个字符；
 *                      “#”操作符没有分块的概念，它将任意“.”均视为关键字的匹配部分，能够匹配多个字符。)
 *              direct 处理路由键。需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。这是一个完整的匹配。
 *              fanout 不处理路由键。你只需要简单的将队列绑定到交换机上。一个发送到交换机的消息都会被转发到与该交换机绑定的所有队列上
 *              headers 不处理路由键。而是根据发送的消息内容中的headers属性进行匹配。在绑定Queue与Exchange时指定一组键值对；
 *              当消息发送到RabbitMQ时会取到该消息的headers与Exchange绑定时指定的键值对进行匹配；如果完全匹配则消息会路由到该队列，
 *              否则不会路由到该队列。headers属性是一个键值对，可以是Hashtable，
 *              键值对的值可以是任何类型。而fanout，direct，topic 的路由键都需要要字符串形式的。
 * @since 2019-07-21
 */

//有两种方式创建队列  此处用配置方式
    //1. 做配置项
    //2. 用 注解 @RabbitListing 进行监听 可自动创建

@Configuration
@Slf4j
public class RabbitMQConfig {


    /**
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     */
    /** 设置交换机类型  */
    @Bean/*fanout*/
    FanoutExchange defaultFanoutExchange() {

        return new FanoutExchange(RabbitMQConstant.DEFAULT_FANOUT_EXCHANGE);
       // return new FanoutExchange(RabbitMQConstant.DEFAULT_FANOUT_EXCHANGE, false, true);
    }


    @Bean
    Queue defaultFanoutQueueA() {

        return new Queue(RabbitMQConstant.DEFAULT_FANOUT_QUEUE_A);
        //return new Queue(RabbitMQConstant.DEFAULT_FANOUT_QUEUE_A, false, false, true);
    }

    @Bean
    Queue defaultFanoutQueueB() {

        return new Queue(RabbitMQConstant.DEFAULT_FANOUT_QUEUE_B);
    }

    /** 将队列绑定到交换机 */
    @Bean
    Binding defaultFanoutBindA() {
        return BindingBuilder.bind(defaultFanoutQueueA()).to(defaultFanoutExchange());
    }

    /** 将队列绑定到交换机 */
    @Bean
    Binding defaultFanoutBindB() {
        return BindingBuilder.bind(defaultFanoutQueueB()).to(defaultFanoutExchange());
    }


    //direct交换机
    @Bean
    DirectExchange defaultDirectExchange() {
        return new DirectExchange(RabbitMQConstant.DEFAULT_DIRECT_EXCHANGE);
    }

    @Bean
    Queue defaultDirectQueueA() {
        return new Queue(RabbitMQConstant.DEFAULT_DIRECT_QUEUE_A);
    }

    @Bean
    Queue defaultDirectQueueB() {
        return new Queue(RabbitMQConstant.DEFAULT_DIRECT_QUEUE_B);
    }

    @Bean
    Binding defaultDirectBindA() {
        return BindingBuilder.bind(defaultDirectQueueA()).to(defaultDirectExchange()).with(RabbitMQConstant.DEFAULT_DIRECT_ROUTINGKEY_A);
    }

    @Bean
    Binding defaultDirectBindB() {
        return BindingBuilder.bind(defaultDirectQueueB()).to(defaultDirectExchange()).with(RabbitMQConstant.DEFAULT_DIRECT_ROUTINGKEY_B);
    }


    //topic
    @Bean
    TopicExchange defaultTopicExchange() {

        return new TopicExchange(RabbitMQConstant.DEFAULT_TOPIC_EXCHANGE);
    }

    @Bean
    Queue defaultTopicQueueA() {
        return new Queue(RabbitMQConstant.DEFAULT_TOPIC_QUEUE_A);
    }

    @Bean
    Queue defaultTopicQueueB() {
        return new Queue(RabbitMQConstant.DEFAULT_TOPIC_QUEUE_B);
    }

    @Bean
    Queue defaultTopicQueueC() {
        return new Queue(RabbitMQConstant.DEFAULT_TOPIC_QUEUE_C);
    }

    @Bean
    Binding defaultTopicBindA() {
        return BindingBuilder.bind(defaultTopicQueueA()).to(defaultTopicExchange()).with(RabbitMQConstant.DEFAULT_TOPIC_ROUTINGKEY_A);
    }

    @Bean
    Binding defaultTopicBindB() {
        return BindingBuilder.bind(defaultTopicQueueB()).to(defaultTopicExchange()).with(RabbitMQConstant.DEFAULT_TOPIC_ROUTINGKEY_B);
    }

    @Bean
    Binding defaultTopicBindC() {
        return BindingBuilder.bind(defaultTopicQueueC()).to(defaultTopicExchange()).with(RabbitMQConstant.DEFAULT_TOPIC_ROUTINGKEY_C);
    }



    //声明简单的消费者，接收到的都是原始的{@link Message}
    @Bean
    SimpleMessageListenerContainer simpleContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(message -> log.info("simple receiver,message:{}", message));
        //container.setQueueNames(RabbitMQConstant.PROGRAMMATICALLY_QUEUE);
        container.setQueueNames("PROGRAMMATICALLY_QUEUE");
        return container;
    }

}
