package org.javaacademy.flat_rent.it;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.javaacademy.flat_rent.dto.PageDto;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoRequest;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoResponse;
import org.javaacademy.flat_rent.entity.Advert;
import org.javaacademy.flat_rent.entity.Apartment;
import org.javaacademy.flat_rent.entity.ApartmentType;
import org.javaacademy.flat_rent.repository.AdvertRepository;
import org.javaacademy.flat_rent.repository.ApartmentRepository;
import org.javaacademy.flat_rent.service.AdvertService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "classpath:clean_db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AdvertControllerTest {

    private static final String CITY = "test city";
    private static final String STREET = "test street";
    private static final String HOUSE = "test house";
    private static final ApartmentType APARTMENT_TYPE = ApartmentType.ONE_ROOM;
    private static final BigDecimal PRICE = BigDecimal.TEN;
    private static final String DESCRIPTION = "test desc";
    private static final BigDecimal FIRST_ADVERT_PRICE = BigDecimal.valueOf(1000);
    private static final BigDecimal SECOND_ADVERT_PRICE = BigDecimal.valueOf(1500);
    private static final String FIRST_ADVERT_DESCRIPTION = "first";
    private static final String SECOND_ADVERT_DESCRIPTION = "second";
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 10;
    private static final int EXPECTED_ADVERT_COUNT = 2;

    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/advert")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();

    @Autowired
    private AdvertService advertService;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private AdvertRepository advertRepository;

    @Test
    @DisplayName("Успешное создание объявления")
    public void createSuccess() {
        Apartment apartment = apartmentRepository.save(Apartment.builder()
                .city(CITY)
                .street(STREET)
                .house(HOUSE)
                .type(APARTMENT_TYPE)
                .build());

        AdvertDtoRequest advertDtoRequest = AdvertDtoRequest.builder()
                .price(PRICE)
                .description(DESCRIPTION)
                .apartmentId(apartment.getId())
                .isActive(true)
                .build();

        AdvertDtoResponse response = given(requestSpecification)
                .body(advertDtoRequest)
                .post()
                .then()
                .spec(responseSpecification)
                .extract()
                .as(AdvertDtoResponse.class);

        assertNotNull(response);
        assertEquals(PRICE, response.getPrice());
        assertEquals(DESCRIPTION, response.getDescription());
        assertEquals(apartment.getId(), response.getApartment().getId());
        assertTrue(response.getIsActive());
    }

    @Test
    @DisplayName("Успешное получение объявлений по городу")
    public void getAdvertByCitySuccess() {
        Apartment apartment = apartmentRepository.save(Apartment.builder()
                .city(CITY)
                .street("main street")
                .house(HOUSE)
                .type(APARTMENT_TYPE)
                .build());

        advertRepository.saveAll(List.of(
                Advert.builder().apartment(apartment)
                        .price(FIRST_ADVERT_PRICE)
                        .description(FIRST_ADVERT_DESCRIPTION)
                        .isActive(true)
                        .build(),
                Advert.builder().apartment(apartment)
                        .price(SECOND_ADVERT_PRICE)
                        .description(SECOND_ADVERT_DESCRIPTION)
                        .isActive(true)
                        .build()
        ));

        PageDto<AdvertDtoResponse> response = given(requestSpecification)
                .queryParam("city", CITY)
                .queryParam("page", PAGE_NUMBER)
                .queryParam("size", PAGE_SIZE)
                .get()
                .then()
                .spec(responseSpecification)
                .statusCode(OK.value())
                .extract()
                .as(new TypeRef<>() {
                });

        assertNotNull(response);
        assertFalse(response.getContent().isEmpty());
        assertEquals(PAGE_NUMBER, response.getPageNumber());
        assertEquals(PAGE_SIZE, response.getPageSize());
        assertEquals(EXPECTED_ADVERT_COUNT, response.getContent().size());
    }
}
