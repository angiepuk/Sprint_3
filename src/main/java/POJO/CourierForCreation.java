package POJO;
import org.apache.commons.lang3.RandomStringUtils;

import java.net.http.HttpRequest;

import static io.restassured.RestAssured.given;

public class CourierForCreation {
    private String login;
    private String  password;
    private String firstName;

    public CourierForCreation(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CourierForCreation() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public static CourierForCreation getRandomCourier(){
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        return new CourierForCreation(login, password, firstName);

    }

    public static CourierForCreation getRandomCourierWithoutLogin(){
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        return new CourierForCreation(null, password, firstName);
    }
}
