import base.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import model.Courier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierWithInvalidDataTest {

    private CourierApi courierApi;
    private Courier courier;

    public CreateCourierWithInvalidDataTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new Courier(null, "123", "saske")},
                {new Courier("ninja-qa", null, "saske")}
        };
    }

    @Before
    public void setUp() {
        courierApi = new CourierApi();
    }

    @Test
    @DisplayName("Check create without login or password")
    public void checkCreateCourierWithoutLoginOrPassword() {
        courierApi.create(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
