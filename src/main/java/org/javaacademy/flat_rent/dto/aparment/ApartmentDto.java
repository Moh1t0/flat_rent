package org.javaacademy.flat_rent.dto.aparment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaacademy.flat_rent.entity.ApartmentType;


@Builder
@Getter
@Setter
public class ApartmentDto {
    private Integer id;
    private String city;
    private String street;
    private String house;
    @JsonProperty("apartment_type")
    private ApartmentType type;
}
