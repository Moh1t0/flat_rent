package org.javaacademy.flat_rent.controller;

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
public class AdvertController {
    private final AdvertService advertService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AdvertDtoResponse> create(@RequestBody AdvertDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(advertService.save(dto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<PageDto<AdvertDtoResponse>> getAdvertByCity(
            @RequestParam String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageDto<AdvertDtoResponse> advertsByCity = advertService.getAdvertsByCity(city, page, size);
        return ResponseEntity.ok(advertsByCity);
    }
}