package org.javaacademy.flat_rent.mapper;

import org.javaacademy.flat_rent.dto.advert.AdvertDtoRequest;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoResponse;
import org.javaacademy.flat_rent.entity.Advert;
import org.javaacademy.flat_rent.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdvertMapper {

    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apartment", source = "apartment")
    Advert toEntity(AdvertDtoRequest advertDtoRequest, Apartment apartment);

    AdvertDtoResponse toDtoResponse(Advert advert);
}
