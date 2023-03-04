import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.given;

public class TestConfiguration {
    protected static RequestSpecification requestSpecification;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.basePath = "/api/v1";

        requestSpecification = given()
                .header("Content-type", "application/json");
    }
}
