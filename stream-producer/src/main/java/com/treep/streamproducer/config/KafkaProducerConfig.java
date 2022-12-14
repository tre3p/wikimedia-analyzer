package com.treep.streamproducer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerProperties() {
        log.debug("+producerProperties()");
        Map<String, Object> producerProperties = new HashMap<>();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, "20");
        producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024));
        producerProperties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        log.debug("-producerProperties()");
        return producerProperties;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        log.debug("producerFactory()");
        return new DefaultKafkaProducerFactory<>(producerProperties());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        log.debug("kafkaTemplate()");
        return new KafkaTemplate<>(producerFactory());
    }
}
