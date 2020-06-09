package com.redhat.hackfest;

import org.apache.http.client.fluent.Request;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Path("/api/v1")
public class AmendmentResource {

    private static Logger logger = Logger.getLogger(AmendmentResource.class);

    @ConfigProperty(name = "app.compughterratings.url")
    String url;

    @GET
    @Path("/teams/{sport}/true")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSport(@HeaderParam("app-id")
                                     String appId,
                             @HeaderParam("api-key")
                                     String apikey,
                             @PathParam String sport) throws Exception {
        String fullUrl = String.format("%s/teams/%s/true", url, enc(sport));
        logger.infov("URL {0}", fullUrl);
        return Response.ok(Request.Get(fullUrl)
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
            @PathParam("awayTeam") String awayTeam) throws Exception {
        String fullUrl = String.format("%s/simulation/%s/%s/%s", url, enc(sport), enc(homeTeam), enc(awayTeam));
        logger.infov("URL {0}", fullUrl);
        return Response.ok(Request.Get(fullUrl)
                                  .addHeader("app-id", appId)
                                  .addHeader("api-key", apikey)
                                  .execute().returnContent().asString()).build();
    }

    private String enc(String str) throws Exception {
        return URLEncoder.encode(str, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20").trim();
    }
}