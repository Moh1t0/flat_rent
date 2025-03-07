package org.javaacademy.flat_rent.dto.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;


@Builder
@Getter
@Setter
@Schema(description = "DTO для клиента")
public class ClientDto {

    @Schema(description = "ID клиента")
    private Integer id;

    @Schema(description = "Имя клиента")
    private String name;

    @Schema(description = "Email клиента")
    private String email;
}
