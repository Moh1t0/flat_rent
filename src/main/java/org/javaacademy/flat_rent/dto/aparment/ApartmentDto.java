package org.javaacademy.flat_rent.dto.aparment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaacademy.flat_rent.entity.ApartmentType;
import io.swagger.v3.oas.annotations.media.Schema;


@Builder
@Getter
@Setter
@Schema(description = "DTO для квартиры")
public class ApartmentDto {

    @Schema(description = "ID квартиры")
    private Integer id;

    @Schema(description = "Город")
    private String city;

    @Schema(description = "Улица")
    private String street;

    @Schema(description = "Дом")
    private String house;

    @Schema(description = "Тип квартиры")
    @JsonProperty("apartment_type")
    private ApartmentType type;
}
