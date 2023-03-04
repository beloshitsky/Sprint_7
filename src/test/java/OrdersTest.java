import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class OrdersTest extends TestConfiguration {

    @Test
    @DisplayName("check get orders list")
    public void checkGetOrders() {
        Response response = given()
                .spec(requestSpecification)
                .get("/orders");

        response.then()
                .assertThat().body("orders", notNullValue())
                .and().statusCode(200);

        assertEquals(30, response
                .jsonPath()
                .getList("orders")
                .size());
    }
}
