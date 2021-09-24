package com.teedjay;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ConfigFilter implements ContainerRequestFilter {

    @ConfigProperty(name = "wiremock.port", defaultValue = "NotInjected")
    String injectedPort;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        requestContext.getHeaders().add("TestInjectedPort", injectedPort);
        requestContext.getHeaders().add("TestConfigProvidePort", ConfigProvider.getConfig().getValue("wiremock.port", String.class));
        requestContext.getHeaders().add("TestSystemPropertiesPort", System.getProperty("wiremock.port", "NoProperty"));
    }

}
