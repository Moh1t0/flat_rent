package org.javaacademy.flat_rent.mapper;

import org.javaacademy.flat_rent.dto.advert.AdvertDtoRequest;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoResponse;
import org.javaacademy.flat_rent.entity.Advert;
import org.javaacademy.flat_rent.entity.Apartment;
import org.javaacademy.flat_rent.repository.ApartmentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ApartmentMapper.class)
public abstract class AdvertMapper {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apartment", source = "apartment")
    public abstract Advert toEntity(AdvertDtoRequest advertDtoRequest, Apartment apartment);

    public Advert toEntityWithRelation(AdvertDtoRequest advertDtoRequest) {
        Apartment apartment = apartmentRepository.findById(advertDtoRequest.getApartmentId()).orElseThrow();
        return toEntity(advertDtoRequest, apartment);
    }

    public abstract AdvertDtoResponse toDtoResponse(Advert advert);
}
