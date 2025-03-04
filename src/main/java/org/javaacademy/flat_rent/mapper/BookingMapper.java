package org.javaacademy.flat_rent.mapper;

import org.javaacademy.flat_rent.dto.booking.BookingDtoRequest;
import org.javaacademy.flat_rent.dto.booking.BookingDtoResponse;
import org.javaacademy.flat_rent.dto.client.ClientDto;
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

import java.math.BigDecimal;

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
    @Mapping(target = "totalPrice", ignore = true)
    public abstract Booking toEntity(BookingDtoRequest bookingDtoRequest, Client client, Advert advert);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "client", qualifiedByName = "getClientById")
    @Mapping(target = "advert", source = "advertId", qualifiedByName = "getAdvertById")
    @Mapping(target = "totalPrice", ignore = true)
    public abstract Booking toEntityWithRelation(BookingDtoRequest bookingDtoRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "client")
    @Mapping(target = "advert", source = "advert")
    @Mapping(target = "totalPrice", source = "totalPrice")
    public abstract Booking createBooking(BookingDtoRequest bookingDtoRequest,
                                          Advert advert, Client client, BigDecimal totalPrice);


    @Named("getClientById")
    protected Client getClientById(ClientDto clientDto) {
        if (clientDto == null || clientDto.getId() == null) {
            throw new IllegalArgumentException("Client id is null!");
        }
        return clientRepository.findById(clientDto.getId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Client с id: %s не найден".formatted(clientDto.getId())));
    }

    @Named("getAdvertById")
    protected Advert getAdvertById(Integer id) {
        return advertRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Advert с id: %s не найдено".formatted(id))
        );
    }

    public abstract BookingDtoResponse toDtoResponse(Booking booking);
}
