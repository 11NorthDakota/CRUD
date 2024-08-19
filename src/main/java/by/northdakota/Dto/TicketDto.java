package by.northdakota.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto{
    private Long id;
    private Long flightId;
    private String seatNo;
}
