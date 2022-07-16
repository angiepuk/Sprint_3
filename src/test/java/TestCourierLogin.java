import POJO.CourierForCreation;
import POJO.CourierForLogin;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.scooter.api.EndPoint.*;

public class TestCourierLogin {
    static Integer id;


       @BeforeClass
    public static void create_courier_before_test() {
        CourierForCreation courierForCreation = new CourierForCreation("Agent0004", "Parrot0004", "Roman");
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierForCreation)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .statusCode(SC_CREATED);
    }



    @AfterClass
    public static void delete_courier_after_test() {

        String idCourier = Integer.toString(id);
        RestAssured.with()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .delete("http://qa-scooter.praktikum-services.ru/api/v1/courier/{idCourier}", idCourier)
                .then()
                .statusCode(SC_OK);
    }

    //курьер может авторизоваться
    @Test
    public void login_courier() {


        CourierForLogin courierForLogin = new CourierForLogin("Agent0004", "Parrot0004");

       given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierForLogin)
                .when()
                .post(LOGIN)
                .then()
                .log().all()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    //для авторизации нужно передать все обязательные поля
    @Test
    public void should_be_all_required_fields_for_authorization() {
        CourierForLogin courierForLogin = new CourierForLogin(null, "Parrot0004");

        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierForLogin)
                .when()
                .post(LOGIN)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.is("Недостаточно данных для входа"));
    }

    //система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    public void incorrect_login() {

        CourierForLogin courierForLogin = new CourierForLogin("Parrot0004", "Parrot33");

        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierForLogin)
                .when()
                .post(LOGIN)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", Matchers.is("Учетная запись не найдена"));

    }

    //если какого-то поля нет, запрос возвращает ошибку;
    @Test
    public void without_one_field() {

        CourierForLogin courierForLogin = new CourierForLogin("Agent0004", null);

        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierForLogin)
                .when()
                .post(LOGIN)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message",Matchers.is("Недостаточно данных для входа"));
    }

    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    public void user_doesnt_exist() {
        CourierForLogin courierForLogin = new CourierForLogin("Pirat", "Pirat");

        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierForLogin)
                .when()
                .post(LOGIN)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", Matchers.is("Учетная запись не найдена"));

    }

    //успешный запрос возвращает id
    @Test
    public void successful_request_return_id() {
        CourierForLogin courierForLogin = new CourierForLogin("Agent0004", "Parrot0004");

       id = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierForLogin)
                .when()
                .post(LOGIN)
                .then()
                .log().all()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(SC_OK)
                .extract()
                .body()
                .path("id");
    }
    }




