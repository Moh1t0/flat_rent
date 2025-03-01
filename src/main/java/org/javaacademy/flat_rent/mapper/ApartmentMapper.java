package org.javaacademy.flat_rent.mapper;

import org.javaacademy.flat_rent.dto.aparment.ApartmentDto;
import org.javaacademy.flat_rent.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApartmentMapper {

    @Mapping(target = "adverts", ignore = true)
    Apartment toEntity(ApartmentDto apartment);

    ApartmentDto toDto(Apartment apartment);
}
