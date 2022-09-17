package com.geekbrains.spring.web.front;

import com.geekbrains.spring.web.front.dto.OrderComponentsDto;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestConsumer {

    //@KafkaListener(topics = "Order")
    //public void msgListener(OrderComponentsDto orderComponentsDto) {
    //    System.out.println(orderComponentsDto);
    //}

}
