package org.javaacademy.flat_rent.mapper;

import org.javaacademy.flat_rent.dto.booking.BookingDtoRequest;
import org.javaacademy.flat_rent.dto.booking.BookingDtoResponse;
import org.javaacademy.flat_rent.entity.Advert;
import org.javaacademy.flat_rent.entity.Booking;
import org.javaacademy.flat_rent.entity.Client;
import org.javaacademy.flat_rent.repository.AdvertRepository;
import org.javaacademy.flat_rent.repository.ClientRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ClientMapper.class, AdvertMapper.class})
public abstract class BookingMapper {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdvertRepository advertRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "client")
    @Mapping(target = "advert", source = "advert")
    public abstract Booking toEntity(BookingDtoRequest bookingDtoRequest, Client client, Advert advert);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "clientId", qualifiedByName = "getClientById")
    @Mapping(target = "advert", source = "advertId", qualifiedByName = "getAdvertById")
    public abstract Booking toEntityWithRelation(BookingDtoRequest bookingDtoRequest);

    @Named("getClientById")
    protected Client getClientById(Integer id) {
        return clientRepository.findById(id).orElseThrow();
    }

    @Named("getAdvertById")
    protected Advert getAdvertById(Integer id) {
        return advertRepository.findById(id).orElseThrow();
    }

    public abstract BookingDtoResponse toDtoResponse(Booking booking);
}
