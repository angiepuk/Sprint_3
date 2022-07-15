import POJO.CourierForCreation;
import POJO.CourierForLogin;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.*;
import ru.yandex.scooter.api.EndPoint;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static ru.yandex.scooter.api.EndPoint.COURIER;
import static ru.yandex.scooter.api.EndPoint.LOGIN;

public class TestCourierLogin {
    static Integer id;


       @BeforeClass
    public static void create_courier_before_test() {
        CourierForCreation courierForCreation = new CourierForCreation("Agent333", "Parrot333", "Roman");
        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(courierForCreation)
                .when()
                .post(baseURI+COURIER)
                .then()
                .log().all()
                .statusCode(SC_CREATED);
    }



    @AfterClass
    public static void delete_courier_after_test() {
        String idCourier = Integer.toString(id);
        RestAssured.with()
                .contentType(ContentType.JSON)
                .log().all()
                .delete(baseURI+COURIER+"/{idCourier}", idCourier)
                .then()
                .statusCode(SC_OK);
    }

    //курьер может авторизоваться
    @Test
    public void login_courier() {


        CourierForLogin courierForLogin = new CourierForLogin("Agent33", "Parrot33");

        Response response = given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(courierForLogin)
                .post(baseURI + LOGIN);

        response.then().log().all().assertThat().body("id", notNullValue()).and().statusCode(SC_OK);
    }

    //для авторизации нужно передать все обязательные поля
    @Test
    public void should_be_all_required_fields_for_authorization() {
        CourierForLogin courierForLogin = new CourierForLogin(null, "Parrot33");

        String error = "Недостаточно данных для входа";
        String response = given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(courierForLogin)
                .post(baseURI + LOGIN)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");
        assertEquals(error, response);
    }

    //система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    public void incorrect_login() {

        CourierForLogin courierForLogin = new CourierForLogin("Parrot11", "Parrot33");

        String error = "Учетная запись не найдена";
        String response = given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(courierForLogin)
                .post(baseURI + LOGIN)
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .path("message");
        assertEquals(error, response);
    }

    //если какого-то поля нет, запрос возвращает ошибку;
    @Test
    public void without_one_field() {

        CourierForLogin courierForLogin = new CourierForLogin("Agent33", null);

        String error = "Недостаточно данных для входа";
        String response = given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(courierForLogin)
                .post(baseURI + LOGIN)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");
        assertEquals(error, response);
    }

    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    public void user_doesnt_exist() {
        CourierForLogin courierForLogin = new CourierForLogin("Pirat", "Pirat");

        String error = "Учетная запись не найдена";
        String response = given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(courierForLogin)
                .post(baseURI + LOGIN)
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .path("message");
        assertEquals(error, response);
    }

    //успешный запрос возвращает id
    @Test
    public void successful_request_return_id() {
        CourierForLogin courierForLogin = new CourierForLogin("Agent33", "Parrot33");

        Response response = given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(courierForLogin)
                .post(baseURI + LOGIN);

        response.then().log().all().assertThat().body("id", notNullValue()).and().statusCode(SC_OK);
    }
    }




