import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.scooter.api.EndPoint;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.scooter.api.EndPoint.BASE_URL;
import static ru.yandex.scooter.api.EndPoint.ORDER;

public class TestGetListOrders {


    @Test
    public void test() {
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .when()
                .get(ORDER)
                .then()
                .assertThat()
                .log().all()
                .body("orders", notNullValue())
                .and()
                .statusCode(SC_OK);
    }
}


