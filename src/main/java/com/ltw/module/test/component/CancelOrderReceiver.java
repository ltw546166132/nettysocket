package com.ltw.module.test.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RabbitListener(queues = "mall.order.cancel")
public class CancelOrderReceiver {
    private static Logger LOGGER = LoggerFactory.getLogger(CancelOrderReceiver.class);

//    @Autowired
//    private OmsPortalOrderService portalOrderService;

    @RabbitHandler
    public void handle(Long orderId){
        LOGGER.info("接收时间:  "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        LOGGER.info("receive delay message orderId:{}",orderId);
//        portalOrderService.cancelOrder(orderId);
    }

}
