package weather;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Concurrent;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;

@RunWith(SerenityRunner.class)
@Concurrent
public class WeatherTest {
    @Steps
    private String cityId;

    @Test
    public void getWeatherPerCityTest() {

        RestAssured.baseURI = "https://pinformer.sinoptik.ua/";
        ValidatableResponse response = SerenityRest.given()
                .param("Lang", "ua")
                .param("return_id", "1")
                .param("q", "Lviv")
                .basePath("/search.php")
                //.log().uri()
                .get()
                .then()
                //Met.log().all()
                .statusCode(200);

        String cityInfo = response.extract().asString();
        cityId = cityInfo.substring(cityInfo.lastIndexOf("|") + 1);
        System.out.println("City id = " + cityId);


        //get response with known ID
        RestAssured.baseURI = "https://pinformer.sinoptik.ua/";

        ValidatableResponse responseWeather = SerenityRest.given()
                .param("type", "js")
                .param("lang", "ua")
                .param("id", cityId)
                .basePath("/pinformer4.php")
                .get()
                .then()
                .log().all()
                .statusCode(200)
                .body("any { it.key == '{pcity}' }", is(true)) //Groovy path with hamcrest matchers
                .body("'{pcity}'", is(cityId)); //is(not(1)) -- JSON path with humcrast matchers

        //String WeatherKeyValues = responseWeather.extract().asString();
        //temp = WeatherKeyValues.indexOf("temp")
        //System.out.println(WeatherKeyValues);

    }

    @Test
    public void postFastLoan() {
        RestAssured.baseURI = "https://fastloansystem.com/api/token";

        ValidatableResponse response = SerenityRest.given()
                .param("login", "tehnoskarb_new_market")
                .param("password", "hdu5K8hsu")
                .param("grant_type", "password")
                .post()
                .then()
                .log().all()
                .statusCode(200);

    }
}
