package ch.ipt.kafka.spring;

import ch.ipt.kafkaexample.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    public static final String TOPIC_CUSTOMER = "input.customer";

    private final long secondsInAMonth;
    private final LocalDateTime now;
    private final static int maxCustomerId = 1_000_000;
    private final static String STATUS = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
    private final ObjectMapper om;

    public Producer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper om) {
        this.kafkaTemplate = kafkaTemplate;
        this.om = om;

        now = LocalDateTime.now();
        LocalDateTime lastMonth = now.minusDays(31);
        Duration oneMonth = Duration.between(lastMonth, now);
        secondsInAMonth = oneMonth.getSeconds();
    }


    @Scheduled(cron = "*/5 * * * * *")
    public void createMessage() {
        sendCustomer(1);
    }

    private void sendCustomer(int numberOfMessages) {
        try {
            for (int i = 0; i < numberOfMessages; i++) {
                Customer randomCustomer = createRandomCustomer();
                String message = om.writeValueAsString(randomCustomer);
                log.info("create record on topic {}: {}", TOPIC_CUSTOMER, message);
                kafkaTemplate.send(TOPIC_CUSTOMER, randomCustomer.getCustomerId(), message);
            }
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Customer createRandomCustomer() {
        double customerIdRand = Math.random();
        double dateRand = Math.random();

        int customerId = (int) Math.floor(customerIdRand * maxCustomerId) + 1;
        LocalDateTime date = now.minusSeconds(Math.round(dateRand * secondsInAMonth));

        return new Customer(customerId + "", date, "Customer " + customerId, STATUS);
    }

}
