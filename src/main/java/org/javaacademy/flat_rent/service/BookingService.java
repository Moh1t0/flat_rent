package org.javaacademy.flat_rent.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.flat_rent.dto.PageDto;
import org.javaacademy.flat_rent.dto.booking.BookingDtoRequest;
import org.javaacademy.flat_rent.dto.booking.BookingDtoResponse;
import org.javaacademy.flat_rent.dto.client.ClientDto;
import org.javaacademy.flat_rent.entity.Advert;
import org.javaacademy.flat_rent.entity.Booking;
import org.javaacademy.flat_rent.entity.Client;
import org.javaacademy.flat_rent.mapper.BookingMapper;
import org.javaacademy.flat_rent.mapper.ClientMapper;
import org.javaacademy.flat_rent.repository.AdvertRepository;
import org.javaacademy.flat_rent.repository.BookingRepository;
import org.javaacademy.flat_rent.repository.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final AdvertRepository advertRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public BookingDtoResponse save(BookingDtoRequest bookingDtoRequest) {
        try {
            Advert advert = checkAdvert(bookingDtoRequest.getAdvertId());
            validateBookingDates(advert, bookingDtoRequest.getStartDate(), bookingDtoRequest.getEndDate());
            Client client = checkClient(bookingDtoRequest.getClient());
            BigDecimal totalPrice = calculateTotalPrice(
                    advert.getPrice(),
                    bookingDtoRequest.getStartDate(),
                    bookingDtoRequest.getEndDate());
            Booking booking = createBooking(bookingDtoRequest, advert, client, totalPrice);
            Booking savedBooking = bookingRepository.save(booking);
            return bookingMapper.toDtoResponse(savedBooking);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    public PageDto<BookingDtoResponse> getBookingsByClientEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage = bookingRepository.findAllByClientEmail(email, pageable);

        List<BookingDtoResponse> bookings = bookingPage.getContent().stream()
                .map(bookingMapper::toDtoResponse)
                .toList();

        return new PageDto<>(
                bookings,
                bookingPage.getNumber(),
                bookingPage.getSize(),
                bookingPage.getTotalElements(),
                bookingPage.getTotalPages()
        );
    }

    private Advert checkAdvert(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Advert is null!");
        }
        return advertRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Advert не найден!"));
    }

    private Client checkClient(ClientDto clientDto) {
        if (clientDto == null) {
            throw new IllegalArgumentException("Client is null!");
        }

        if (clientDto.getId() != null) {
            return clientRepository.findById(clientDto.getId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Client с id " + clientDto.getId() + " не найден!"));
        }

        Client newClient = clientMapper.toEntity(clientDto);
        return clientRepository.save(newClient);
    }

    private Booking createBooking(BookingDtoRequest bookingDtoRequest,
                                  Advert advert, Client client, BigDecimal totalPrice) {
        return bookingMapper.createBooking(bookingDtoRequest, advert, client, totalPrice);
    }

    private void validateBookingDates(Advert advert, LocalDate startDate, LocalDate endDate) {
        if (advertRepository.existsByAdvertAndDatesOverlap(advert, startDate, endDate)) {
            throw new IllegalArgumentException("Даты бронирования совпадают с существующими бронированиями");
        }
    }

    private BigDecimal calculateTotalPrice(BigDecimal pricePerNight, LocalDate startDate, LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return pricePerNight.multiply(BigDecimal.valueOf(daysBetween));
    }
}
