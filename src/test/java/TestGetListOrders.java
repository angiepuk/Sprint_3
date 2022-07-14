import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class TestGetListOrders {
    String URL = "http://qa-scooter.praktikum-services.ru";
    public static String endPointOrders = "/api/v1/orders";
    @Test
    public void test() {
        Response response = given()
                .header("Content-Type", "Application/json")
                .get(URL + endPointOrders);

        response.then().log().all().assertThat().body("orders", notNullValue()).and().statusCode(SC_OK);
    }
}

//Проверь, что в тело ответа возвращается список заказов.
//записать в переменную, десериализовать ответ в объект и проверить, что это список

