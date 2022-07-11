package POJO;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;

import static io.restassured.RestAssured.given;

public class CourierForLogin {
    private String login;
    private String password;

    public CourierForLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierForLogin() {
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




}



