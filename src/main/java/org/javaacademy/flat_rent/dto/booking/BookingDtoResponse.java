package org.javaacademy.flat_rent.dto.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoResponse;
import org.javaacademy.flat_rent.dto.client.ClientDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class BookingDtoResponse {

    private Integer id;

    @JsonProperty("client")
    private ClientDto client;

    @JsonProperty("advert")
    private AdvertDtoResponse advert;

    @JsonProperty("date_start")
    private LocalDateTime startDate;

    @JsonProperty("date_finish")
    private LocalDateTime endDate;

    @JsonProperty("result_price")
    private BigDecimal totalPrice;
}
