package org.javaacademy.flat_rent.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoRequest;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoResponse;
import org.javaacademy.flat_rent.entity.Advert;
import org.javaacademy.flat_rent.entity.Apartment;
import org.javaacademy.flat_rent.mapper.AdvertMapper;
import org.javaacademy.flat_rent.repository.AdvertRepository;
import org.javaacademy.flat_rent.repository.ApartmentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;
    private final ApartmentRepository apartmentRepository;

    public AdvertDtoResponse save(AdvertDtoRequest advertDtoRequest) {
        Apartment apartmentId = apartmentRepository.findById(advertDtoRequest.getApartmentId()).orElseThrow();
        Advert advert = advertMapper.toEntity(advertDtoRequest, apartmentId);
        advertRepository.save(advert);
        return advertMapper.toDtoResponse(advert);
    }
}