package org.javaacademy.flat_rent.mapper;

import org.javaacademy.flat_rent.dto.advert.AdvertDtoRequest;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoResponse;
import org.javaacademy.flat_rent.entity.Advert;
import org.javaacademy.flat_rent.entity.Apartment;
import org.javaacademy.flat_rent.repository.ApartmentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ApartmentMapper.class)
public abstract class AdvertMapper {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apartment", source = "apartment")
    public abstract Advert toEntity(AdvertDtoRequest advertDtoRequest, Apartment apartment);

    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apartment", source = "apartmentId", qualifiedByName = "getApartmentById")
    public abstract Advert toEntityWithRelation(AdvertDtoRequest advertDtoRequest);

    @Named("getApartmentById")
    protected Apartment getApartmentById(Integer id) {
        return apartmentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Apartment с id: %s не найдено".formatted(id)));
    }

    public abstract AdvertDtoResponse toDtoResponse(Advert advert);
}
