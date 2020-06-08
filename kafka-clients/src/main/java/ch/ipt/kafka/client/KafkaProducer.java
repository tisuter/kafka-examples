package ch.ipt.kafka.client;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
public class KafkaProducer {

    public static final String TOPIC = "random-numbers";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Producer<String, Double> producer = createProducer();

        for (int index = 0; index < 100; index++) {
            double random = Math.random() * 1000;

            String key = String.valueOf((int) random);
            ProducerRecord<String, Double> record = new ProducerRecord<>(TOPIC, key, random);
            RecordMetadata metadata = producer.send(record).get();
            log.info("Record sent with key {} to partition {} with offset {}", key, metadata.partition(), metadata.offset());
        }
    }

    public static Producer<String, Double> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "kafka-clients-producer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DoubleSerializer.class.getName());
        return new org.apache.kafka.clients.producer.KafkaProducer<>(props);
    }

}
