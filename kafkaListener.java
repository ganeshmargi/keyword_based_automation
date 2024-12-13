package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.config.MessageListenerEndpoint;
import org.springframework.kafka.listener.config.SimpleMessageListenerEndpoint;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.Acknowledgment;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaListenerConfig {

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-broker:9093");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "your-group-id");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        
        // SSL Configuration for mTLS
        consumerProps.put("security.protocol", "SSL");
        consumerProps.put("ssl.keystore.location", "/path/to/kafka.client.keystore.jks");
        consumerProps.put("ssl.keystore.password", "<client-keystore-password>");
        consumerProps.put("ssl.truststore.location", "/path/to/kafka.client.truststore.jks");
        consumerProps.put("ssl.truststore.password", "<client-truststore-password>");
        consumerProps.put("ssl.client.auth", "required");

        return new DefaultKafkaConsumerFactory<>(consumerProps);
    }

    @Bean
    public SimpleMessageListenerEndpoint messageListenerEndpoint() {
        SimpleMessageListenerEndpoint endpoint = new SimpleMessageListenerEndpoint();
        endpoint.setId("myKafkaListener");
        endpoint.setTopic("your-topic");

        // Define the MessageListener with acknowledgment
        endpoint.setMessageListener(new MessageListener<String, String>() {
            @Override
            public void onMessage(String message) {
                // Print the received message to the console
                System.out.println("Received message: " + message);
                
                // Perform additional processing logic here
                
                // Acknowledge the message manually
                // The acknowledgment is done here
                // (After the message is processed successfully)
                acknowledgment.acknowledge();
            }
        });

        return endpoint;
    }

    // MessageListenerContainer to manage the consumer
    @Bean
    public MessageListenerContainer messageListenerContainer(SimpleMessageListenerEndpoint messageListenerEndpoint,
                                                             ConsumerFactory<String, String> consumerFactory) {
        return new ConcurrentMessageListenerContainer<>(consumerFactory, messageListenerEndpoint);
    }
}
