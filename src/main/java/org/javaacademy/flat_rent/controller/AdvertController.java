package org.javaacademy.flat_rent.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rent.dto.PageDto;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoRequest;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoResponse;
import org.javaacademy.flat_rent.service.AdvertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/advert")
@Tag(name = "Объявление", description = "Контроллер для работы с объявлениями")
public class AdvertController {
    private final AdvertService advertService;

    @Operation(summary = "Создание объявления",
            description = "Создает новое объявление для аренды квартиры",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Объявление успешно создано",
                            content = @Content(schema = @Schema(implementation = AdvertDtoResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertDtoResponse create(@RequestBody AdvertDtoRequest dto) {
        return advertService.save(dto);
    }

    @Operation(summary = "Получение объявлений по городу",
            description = "Возвращает список объявлений для указанного города",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный запрос",
                            content = @Content(schema = @Schema(implementation = PageDto.class))),
                    @ApiResponse(responseCode = "404", description = "Город не найден")
            })
    @GetMapping
    public ResponseEntity<PageDto<AdvertDtoResponse>> getAdvertByCity(
            @RequestParam String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageDto<AdvertDtoResponse> advertsByCity = advertService.getAdvertsByCity(city, page, size);
        return ResponseEntity.ok(advertsByCity);
    }
}
