package com.cyl.it.practice.message;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chengyuanliang
 * @desc @Header 注入消息头的单个属性
 * @Payload 注入消息体到一个JavaBean中
 * @Headers 注入所有消息头到一个Map中
 * @since 2019-07-22
 */
@Component
@Slf4j
public class RabbitMQListing {


    /**
     * 申明一个队列，如果这个队列不存在，将会被创建
     * @param queue 队列名称
     * @param durable 持久性：true队列会再重启过后存在，但是其中的消息不会存在。
     * @param exclusive 是否只能由创建者使用，其他连接不能使用。
     * @param autoDelete 是否自动删除（没有连接自动删除）
     * @param arguments 队列的其他属性(构造参数)
     * @return Queue.DeclareOk：宣告队列的声明确认方法已成功声明。
     * @throws java.io.IOException if an error is encountered
     */


    /**
    * 声明一个 exchange.
    * @param exchange 名称
    * @param type  exchange type：direct、fanout、topic、headers
    * @param durable 持久化
    * @param autoDelete 是否自动删除（没有连接自动删除）
    * @param arguments 队列的其他属性(构造参数)
    * @return 成功地声明了一个声明确认方法来指示交换。
    * @throws java.io.IOException if an error is encountered
    */

    /**
     * 可以直接通过注解声明交换器、绑定、队列。但是如果声明的和rabbitMq中已经存在的不一致的话
     * 会报错便于测试，我这里都是不使用持久化，没有消费者之后自动删除
     * {@link RabbitListener}是可以重复的。并且声明队列绑定的key也可以有多个.
     *
     * @param headers
     * @param msg
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(value = "defaultExchange", type = ExchangeTypes.TOPIC,
                            durable = "false", autoDelete = "true"),
                    value = @Queue(value = "defaultQueue", durable = "false",
                            autoDelete = "true"),
                    key = "*.cyl.#"
            ),
            //手动指明消费者的监听容器，默认Spring为自动生成一个SimpleMessageListenerContainer
            containerFactory = "container",
            //指定消费者的线程数量,一个线程会打开一个Channel，一个队列上的消息只会被消费一次（不考虑消息重新入队列的情况）,下面的表示至少开启5个线程，最多10个。线程的数目需要根据你的任务来决定，如果是计算密集型，线程的数目就应该少一些
            concurrency = "5-10"
    )
    public void process(@Headers Map<String, Object> headers, @Payload String msg) {
        log.info("basic consumer receive message:{headers = [" + headers + "], msg = [" + msg + "]}");
    }

    /**
     * {@link Queue#ignoreDeclarationExceptions}声明队列会忽略错误不声明队列，这个消费者仍然是可用的
     *
     * @param headers
     * @param msg
     */
    @RabbitListener(queuesToDeclare = @Queue(value = "defaultQueue", ignoreDeclarationExceptions ="true"))
    public void process2(@Headers Map<String, Object> headers, @Payload String msg) {
        log.info("basic2 consumer receive message:{headers = [" + headers + "], msg = [" + msg + "]}");
    }


    //消费者确认模式
   /* @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = RabbitMQConstant.CONFIRM_EXCHANGE, type = ExchangeTypes.TOPIC,
                    durable = RabbitMQConstant.FALSE_CONSTANT, autoDelete = RabbitMQConstant.true_CONSTANT),
            value = @Queue(value = RabbitMQConstant.CONFIRM_QUEUE, durable = RabbitMQConstant.FALSE_CONSTANT,
                    autoDelete = RabbitMQConstant.true_CONSTANT),
            key = RabbitMQConstant.CONFIRM_KEY),
            containerFactory = "containerWithConfirm")*/
    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "CONFIRM_EXCHANGE", type = ExchangeTypes.TOPIC,
                    durable = "false", autoDelete = "true"),
            value = @Queue(value = "CONFIRM_QUEUE", durable = "false",
                    autoDelete = "true"),
            key = "CONFIRM_QUEUE"),
            containerFactory = "containerWithConfirm")
    public void process(String msg, Channel channel, @Header(name = "amqp_deliveryTag") long deliveryTag,
                        @Header("amqp_redelivered") boolean redelivered, @Headers Map<String, String> head) {
        try {
            log.info("ConsumerWithConfirm receive message:{},header:{}", msg, head);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("consume confirm error!", e);
            //这一步千万不要忘记，不会会导致消息未确认，消息到达连接的qos之后便不能再接收新消息
            //一般重试肯定的有次数，这里简单的根据是否已经重发过来来决定重发。第二个参数表示是否重新分发
            channel.basicReject(deliveryTag, !redelivered);
            //这个方法我知道的是比上面多一个批量确认的参数
            // channel.basicNack(deliveryTag, false,!redelivered);
        }
    }

     //异常重试机制
    /*spring:
    rabbitmq:
    listener:
    retry:
            #    重试次数
    max-attempts: 3
            #   开启重试机制
    enabled: true*/


    /*关于消息进入死信的规则：

    消息被拒绝(basic.reject/basic.nack)并且requeue=false
    消息TTL过期
    队列达到最大长度;*/
    //死信队列其实就是普通的队列，只不过一个队列声明的时候指定的属性，会将死信转发到该交换器中
    //其实也就只是在声明的时候多加了两个参数x-dead-letter-exchange和x-dead-letter-routing-key
   /* @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(value = RabbitMQConstant.DEFAULT_EXCHANGE, type = ExchangeTypes.TOPIC,
                            durable = RabbitMQConstant.FALSE_CONSTANT, autoDelete = RabbitMQConstant.true_CONSTANT),
                    value = @Queue(value = RabbitMQConstant.DEFAULT_QUEUE, durable = RabbitMQConstant.FALSE_CONSTANT,
                            autoDelete = RabbitMQConstant.true_CONSTANT, arguments = {
                            @Argument(name = RabbitMQConstant.DEAD_LETTER_EXCHANGE, value = RabbitMQConstant.DEAD_EXCHANGE),
                            @Argument(name = RabbitMQConstant.DEAD_LETTER_KEY, value = RabbitMQConstant.DEAD_KEY)
                    }),
                    key = RabbitMQConstant.DEFAULT_KEY
            ))*/
    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(value = "DEFAULT_EXCHANGE_A", type = ExchangeTypes.TOPIC,
                            durable = "false", autoDelete = "true"),
                    value = @Queue(value = "DEFAULT_QUEUE_A", durable = "false",
                            autoDelete = "true", arguments = {
                            @Argument(name = "DEAD_LETTER_EXCHANGE", value = "DEAD_EXCHANGE"),
                            @Argument(name = "DEAD_LETTER_KEY", value = "DEAD_KEY")
                    }),
                    key = "DEFAULT_KEY_A"
            ))
    public void process(String msg) {
        log.info("消费者：<<<<===MqReceiver===>>>myQueue:{}",msg);
    }
}

