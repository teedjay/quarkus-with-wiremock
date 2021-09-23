package com.teedjay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.teedjay.wiremock.InjectWireMock;
import com.teedjay.wiremock.WireMockEnabled;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@QuarkusTestResource(WireMockEnabled.class)
public class CountryServiceHttpClientTest {

    @InjectWireMock
    WireMockServer wireMockServer;

    @Inject
    ObjectMapper objectMapper;

    @Test
    public void checkWiremock() {
        assertTrue(wireMockServer.isRunning(), "Wiremock should be running");
    }

    @Test
    public void fetchCountries() throws Exception {
        var url = "http://localhost:%d/countries".formatted(wireMockServer.port());
        var request = HttpRequest.newBuilder(new URI(url)).GET().build();
        var json = HttpClient.newHttpClient().send(request, BodyHandlers.ofString()).body();
        var countries = objectMapper.readValue(json, Country[].class);
        assertEquals(3, countries.length);
        assertEquals(new Country("Denmark", "Copenhagen"), countries[2]);
    }

}
