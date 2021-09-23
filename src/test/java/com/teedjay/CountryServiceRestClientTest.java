package com.teedjay;

import com.teedjay.wiremock.WireMockEnabled;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@QuarkusTestResource(WireMockEnabled.class)
public class CountryServiceRestClientTest {

    @RestClient
    CountryServiceRestClient countryService;

    @Test
    public void fetchCountries() {
        var response = countryService.getAllCountries();
        assertEquals(3, response.size());
        assertTrue(response.contains(new Country("Norway", "Oslo")), "Capital of Norway should be Oslo");
    }

}
