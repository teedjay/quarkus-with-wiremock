package com.teedjay.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class WireMockEnabled implements QuarkusTestResourceLifecycleManager {

    WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {

        // depending on quarkus launch mode the root directory seems to change, hack to make it work
        String wiremockRootDir = Files.isDirectory(Path.of("src/test/resources")) ? "src/test/resources" : "test-classes";
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().withRootDirectory(wiremockRootDir).dynamicPort());
        wireMockServer.start();

        // stub some data for known url's
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlPathEqualTo("/countries"))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .withBodyFile("countries.json")
            )
        );

        // return system properties that should be set for the running test
        return Map.of("com.teedjay.CountryServiceRestClient/mp-rest/url", "http://localhost:" + wireMockServer.port());
    }

    @Override
    public void stop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            wireMockServer = null;
        }
    }

    @Override
    public void inject(TestInjector testInjector) {
        testInjector.injectIntoFields(wireMockServer, new TestInjector.AnnotatedAndMatchesType(InjectWireMock.class, WireMockServer.class));
    }

}
