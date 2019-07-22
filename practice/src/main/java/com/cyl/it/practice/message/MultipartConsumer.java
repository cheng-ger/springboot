package com.cyl.it.practice.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author chengyuanliang
 * @desc  如果是同一个队列多个消费类型那么就需要针对每种类型提供一个消费方法，否则找不到匹配的方法会报错，如下：
 * @since 2019-07-22
 */
@Component
@Slf4j
/*@RabbitListener(
        bindings = @QueueBinding(
                exchange = @Exchange(value = RabbitMQConstant.MULTIPART_HANDLE_EXCHANGE, type = ExchangeTypes.TOPIC,
                        durable = RabbitMQConstant.FALSE_CONSTANT, autoDelete = RabbitMQConstant.true_CONSTANT),
                value = @Queue(value = RabbitMQConstant.MULTIPART_HANDLE_QUEUE, durable = RabbitMQConstant.FALSE_CONSTANT,
                        autoDelete = RabbitMQConstant.true_CONSTANT),
                key = RabbitMQConstant.MULTIPART_HANDLE_KEY
        )
)*/
@RabbitListener(
        bindings = @QueueBinding(
                exchange = @Exchange(value = "MULTIPART_HANDLE_EXCHANGE", type = ExchangeTypes.TOPIC,
                        durable = "false", autoDelete = "true"),
                value = @Queue(value = "MULTIPART_HANDLE_QUEUE", durable = "false",
                        autoDelete = "true"),
                key = "MULTIPART_HANDLE_KEY"
        )
)
public class MultipartConsumer {

    /**
     * RabbitHandler用于有多个方法时但是参数类型不能一样，否则会报错
     *
     * @param msg
     */
    @RabbitHandler
    public void process(/*ExampleEvent*/ String msg) {
        log.info("param:{msg = [" + msg + "]} info:");
    }

    @RabbitHandler
    public void processMessage2(/*ExampleEvent2*/ Integer msg) {
        log.info("param:{msg2 = [" + msg + "]} info:");
    }

    /**
     * 下面的多个消费者，消费的类型不一样没事，不会被调用，但是如果缺了相应消息的处理Handler则会报错
     *
     * @param msg
     */
    @RabbitHandler
    public void processMessage3(/*ExampleEvent3*/ Double msg) {
        log.info("param:{msg3 = [" + msg + "]} info:");
    }


}
