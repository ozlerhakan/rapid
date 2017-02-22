package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonStructure;
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
    public JsonObject getAuth(JsonObject content) {
        WebTarget target = resource().path("auth");
        Response response = postResponse(target, content);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("version")
    public JsonObject getVersion() {
        WebTarget target = resource().path("version");
        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("info")
    public JsonObject getInfo() {
        WebTarget target = resource().path("info");
        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("_ping")
    public JsonStructure ping() {
        WebTarget target = resource().path("_ping");
        Response response = getResponse(target);
        JsonStructure entity;
        if (response.getStatus() == 200) {
            String value = response.readEntity(String.class);
            entity = Json.createObjectBuilder().add("message", value).build();
        }
        else
            entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("system/df")
    public JsonObject df() {
        WebTarget target = resource().path("system").path("df");
        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

}
