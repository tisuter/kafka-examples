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

    public static final String TOPIC = "random-numbers";

    public static void main(String[] args) {

        Consumer<String, Double> consumer = createConsumer();
        consumer.subscribe(Collections.singletonList(KafkaProducer.TOPIC));
        while (true) {
            ConsumerRecords<String, Double> consumerRecords = consumer.poll(Duration.ofSeconds(1));
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
        Properties props = new Properties();


        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "kafka-clients-producer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-clients-producer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DoubleDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        return new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
    }

}
