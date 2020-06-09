package ch.ipt.kafka.client;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.DoubleDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
public class KafkaConsumer {

    public static void main(String[] args) {

        Consumer<String, Double> consumer = createConsumer();
        //TODO: Subscribe to topics

        while (true) {
            //TODO: Poll Records
            ConsumerRecords<String, Double> consumerRecords = consumer...
            if (consumerRecords.count() == 0) {
                log.info("Could not find records.");
            }

            //print each record.
            consumerRecords.forEach(record -> {
                log.info("Record partition: {}, offset: {}, Key:  {}, value: {} ", record.partition(), record.offset(), record.key(), record.value());
            });

            // commits the offset of record to broker.
            consumer.commitAsync();
        }
    }

    public static Consumer<String, Double> createConsumer() {
        //TODO: org.apache.kafka.clients.consumer.KafkaConsumer
    }

}
