package base;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Courier;

import static io.restassured.RestAssured.given;

public class CourierApi extends ScooterRestClient {

    private static final String COURIER_URI = BASE_URI + "courier/";

    @Step("Create courier {courier}")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getReqSpec())
                .body(courier)
                .post(COURIER_URI)
                .then();
    }

    @Step("Login as courier {courier}")
    public ValidatableResponse login(Courier courier) {
        return given()
                .spec(getReqSpec())
                .body(courier)
                .post(COURIER_URI + "login/")
                .then();
    }

    @Step("Delete courier with id {id}")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getReqSpec())
                .when()
                .delete(COURIER_URI + id)
                .then();
    }
}
