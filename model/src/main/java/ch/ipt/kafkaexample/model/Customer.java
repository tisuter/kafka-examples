package ch.ipt.kafkaexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private String customerId;
    private LocalDateTime timestamp;
    private String name;
    private String status;

}
