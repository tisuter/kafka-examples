package ch.ipt.kafkaexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTransaction {

    private String customerId;
    private LocalDateTime timestamp;
    private Double ammount;
    private String information;
    private String recipientId;

}
