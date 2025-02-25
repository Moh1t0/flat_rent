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
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ClientMapper.class, AdvertMapper.class})
public abstract class BookingMapper {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdvertRepository advertRepository;

    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "client")
    @Mapping(target = "advert", source = "advert")
    public abstract Booking toEntity(BookingDtoRequest bookingDtoRequest, Client client, Advert advert);

    public Booking toEntityWithRelation(BookingDtoRequest bookingDtoRequest) {
        Client client = clientRepository.findById(bookingDtoRequest.getClientId()).orElseThrow();
        Advert advert = advertRepository.findById(bookingDtoRequest.getAdvertId()).orElseThrow();
        return toEntity(bookingDtoRequest, client, advert);
    }

    public abstract BookingDtoResponse toDtoResponse(Booking booking);
}





























