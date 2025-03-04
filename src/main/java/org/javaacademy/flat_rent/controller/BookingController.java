package org.javaacademy.flat_rent.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rent.dto.PageDto;
import org.javaacademy.flat_rent.dto.booking.BookingDtoRequest;
import org.javaacademy.flat_rent.dto.booking.BookingDtoResponse;
import org.javaacademy.flat_rent.service.BookingService;
import org.javaacademy.flat_rent.service.ClientService;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
@Tag(name = "Бронирование", description = "Контроллер для работы с бронированиями")
public class BookingController {
    private final BookingService bookingService;
    private final ClientService clientService;

    @Operation(summary = "Создание бронирования",
            description = "Создает новое бронирование для клиента",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Бронирование успешно создано",
                            content = @Content(schema = @Schema(implementation = BookingDtoResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDtoResponse createBooking(@RequestBody BookingDtoRequest dtoRequest) {
        return bookingService.save(dtoRequest);
    }

    @Operation(summary = "Получение бронирований по email клиента",
            description = "Возвращает список бронирований для указанного email клиента",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный запрос",
                            content = @Content(schema = @Schema(implementation = PageDto.class))),
                    @ApiResponse(responseCode = "404", description = "Клиент не найден")
            })
    @GetMapping
    public PageDto<BookingDtoResponse> getBookingByClientEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        return bookingService.getBookingsByClientEmail(email, page, size);

    }
}
