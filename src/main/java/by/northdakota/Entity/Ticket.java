package by.northdakota.Entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Ticket {
    private Long id;
    private String passenger_no;
    private String passenger_name;
    private Long flightId;
    private String seatNo;
    private BigDecimal cost;
}
