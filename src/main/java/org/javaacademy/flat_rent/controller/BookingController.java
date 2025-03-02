package org.javaacademy.flat_rent.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rent.dto.PageDto;
import org.javaacademy.flat_rent.dto.booking.BookingDtoRequest;
import org.javaacademy.flat_rent.dto.booking.BookingDtoResponse;
import org.javaacademy.flat_rent.service.BookingService;
import org.javaacademy.flat_rent.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<BookingDtoResponse> createBooking(@RequestBody BookingDtoRequest dtoRequest) {
        BookingDtoResponse bookingDtoResponse = bookingService.save(dtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingDtoResponse);
    }

    @GetMapping
    public ResponseEntity<PageDto<BookingDtoResponse>> getBookingByClientEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageDto<BookingDtoResponse> bookingsByClientEmail = bookingService.getBookingsByClientEmail(email, page, size);
        return ResponseEntity.ok(bookingsByClientEmail);
    }
}
