import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest extends TestConfiguration {

    private Courier courier;

    @Before
    public void createCourier() {
        courier = new Courier("ninja-qa", "123", "saske");
    }

    @Test
    @DisplayName("Check status code and create courier")
    public void checkCreateCourier() {
        given()
                .spec(requestSpecification)
                .and()
                .body(courier)
                .when()
                .post("/courier")
                .then().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    /*
    следующие методы по сути тоже негативные проверки, но один курьер создается, и чтобы его удалить
    оставил в этом классе, не стал переносить в NegativeCreateCourierTest
     */
    @Test
    @DisplayName("Check create two same couriers")
    public void checkCreateTwoCouriers() {
        Courier secondCourier = new Courier("ninja-qa", "123", "saske");

        given()
                .spec(requestSpecification)
                .and()
                .body(courier)
                .post("/courier")
                .then().statusCode(201);

        given()
                .spec(requestSpecification)
                .and()
                .body(secondCourier)
                .post("/courier")
                .then().statusCode(409)
                .and()
                .assertThat().body("message",
                        equalTo("Этот логин уже используется. Попробуйте другой."));
    }


    @Test
    @DisplayName("Check create courier with repeated login")
    public void checkCreateCourierRepeatedLogin() {
        given()
                .spec(requestSpecification)
                .and()
                .body(courier)
                .post("/courier")
                .then().statusCode(201);

        given()
                .spec(requestSpecification)
                .and()
                .body(new Courier(courier.getLogin(), "1234", "naruto"))
                .post("/courier")
                .then().statusCode(409)
                .and()
                .assertThat().body("message",
                        equalTo("Этот логин уже используется. Попробуйте другой."));
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
