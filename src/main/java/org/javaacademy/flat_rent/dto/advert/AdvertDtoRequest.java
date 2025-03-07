package org.javaacademy.flat_rent.dto.advert;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;


@Builder
@Getter
@Setter
@Schema(description = "DTO для создания объявления")
public class AdvertDtoRequest {

    @Schema(description = "ID объявления")
    private Integer id;

    @Schema(description = "Цена аренды")
    private BigDecimal price;

    @Schema(description = "Активно ли объявление")
    @JsonProperty("is_active")
    private Boolean isActive;

    @Schema(description = "ID квартиры")
    @JsonProperty("apartment_id")
    private Integer apartmentId;

    @Schema(description = "Описание объявления")
    private String description;
}
