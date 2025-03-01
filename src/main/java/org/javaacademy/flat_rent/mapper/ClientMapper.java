package org.javaacademy.flat_rent.mapper;

import org.javaacademy.flat_rent.dto.client.ClientDto;
import org.javaacademy.flat_rent.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    @Mapping(target = "bookings", ignore = true)
    Client toEntity(ClientDto client);

    ClientDto toDto(Client client);
}
