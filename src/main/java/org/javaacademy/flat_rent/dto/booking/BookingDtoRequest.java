package org.javaacademy.flat_rent.dto.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class BookingDtoRequest {
    private Integer id;

    @JsonProperty("client_id")
    private Integer clientId;

    @JsonProperty("advert_id")
    private Integer advertId;

    @JsonProperty("date_start")
    private LocalDate startDate;

    @JsonProperty("date_finish")
    private LocalDate endDate;
}
