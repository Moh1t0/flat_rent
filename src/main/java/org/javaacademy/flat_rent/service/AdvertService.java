package org.javaacademy.flat_rent.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rent.dto.PageDto;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoRequest;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoResponse;
import org.javaacademy.flat_rent.entity.Advert;
import org.javaacademy.flat_rent.mapper.AdvertMapper;
import org.javaacademy.flat_rent.repository.AdvertRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;
    private static final String PRICE_COLUMN = "price";

    public AdvertDtoResponse save(AdvertDtoRequest advertDtoRequest) {
        Advert entity = advertMapper.toEntityWithRelation(advertDtoRequest);
        advertRepository.save(entity);
        return advertMapper.toDtoResponse(entity);
    }

    public PageDto<AdvertDtoResponse> getAdvertsByCity(String city, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, PRICE_COLUMN));
        Page<Advert> advertPage = advertRepository.findByApartmentCity(city, pageable);

        List<AdvertDtoResponse> list = advertPage.getContent().stream()
                .map(advertMapper::toDtoResponse)
                .toList();

        return new PageDto<>(list,
                advertPage.getNumber(),
                advertPage.getSize(),
                advertPage.getTotalElements(),
                advertPage.getTotalPages());
    }
}
