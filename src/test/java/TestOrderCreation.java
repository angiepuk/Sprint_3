import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Parameterized.class)
    public class TestOrderCreation {
    File json;
    String URL = "http://qa-scooter.praktikum-services.ru";

    public TestOrderCreation(File json) {
        this.json = json;
    }

    @Parameterized.Parameters
    public static Object[] getSumData() {
         return new Object[][] {
                {new File("src/test/resources/orderWithColorBlack.json")},
                {new File("src/test/resources/orderWithTwoColor.json")},
                {new File("src/test/resources/orderWithoutColor.json")},
        };
    }

    @Test
    public void create_new_order() {

        Response response =
                 given()
                .header("Content-Type", "application/json")
                .log().all()
                .body(json)
                .when()
                .post(URL + TestGetListOrders.endPointOrders);

        response.then().log().all().assertThat().body("track", notNullValue()).and().statusCode(SC_CREATED);
    }
}



