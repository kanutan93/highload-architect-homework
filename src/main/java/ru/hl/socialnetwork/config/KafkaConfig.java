package ru.hl.socialnetwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
public class KafkaConfig {

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory =
        new ConcurrentKafkaListenerContainerFactory<>();

    listenerContainerFactory.setConsumerFactory(consumerFactory);
    listenerContainerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);

    FixedBackOff backOff = new FixedBackOff(5000, 3);
    SeekToCurrentErrorHandler seekToCurrentErrorHandler = new SeekToCurrentErrorHandler(backOff);
    listenerContainerFactory.setErrorHandler(seekToCurrentErrorHandler);

    return listenerContainerFactory;
  }
}
