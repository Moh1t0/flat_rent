package org.javaacademy.flat_rent.dto.advert;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class AdvertDtoRequest {
    private Integer id;

    private BigDecimal price;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("apartment_id")
    private Integer apartmentId;

    private String description;
}
