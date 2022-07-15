import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.scooter.api.EndPoint;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.scooter.api.EndPoint.ORDER;

public class TestGetListOrders {


    @Test
    public void test() {
        Response response = given()
                .contentType(ContentType.JSON)
                .get(baseURI + ORDER);

        response.then().log().all().assertThat().body("orders", notNullValue()).and().statusCode(SC_OK);
    }
}


