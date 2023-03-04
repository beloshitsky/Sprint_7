import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class NegativeLoginCourierTest extends TestConfiguration {

    private Courier courier;

    public NegativeLoginCourierTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new Courier("", "123")},
                {new Courier("ninja-qa", "")}
        };
    }

    @Test
    @DisplayName("Check login without login or password")
    public void checkLoginCourierWithoutLoginOrPassword() {
        given()
                .spec(requestSpecification)
                .and()
                .body(courier)
                .post("/courier/login")
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
}
