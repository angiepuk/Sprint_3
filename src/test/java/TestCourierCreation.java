
import POJO.CourierForCreation;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.scooter.api.EndPoint.COURIER;

public class TestCourierCreation {


    //курьера можно создать
    @Test
       public void create_courier_return_200_Test() {

        CourierForCreation courierForCreation = CourierForCreation.getRandomCourier();

           boolean ok = given()
                   .header("Content-Type", "application/json")
                   .log().all()
                   .body(courierForCreation)
                   .when()
                   .post(baseURI + COURIER)
                   .then()
                   .log().all()
                   .statusCode(SC_CREATED)
                   .extract()
                   .path("ok");
           assertTrue(ok);
       }

//нельзя создать двух одинаковых курьеров
    @Test
    public void cannot_create_two_identical_couriers(){

        CourierForCreation courierForCreation = CourierForCreation.getRandomCourier();

        String error = "Этот логин уже используется. Попробуйте другой.";
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierForCreation)
                .when()
                .post(baseURI + COURIER)
                .then()
                .log().all()
                .statusCode(SC_CREATED);

       String response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierForCreation)
                .when()
                .post(baseURI + COURIER)
                .then()
                .log().all()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("message");
       assertEquals(error, response);
    }

//чтобы создать курьера, нужно передать в ручку все обязательные поля
    @Test
    public void should_be_all_required_fields_for_creation(){

        CourierForCreation courierForCreation = new CourierForCreation(null, null, null);
        String error = "Недостаточно данных для создания учетной записи";

        String response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierForCreation)
                .when()
                .post(baseURI + COURIER)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");
        assertEquals(error, response);
    }

//успешный запрос возвращает ok: true
    @Test
    public void return_response_true(){

        CourierForCreation courierForCreation = CourierForCreation.getRandomCourier();

        boolean ok = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierForCreation)
                .when()
                .post(baseURI + COURIER)
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok");
        assertTrue(ok);
    }
//запрос возвращает правильный код ответа
    @Test
    public void return_correct_response_code(){

        CourierForCreation courierForCreation = CourierForCreation.getRandomCourier();

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierForCreation)
                .when()
                .post(baseURI + COURIER)
                .then()
                .statusCode(201);
    }

//если одного из полей нет, запрос возвращает ошибку;
    @Test
    public void return_error_without_login() {

        CourierForCreation courierForCreation = CourierForCreation.getRandomCourierWithoutLogin();

        String error = "Недостаточно данных для создания учетной записи";
        String response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierForCreation)
                .when()
                .post(baseURI + COURIER)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");
        assertEquals(response, error);
    }
//если создать пользователя с логином, который уже есть, возвращается ошибка.
        @Test
        public void create_two_identical_login(){

            CourierForCreation courierForCreation = CourierForCreation.getRandomCourier();

            String error = "Этот логин уже используется. Попробуйте другой.";
            given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(courierForCreation)
                    .when()
                    .post(baseURI + COURIER)
                    .then()
                    .log().all()
                    .statusCode(SC_CREATED);

            String response = given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(courierForCreation)
                    .when()
                    .post(baseURI + COURIER)
                    .then()
                    .log().all()
                    .statusCode(SC_CONFLICT)
                    .extract()
                    .path("message");
            assertEquals(error, response);
        }


}

