import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends TestConfiguration {

    private Order order;
    private Response response;

    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                        "+7 800 355 35 35", "5", "2020-06-06",
                        "Saske, come back to Konoha", List.of())},
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                        "+7 800 355 35 35", "5", "2020-06-06",
                        "Saske, come back to Konoha", List.of("BLACK"))},
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                        "+7 800 355 35 35",
                        "5", "2020-06-06",
                        "Saske, come back to Konoha", List.of("BLACK", "GREY"))},
        };
    }

    @Test
    @DisplayName("check create orders")
    public void checkCreateOrder() {
        response = given()
                .spec(requestSpecification)
                .body(order)
                .when()
                .post("/orders");

        response
                .then().statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @After
    public void deleteOrder() {
        Integer track = response.then().extract().path("track");

        given()
                .spec(requestSpecification)
                .put("/orders/cancel?track={track}", track)
                .then().statusCode(200);
    }
}
