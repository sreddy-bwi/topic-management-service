package com.smartshare.user_management.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {

    @Bean
    public Map<String, Object> producerConfigs() {
        var properties = new HashMap<String, Object>();
        properties.put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092" );
        properties.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class );
        properties.put( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class );
        return properties;
    }

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>( producerConfigs() );
    }


    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate() {
        return new KafkaTemplate<>( producerFactory() );
    }

}
