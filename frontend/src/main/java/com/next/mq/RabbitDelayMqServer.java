package com.next.mq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RabbitDelayMqServer {


    @RabbitListener(queues = QueueConstants.DELAY_QUEUE)
    public void receive(String message) {
        try {
            log.info("delay queue receive message, {}", message);
        } catch (Exception e) {
            log.error("delay queue message handle exception, msg:{}", message, e);
        }
    }
}
