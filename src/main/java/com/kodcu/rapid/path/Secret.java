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
 * Created by hakan on 15/02/2017.
 */
@Path("secrets")
public class Secret extends DockerClient {

    @GET
    public JsonStructure getSecrets(
            @QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("secrets");

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

    @POST
    @Path("create")
    public JsonObject createSecret(JsonObject content) {

        WebTarget target = resource().path("secrets").path("create");

        Response response = postResponse(target, content);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}")
    public JsonObject inspectSecret(@PathParam("id") String id) {

        WebTarget target = resource().path("secrets").path(id);

        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @DELETE
    @Path("{id}")
    public JsonStructure deleteSecret(@PathParam("id") String id) {

        WebTarget target = resource().path("secrets").path(id);

        Response response = deleteResponse(target);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure structure;
        if (entity.isEmpty()) {
            structure = Json.createObjectBuilder().add("message", id + " removed.").build();
        } else {
            structure = Json.createReader(new StringReader(entity)).read();
        }
        return structure;
    }

    @POST
    @Path("{id}/update")
    public JsonStructure updateSecret(
            @PathParam("id") String id,
            @QueryParam("version") String version,
            JsonObject content) {

        WebTarget target = resource().path("secrets").path(id).path("update").queryParam("version", version);

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure structure;
        if (entity.isEmpty()) {
            structure = Json.createObjectBuilder().add("message", id + " updated.").build();
        } else {
            structure = Json.createReader(new StringReader(entity)).read();
        }
        return structure;
    }

}
