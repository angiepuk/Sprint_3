
import POJO.CourierForCreation;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.scooter.api.EndPoint.BASE_URL;
import static ru.yandex.scooter.api.EndPoint.COURIER;

public class TestCourierCreation {


    //курьера можно создать
    @Test
       public void create_courier_return_200_Test() {

        CourierForCreation courierForCreation = CourierForCreation.getRandomCourier();

            given()
                   .header("Content-Type", "application/json")
                    .baseUri(BASE_URL)
                   .log().all()
                   .body(courierForCreation)
                   .when()
                   .post(COURIER)
                   .then()
                   .log().all()
                   .assertThat()
                   .statusCode(SC_CREATED)
                   .and()
                   .body("ok", Matchers.is(true));
       }

//нельзя создать двух одинаковых курьеров
    @Test
    public void cannot_create_two_identical_couriers(){

        CourierForCreation courierForCreation = CourierForCreation.getRandomCourier();

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierForCreation)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .statusCode(SC_CREATED);

       given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierForCreation)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", Matchers.is("Этот логин уже используется. Попробуйте другой."));

    }

//чтобы создать курьера, нужно передать в ручку все обязательные поля
    @Test
    public void should_be_all_required_fields_for_creation(){

        CourierForCreation courierForCreation = new CourierForCreation(null, null, null);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierForCreation)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }

//успешный запрос возвращает ok: true
    @Test
    public void return_response_true(){

        CourierForCreation courierForCreation = CourierForCreation.getRandomCourier();

        given()
                .header("Content-Type", "application/json")
                .baseUri(BASE_URL)
                .log().all()
                .body(courierForCreation)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", Matchers.is(true));
    }
//запрос возвращает правильный код ответа
    @Test
    public void return_correct_response_code(){

        CourierForCreation courierForCreation = CourierForCreation.getRandomCourier();

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierForCreation)
                .when()
                .post(COURIER)
                .then()
                .assertThat()
                .statusCode(SC_CREATED);
    }

//если одного из полей нет, запрос возвращает ошибку;
    @Test
    public void return_error_without_login() {

        CourierForCreation courierForCreation = CourierForCreation.getRandomCourierWithoutLogin();


        given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierForCreation)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message",Matchers.is( "Недостаточно данных для создания учетной записи"));
    }
//если создать пользователя с логином, который уже есть, возвращается ошибка.
        @Test
        public void create_two_identical_login(){

            CourierForCreation courierForCreation = CourierForCreation.getRandomCourier();

            given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .baseUri(BASE_URL)
                    .body(courierForCreation)
                    .when()
                    .post(COURIER)
                    .then()
                    .log().all()
                    .statusCode(SC_CREATED);

            given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .baseUri(BASE_URL)
                    .body(courierForCreation)
                    .when()
                    .post(COURIER)
                    .then()
                    .log().all()
                    .assertThat()
                    .statusCode(SC_CONFLICT)
                    .and()
                    .body("message", Matchers.is("Этот логин уже используется. Попробуйте другой."));
        }


}

