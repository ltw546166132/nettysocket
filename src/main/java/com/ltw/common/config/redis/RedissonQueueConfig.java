package com.ltw.common.config.redis;

import com.alibaba.fastjson.JSONObject;
import com.ltw.module.test.model.entity.TestUser;
import com.riven.redisson.annotation.EnableRedisson;
import com.riven.redisson.annotation.RedissonListener;
import com.riven.redisson.config.RedissonQueue;
import com.riven.redisson.exception.MessageConversionException;
import com.riven.redisson.message.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableRedisson
public class RedissonQueueConfig {
    @Bean
    public RedissonQueue redissonQueue() {
        return new RedissonQueue("testRedissonQueue", true, null, messageConverter());
    }

    @Bean("myMessageConverter")
    public MessageConverter messageConverter() {
        return new MessageConverter() {
            @Override
            public QueueMessage<?> toMessage(Object object, Map<String, Object> headers) throws MessageConversionException {
                //do something you want, eg:
                headers.put("my_header", "my_header_value");
                headers.put(RedissonHeaders.MESSAGE_ID, UUID.randomUUID());
                return QueueMessageBuilder.withPayload(object).headers(headers).build();
            }

            @Override
            public Object fromMessage(RedissonMessage redissonMessage) throws MessageConversionException {
                byte[] payload = redissonMessage.getPayload();
                String payloadStr = new String(payload);
                return JSONObject.parseObject(payloadStr, TestUser.class);
            }
        };
    }

    @RedissonListener(queues = "testRedissonQueue", messageConverter = "myMessageConverter")
    public void handler(@Header(value = RedissonHeaders.MESSAGE_ID, required = false) String messageId,
                        @Header(RedissonHeaders.DELIVERY_QUEUE_NAME) String queue,
                        @Header(RedissonHeaders.SEND_TIMESTAMP) long sendTimestamp,
                        @Header(RedissonHeaders.EXPECTED_DELAY_MILLIS) long expectedDelayMillis,
                        @Header(value = "count", required = false, defaultValue = "0") String count,
                        @Payload TestUser testUser) {
        System.out.println(messageId);
        System.out.println(queue);
        System.out.println(count);
        long actualDelay = System.currentTimeMillis() - (sendTimestamp + expectedDelayMillis);
        System.out.println("receive " + testUser + ", delayed " + actualDelay + " millis");
    }
}
