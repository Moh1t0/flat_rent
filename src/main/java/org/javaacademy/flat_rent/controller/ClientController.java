package org.javaacademy.flat_rent.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rent.dto.client.ClientDto;
import org.javaacademy.flat_rent.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/client")
@Tag(name = "Клиент", description = "Контроллер для работы с клиентами")
public class ClientController {
    private final ClientService clientService;

    @Operation(summary = "Создание клиента",
            description = "Создает нового клиента",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Клиент успешно создан",
                            content = @Content(schema = @Schema(implementation = ClientDto.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            })
    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto) {
        ClientDto savedClient = clientService.save(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @Operation(summary = "Удаление клиента",
            description = "Удаляет клиента по его ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Клиент успешно удален"),
                    @ApiResponse(responseCode = "404", description = "Клиент не найден")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }
}
