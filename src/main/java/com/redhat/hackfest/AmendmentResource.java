package com.redhat.hackfest;

import org.apache.http.client.fluent.Request;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Path("/api/v1")
public class AmendmentResource {

    @ConfigProperty(name = "app.compughterratings.url")
    String url;

    @GET
    @Path("/teams/{sport}/true")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSport(@HeaderParam("app-id")
                                     String appId,
                             @HeaderParam("api-key")
                                     String apikey,
                             @PathParam String sport) throws IOException {
        String endpoint = String.format("%s/teams/%s/true", url, enc(sport));
        return Response.ok(Request.Get(endpoint)
                                  .addHeader("app-id", appId)
                                  .addHeader("api-key", apikey)
                                  .execute().returnContent().asString()).build();
    }

    @GET
    @Path("/simulation/{sport}/{homeTeam}/{awayTeam}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSimulation(
            @HeaderParam("app-id")
                    String appId,
            @HeaderParam("api-key")
                    String apikey,
            @PathParam("sport") String sport,
            @PathParam("homeTeam") String homeTeam,
            @PathParam("awayTeam") String awayTeam) throws IOException {
        String endpoint = String.format("%s/simulation/%s/%s/%s", url, enc(sport), enc(homeTeam), enc(awayTeam));
        return Response.ok(Request.Get(endpoint)
                                  .addHeader("app-id", appId)
                                  .addHeader("api-key", apikey)
                                  .execute().returnContent().asString()).build();
    }

    public static String enc(String raw) throws UnsupportedEncodingException {
        return URLEncoder.encode(raw, "utf-8").replaceAll("\\+", "%20");
    }
}