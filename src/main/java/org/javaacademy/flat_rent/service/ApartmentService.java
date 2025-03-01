package org.javaacademy.flat_rent.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rent.dto.aparment.ApartmentDto;
import org.javaacademy.flat_rent.entity.Apartment;
import org.javaacademy.flat_rent.mapper.ApartmentMapper;
import org.javaacademy.flat_rent.repository.ApartmentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper apartmentMapper;

    public ApartmentDto save(ApartmentDto apartmentDto) {
        Apartment apartment = apartmentMapper.toEntity(apartmentDto);
        apartmentRepository.save(apartment);
        return apartmentMapper.toDto(apartment);
    }
}
