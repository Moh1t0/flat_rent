package org.javaacademy.flat_rent.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rent.dto.aparment.ApartmentDto;
import org.javaacademy.flat_rent.service.ApartmentService;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/apartment")
@Tag(name = "Квартира", description = "Контроллер для работы с квартирами")
public class ApartmentController {
    private final ApartmentService apartmentService;

    @Operation(summary = "Создание квартиры",
            description = "Создает новую квартиру",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Квартира успешно создана",
                            content = @Content(schema = @Schema(implementation = ApartmentDto.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApartmentDto create(@RequestBody ApartmentDto dto) {
        return apartmentService.save(dto);
    }
}
