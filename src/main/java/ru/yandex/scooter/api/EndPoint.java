package ru.yandex.scooter.api;

import io.restassured.RestAssured;

public class EndPoint {
    public static final String COURIER = "/courier";
    public static final String LOGIN = "/courier/login";
    public static final String ORDER = "/orders";

    public void url() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/api/v1";

    }
}
