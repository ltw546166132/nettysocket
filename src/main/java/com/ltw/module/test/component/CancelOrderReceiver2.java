package com.ltw.module.test.component;

import com.ltw.module.test.model.entity.Org;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class CancelOrderReceiver2 {
    private static Logger LOGGER = LoggerFactory.getLogger(CancelOrderReceiver2.class);

//    @Autowired
//    private OmsPortalOrderService portalOrderService;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(), exchange = @Exchange(value = "radio.fanout" , type = ExchangeTypes.FANOUT)))
    public void handle(List<Org> orgList, Channel channel, Message message) throws IOException {
        LOGGER.info("接收时间:  "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        LOGGER.info("receive delay message orderId:{}",orgList);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
//        portalOrderService.cancelOrder(orderId);
    }

}
