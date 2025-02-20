package org.javaacademy.flat_rent.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApartmentType {
    ONLY_ROOM("Комната"),
    ONE_ROOM("Однакомнатная квартира"),
    TWO_ROOM("Двухкомнатная квартира"),
    THREE_ROOM("Трехкомнатная квартира"),
    FOUR_OR_MORE_ROOM("Четерех и более комнатная квартира");

    private final String description;
}
