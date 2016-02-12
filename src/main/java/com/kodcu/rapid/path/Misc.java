package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by Hakan on 2/12/2016.
 */
@Path("")
public class Misc extends DockerClient {

    @POST
    @Path("auth")
    @Produces(value = "application/json")
    public JsonObject getAuth(JsonObject content) throws IOException, ExecutionException, InterruptedException {
        WebTarget target = resource().path("auth");
        Response response = postResponse(target, content);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("version")
    @Produces(value = "application/json")
    public JsonObject getVersion() throws IOException, ExecutionException, InterruptedException {
        WebTarget target = resource().path("version");
        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("info")
    @Produces(value = "application/json")
    public JsonObject getInfo() throws IOException, ExecutionException, InterruptedException {
        WebTarget target = resource().path("info");
        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("_ping")
    public String ping() throws IOException, ExecutionException, InterruptedException {
        WebTarget target = resource().path("_ping");
        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("events")
    public String events(
            @QueryParam("since") String since,
            @QueryParam("until") String until,
            @QueryParam("filters") String filters)
            throws IOException, ExecutionException, InterruptedException {
        WebTarget target = resource().path("events");

        if (Objects.nonNull(since))
            target = target.queryParam("since", since);
        if (Objects.nonNull(until))
            target = target.queryParam("until", until);
        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }
}
