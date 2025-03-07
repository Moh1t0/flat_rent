package org.javaacademy.flat_rent.it;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.javaacademy.flat_rent.dto.aparment.ApartmentDto;
import org.javaacademy.flat_rent.entity.ApartmentType;
import org.javaacademy.flat_rent.repository.ApartmentRepository;
import org.javaacademy.flat_rent.service.ApartmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "classpath:clean_db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ApartmentControllerTest {

    private static final String CITY = "test city";
    private static final String STREET = "test street";
    private static final String HOUSE = "test house";
    private static final ApartmentType APARTMENT_TYPE = ApartmentType.ONE_ROOM;

    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/apartment")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();

    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Test
    @DisplayName("Успешное создание помещения")
    public void createSuccess() {
        ApartmentDto apartmentDto = ApartmentDto.builder()
                .city(CITY)
                .street(STREET)
                .house(HOUSE)
                .type(APARTMENT_TYPE)
                .build();

        ApartmentDto result = given(requestSpecification)
                .body(apartmentDto)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(CREATED.value())
                .extract()
                .as(ApartmentDto.class);

        assertNotNull(result.getId());
        assertEquals(CITY, result.getCity());
        assertEquals(HOUSE, result.getHouse());
        assertEquals(APARTMENT_TYPE, result.getType());
        assertEquals(1, apartmentRepository.findAll().size());
    }
}
