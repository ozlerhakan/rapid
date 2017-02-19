package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import static com.kodcu.rapid.util.Networking.getResponse;
import static com.kodcu.rapid.util.Networking.postResponse;

/**
 * Created by Hakan on 2/12/2016.
 */
@Path("")
public class System extends DockerClient {

    @POST
    @Path("auth")
    @Produces(value = "application/json")
    public JsonObject getAuth(JsonObject content) {
        WebTarget target = resource().path("auth");
        Response response = postResponse(target, content);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("version")
    @Produces(value = "application/json")
    public JsonObject getVersion() {
        WebTarget target = resource().path("version");
        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("info")
    @Produces(value = "application/json")
    public JsonObject getInfo() {
        WebTarget target = resource().path("info");
        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("_ping")
    public String ping() {
        WebTarget target = resource().path("_ping");
        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("system/df")
    public String df() {
        WebTarget target = resource().path("system").path("df");
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
            @QueryParam("filters") String filters) throws UnsupportedEncodingException {
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
