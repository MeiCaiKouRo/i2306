package com.next.mq;


import com.next.dto.RollbackSeatDto;
import com.next.service.TrainSeatService;
import com.next.util.JsonMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RabbitDelayMqServer {

    @Resource
    private TrainSeatService trainSeatService;

    @RabbitListener(queues = QueueConstants.DELAY_QUEUE)
    public void receive(String message) {
        log.info("delay queue receive message, {}", message);

        try {
            MessageBody messageBody = JsonMapper.string2Obj(message, new TypeReference<MessageBody>() {
            });

            if (messageBody == null) {
                return;
            }

            switch (messageBody.getTopic()) {
                case QueueTopic.SEAT_PLACE_ROLLBACK:
                    RollbackSeatDto dto = JsonMapper.string2Obj(messageBody.getDetail(), new TypeReference<RollbackSeatDto>() {
                    });
                    trainSeatService.batchRollbackSeat(dto.getTrainSeat(), dto.getFromStationIdList(), messageBody.getDelay());
                    break;
                default:
                    log.warn("delay queue receive message, {}, no need handle", message);
            }
        } catch (Exception e) {
            log.error("delay queue message handle exception, msg:{}", message, e);
        }
    }
}
