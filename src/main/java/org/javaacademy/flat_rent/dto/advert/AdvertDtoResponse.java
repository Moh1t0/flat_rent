package org.javaacademy.flat_rent.dto.advert;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaacademy.flat_rent.dto.aparment.ApartmentDto;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class AdvertDtoResponse {
    private Integer id;

    private BigDecimal price;

    @JsonProperty("is_active")
    private Boolean isActive;

    private ApartmentDto apartment;

    private String description;
}
