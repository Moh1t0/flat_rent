package org.javaacademy.flat_rent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO для пагинации")
public class PageDto<T> {

    @Schema(description = "Список элементов")
    private List<T> content;

    @Schema(description = "Номер страницы")
    private int pageNumber;

    @Schema(description = "Размер страницы")
    private Integer pageSize;

    @Schema(description = "Общее количество элементов")
    private long totalElements;

    @Schema(description = "Общее количество страниц")
    private int totalPages;
}
