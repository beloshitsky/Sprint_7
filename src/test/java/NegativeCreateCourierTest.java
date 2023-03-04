import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class NegativeCreateCourierTest extends TestConfiguration {

    private Courier courier;

    public NegativeCreateCourierTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new Courier(null, "123", "saske")},
                {new Courier("ninja-qa", null, "saske")}
        };
    }

    @Test
    @DisplayName("Check create without login or password")
    public void checkCreateCourierWithoutLoginOrPassword() {
        given()
                .spec(requestSpecification)
                .and()
                .body(courier)
                .post("/courier")
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
