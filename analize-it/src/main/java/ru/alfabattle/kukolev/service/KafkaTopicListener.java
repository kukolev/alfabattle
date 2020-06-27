package ru.alfabattle.kukolev.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.alfabattle.kukolev.dto.KafkaMessageDto;

@Slf4j
@Component
public class KafkaTopicListener {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private KafkaDataHolder dataHolder;

    @KafkaListener(topics = "${kafka.input-topic}")
    public void consume(@Payload byte[] arr) {
        String s = new String(arr);
        log.info("Raw dto consumed: {}", s);
        try {
            KafkaMessageDto dto = mapper.readValue(s, KafkaMessageDto.class);
            System.out.println(dto);
            dataHolder.add(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
