package org.javaacademy.flat_rent.dto.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaacademy.flat_rent.dto.client.ClientDto;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class BookingDtoRequest {
    private Integer id;

    @JsonProperty("client")
    private ClientDto client;

    @JsonProperty("advert_id")
    private Integer advertId;

    @JsonProperty("date_start")
    private LocalDate startDate;

    @JsonProperty("date_finish")
    private LocalDate endDate;
}
