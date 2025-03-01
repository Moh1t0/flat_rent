package org.javaacademy.flat_rent.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rent.dto.booking.BookingDtoRequest;
import org.javaacademy.flat_rent.dto.booking.BookingDtoResponse;
import org.javaacademy.flat_rent.entity.Booking;
import org.javaacademy.flat_rent.mapper.BookingMapper;
import org.javaacademy.flat_rent.repository.BookingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;

    public BookingDtoResponse save(BookingDtoRequest bookingDtoRequest) {
        Booking booking = bookingMapper.toEntityWithRelation(bookingDtoRequest);
        bookingRepository.save(booking);
        return bookingMapper.toDtoResponse(booking);
    }
}
