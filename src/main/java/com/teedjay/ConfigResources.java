package com.teedjay;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/config")
public class ConfigResources {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getConfig(@HeaderParam("TestInjectedPort") String injectedValue,
                              @HeaderParam("TestConfigProvidePort") String configSource,
                              @HeaderParam("TestSystemPropertiesPort") String systemProperty) {
        return Response.ok("config")
            .header("TestInjectedPort", injectedValue)
            .header("TestConfigProvidePort", configSource)
            .header("TestSystemPropertiesPort", systemProperty)
            .build();
    }

}
