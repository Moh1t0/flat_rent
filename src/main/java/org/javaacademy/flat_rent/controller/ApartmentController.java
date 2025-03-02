package org.javaacademy.flat_rent.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rent.dto.aparment.ApartmentDto;
import org.javaacademy.flat_rent.service.ApartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


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
    public ResponseEntity<ApartmentDto> create(@RequestBody ApartmentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apartmentService.save(dto));
    }
}
