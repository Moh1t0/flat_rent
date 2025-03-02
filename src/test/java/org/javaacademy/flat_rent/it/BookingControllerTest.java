package org.javaacademy.flat_rent.it;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoRequest;
import org.javaacademy.flat_rent.dto.advert.AdvertDtoResponse;
import org.javaacademy.flat_rent.dto.booking.BookingDtoRequest;
import org.javaacademy.flat_rent.dto.booking.BookingDtoResponse;
import org.javaacademy.flat_rent.dto.client.ClientDto;
import org.javaacademy.flat_rent.entity.Apartment;
import org.javaacademy.flat_rent.entity.ApartmentType;
import org.javaacademy.flat_rent.entity.Client;
import org.javaacademy.flat_rent.repository.BookingRepository;
import org.javaacademy.flat_rent.repository.ClientRepository;
import org.javaacademy.flat_rent.repository.ApartmentRepository;
import org.javaacademy.flat_rent.service.AdvertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "classpath:clean_db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BookingControllerTest {

    private static final String CLIENT_NAME = "test name";
    private static final String CLIENT_EMAIL = "test@email.com";
    private static final String NEW_CLIENT_NAME = "new test name";
    private static final String NEW_CLIENT_EMAIL = "newtest@email.com";
    private static final String CITY = "test city";
    private static final String STREET = "test street";
    private static final String HOUSE = "test house";
    private static final ApartmentType APARTMENT_TYPE = ApartmentType.ONE_ROOM;
    private static final BigDecimal PRICE = BigDecimal.TEN;
    private static final String DESCRIPTION = "test advert";
    private static final LocalDateTime START_DATE = LocalDateTime.of(
            2025, 3, 5, 0, 0);
    private static final LocalDateTime END_DATE = LocalDateTime.of(
            2025, 3, 6, 0, 0);
    private static final LocalDateTime NEW_START_DATE = LocalDateTime.of(
            2025, 3, 10, 14, 0);
    private static final LocalDateTime NEW_END_DATE = LocalDateTime.of(
            2025, 3, 15, 12, 0);
    private static final LocalDateTime DATE_4_2_3_START = LocalDateTime.of(
            2025, 9, 29, 0, 0);
    private static final LocalDateTime DATE_4_2_3_END = LocalDateTime.of(
            2025, 10, 2, 0, 0);
    private static final LocalDateTime DATE_4_2_4_START = LocalDateTime.of(
            2025, 10, 9, 0, 0);
    private static final LocalDateTime DATE_4_2_4_END = LocalDateTime.of(
            2025, 10, 11, 0, 0);

    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/booking")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private AdvertService advertService;

    private Client savedClient;
    private AdvertDtoResponse advertDtoResponse;

    @BeforeEach
    public void setUp() {
        savedClient = clientRepository.save(Client.builder()
                .name(CLIENT_NAME)
                .email(CLIENT_EMAIL)
                .build());

        Apartment savedApartment = apartmentRepository.save(Apartment.builder()
                .city(CITY)
                .street(STREET)
                .house(HOUSE)
                .type(APARTMENT_TYPE)
                .build());

        AdvertDtoRequest advertDtoRequest = AdvertDtoRequest.builder()
                .price(PRICE)
                .description(DESCRIPTION)
                .apartmentId(savedApartment.getId())
                .isActive(true)
                .build();
        advertDtoResponse = advertService.save(advertDtoRequest);
    }

    @Test
    @DisplayName("Успешное бронирование, при незаполненности id у клиента. Проверить что создан новый клиент")
    public void createSuccessWithoutClientId() {
        BookingDtoRequest bookingDtoRequest = BookingDtoRequest.builder()
                .advertId(advertDtoResponse.getId())
                .startDate(NEW_START_DATE)
                .endDate(NEW_END_DATE)
                .client(ClientDto.builder()
                        .name(NEW_CLIENT_NAME)
                        .email(NEW_CLIENT_EMAIL)
                        .build())
                .build();

        BookingDtoResponse response = given(requestSpecification)
                .body(bookingDtoRequest)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookingDtoResponse.class);

        assertNotNull(response);
        assertNotNull(response.getId());

        Optional<Client> client = clientRepository.findByEmail(NEW_CLIENT_EMAIL);
        assertTrue(client.isPresent());
        assertEquals(NEW_CLIENT_NAME, client.get().getName());
    }

    @Test
    @DisplayName("Успешное бронирование, при указанном id у клиента")
    public void createSuccessWithClientId() {
        BookingDtoRequest bookingDtoRequest = BookingDtoRequest.builder()
                .advertId(advertDtoResponse.getId())
                .startDate(NEW_START_DATE)
                .endDate(NEW_END_DATE)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        BookingDtoResponse response = given(requestSpecification)
                .body(bookingDtoRequest)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookingDtoResponse.class);

        assertNotNull(response);
        assertNotNull(response.getId());

        Optional<Client> client = clientRepository.findByEmail(savedClient.getEmail());
        assertTrue(client.isPresent());
        assertEquals(savedClient.getId(), client.get().getId());
    }

    @Test
    @DisplayName("Неуспешное бронирование при существующем бронировании на эти даты (случай 4.2.2)")
    public void bookingFailsIfDatesOverlapFirstSituation() {
        BookingDtoRequest firstBookingRequest = BookingDtoRequest.builder()
                .advertId(advertDtoResponse.getId())
                .startDate(START_DATE)
                .endDate(END_DATE)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        BookingDtoResponse firstBookingResponse = given(requestSpecification)
                .body(firstBookingRequest)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookingDtoResponse.class);

        assertNotNull(firstBookingResponse);
        assertNotNull(firstBookingResponse.getId());

        BookingDtoRequest overlappingBookingRequest = BookingDtoRequest.builder()
                .advertId(advertDtoResponse.getId())
                .startDate(START_DATE)
                .endDate(END_DATE)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        given(requestSpecification)
                .body(overlappingBookingRequest)
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Неуспешное бронирование при существующем бронировании на эти даты (случай 4.2.3)")
    public void bookingFailsIfDatesOverlapCaseSecondSituation() {
        BookingDtoRequest firstBookingRequest = BookingDtoRequest.builder()
                .advertId(advertDtoResponse.getId())
                .startDate(DATE_4_2_3_START)
                .endDate(DATE_4_2_3_END)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        BookingDtoResponse firstBookingResponse = given(requestSpecification)
                .body(firstBookingRequest)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookingDtoResponse.class);

        assertNotNull(firstBookingResponse);
        assertNotNull(firstBookingResponse.getId());

        BookingDtoRequest overlappingBookingRequest = BookingDtoRequest.builder()
                .advertId(advertDtoResponse.getId())
                .startDate(DATE_4_2_3_START)
                .endDate(DATE_4_2_3_END)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        given(requestSpecification)
                .body(overlappingBookingRequest)
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Неуспешное бронирование при существующем бронировании на эти даты (случай 4.2.4)")
    public void bookingFailsIfDatesOverlapCaseThirdSituation() {
        BookingDtoRequest firstBookingRequest = BookingDtoRequest.builder()
                .advertId(advertDtoResponse.getId())
                .startDate(DATE_4_2_4_START)
                .endDate(DATE_4_2_4_END)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        BookingDtoResponse firstBookingResponse = given(requestSpecification)
                .body(firstBookingRequest)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookingDtoResponse.class);

        assertNotNull(firstBookingResponse);
        assertNotNull(firstBookingResponse.getId());

        BookingDtoRequest overlappingBookingRequest = BookingDtoRequest.builder()
                .advertId(advertDtoResponse.getId())
                .startDate(DATE_4_2_4_START)
                .endDate(DATE_4_2_4_END)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        given(requestSpecification)
                .body(overlappingBookingRequest)
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
