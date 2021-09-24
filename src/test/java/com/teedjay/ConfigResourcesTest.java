package com.teedjay;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ConfigResourcesTest {

    @ConfigProperty(name = "wiremock.port")
    String injectedPort;

    @Test
    public void configPortsShouldAlwaysBeUpdated() {
        given()
          .when()
            .get("/config")
          .then()
            .statusCode(200)
            .header("TestInjectedPort", injectedPort)
            .header("TestConfigProvidePort", injectedPort)
            .header("TestSystemPropertiesPort", "NoProperty")
            .body(is("config"))
        ;
    }

}
