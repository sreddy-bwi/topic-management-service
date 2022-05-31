package com.smartshare.user_management.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {


    @Bean
    public Map<String, Object> consumerConfigs() {
        var properties = new HashMap<String, Object>();
        properties.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092" );
        properties.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class );
        properties.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
        properties.put( ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RoundRobinAssignor" );
        return properties;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>( consumerConfigs() );
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory() {
        var kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<Integer, String>();
        kafkaListenerContainerFactory.setConsumerFactory( consumerFactory() );
        kafkaListenerContainerFactory.setConcurrency( 3 );
        kafkaListenerContainerFactory.getContainerProperties().setPollTimeout( 3000 );
        return kafkaListenerContainerFactory;
    }


}
