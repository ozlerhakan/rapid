package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import static com.kodcu.rapid.util.Networking.deleteResponse;
import static com.kodcu.rapid.util.Networking.getResponse;
import static com.kodcu.rapid.util.Networking.postResponse;

/**
 * Created by Hakan on 2/12/2016.
 */
@Path("networks")
public class Network extends DockerClient {

    @GET
    public JsonStructure listNetworks(@QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("networks");

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        JsonStructure entity;
        if (response.getStatus() == 200)
            entity = response.readEntity(JsonArray.class);
        else
            entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}")
    public JsonStructure inspectNetwork(
            @PathParam("id") String id) {

        WebTarget target = resource().path("networks").path(id);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        JsonStructure structure = Json.createReader(new StringReader(entity)).read();
        response.close();
        return structure;
    }

    @DELETE
    @Path("{id}")
    public JsonStructure deleteNetwork(
            @PathParam("id") String id) {

        WebTarget target = resource().path("networks").path(id);

        Response response = deleteResponse(target);
        String entity = response.readEntity(String.class);

        JsonStructure structure;
        if (entity.isEmpty()) {
            structure = Json.createObjectBuilder().add("message", id + " removed.").build();
        } else {
            structure = Json.createReader(new StringReader(entity)).read();
        }
        response.close();
        return structure;
    }

    @POST
    @Path("prune")
    public JsonObject pruneNetwork() {

        WebTarget target = resource().path("networks");

        Response response = postResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/connect")
    public JsonStructure connectToNetwork(
            @PathParam("id") String id,
            JsonObject content) {

        WebTarget target = resource().path("networks").path(id).path("connect");

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure structure;
        if (entity.isEmpty()) {
            structure = Json.createObjectBuilder().add("message", id + " connected.").build();
        } else {
            structure = Json.createReader(new StringReader(entity)).read();
        }
        return structure;
    }

    @POST
    @Path("{id}/disconnect")
    public JsonStructure disconnectToNetwork(
            @PathParam("id") String id,
            JsonObject content) {

        WebTarget target = resource().path("networks").path(id).path("disconnect");

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure structure;
        if (entity.isEmpty()) {
            structure = Json.createObjectBuilder().add("message", id + " disconnected.").build();
        } else {
            structure = Json.createReader(new StringReader(entity)).read();
        }
        return structure;
    }

    @POST
    @Path("create")
    public JsonObject createNetwork(JsonObject content) {

        WebTarget target = resource().path("networks").path("create");

        Response response = postResponse(target, content);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }
}
