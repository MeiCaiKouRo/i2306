package com.next.mq;


import com.next.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMqServer {


    @RabbitListener(queues = QueueConstants.COMMON_QUEUE)
    public void receive(String message) {
        log.info("common queue receive message, {}", message);

        try {
            MessageBody messageBody = JsonMapper.string2Obj(message, new TypeReference<MessageBody>() {
            });

            if (messageBody == null) {
                return;
            }
            switch (messageBody.getTopic()) {
                case QueueTopic.ORDER_CREATE:
                    log.info("order create message:{}", message);
                    // 比如给用户发短信，让他尽早支付

                    break;
                default:
                    log.warn("common queue receive message, {}, no need handle", message);
            }
        } catch (Exception e) {
            log.error("common queue message handle exception, msg:{}", message, e);
        }
    }
}
