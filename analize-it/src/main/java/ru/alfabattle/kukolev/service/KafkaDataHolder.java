package ru.alfabattle.kukolev.service;

import org.springframework.stereotype.Component;
import ru.alfabattle.kukolev.dto.KafkaMessageDto;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class KafkaDataHolder extends CopyOnWriteArrayList<KafkaMessageDto> {
}
