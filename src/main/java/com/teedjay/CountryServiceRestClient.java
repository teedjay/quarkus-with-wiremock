package com.teedjay;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/countries")
@RegisterRestClient
public interface CountryServiceRestClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Set<Country> getAllCountries();

}
