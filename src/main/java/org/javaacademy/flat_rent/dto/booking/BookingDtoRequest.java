package org.javaacademy.flat_rent.dto.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaacademy.flat_rent.dto.client.ClientDto;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Getter
@Setter
@Schema(description = "DTO для создания бронирования")
public class BookingDtoRequest {

    @Schema(description = "ID бронирования")
    private Integer id;

    @Schema(description = "Клиент")
    @JsonProperty("client")
    private ClientDto client;

    @Schema(description = "ID объявления")
    @JsonProperty("advert_id")
    private Integer advertId;

    @Schema(description = "Дата начала бронирования")
    @JsonProperty("date_start")
    private LocalDateTime startDate;

    @Schema(description = "Дата окончания бронирования")
    @JsonProperty("date_finish")
    private LocalDateTime endDate;
}
