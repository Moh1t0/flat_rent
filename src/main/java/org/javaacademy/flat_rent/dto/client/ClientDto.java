package org.javaacademy.flat_rent.dto.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClientDto {
    private Integer id;
    private String name;
    private String email;
}
