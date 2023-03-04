import com.google.gson.Gson;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest extends TestConfiguration {

    private Courier courier;

    @Before
    public void createCourier() {
        courier = new Courier("ninja-qa", "123", "saske");

        given()
                .spec(requestSpecification)
                .and()
                .body(courier)
                .when()
                .post("/courier");
    }

    @Test
    @DisplayName("check login courier and id")
    public void checkLoginCourier() {
        given()
                .spec(requestSpecification)
                .and()
                .body(courier)
                .post("/courier/login")
                .then().statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("check login courier with incorrect login or password")
    public void checkIncorrectLoginCourier() {
        given()
                .spec(requestSpecification)
                .and()
                .body(new Gson().toJson(new Courier(courier.getLogin(), "12")))
                .post("/courier/login")
                .then().statusCode(404)
                .and()
                .body( "message", equalTo("Учетная запись не найдена"));

        given()
                .spec(requestSpecification)
                .and()
                .body(new Gson().toJson(new Courier("ninja", courier.getPassword())))
                .post("/courier/login")
                .then().statusCode(404)
                .and()
                .body( "message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteCourier() {
        Integer courierId = given()
                .spec(requestSpecification)
                .body(courier)
                .post("/courier/login")
                .then().extract().path("id");

        given()
                .spec(requestSpecification)
                .delete("/courier/{courierId}", courierId)
                .then().statusCode(200);
    }
}
