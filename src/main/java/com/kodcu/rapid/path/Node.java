package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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
@Path("nodes")
public class Node extends DockerClient {

    @GET
    public JsonStructure listNodes(@QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("nodes");

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return Json.createReader(new StringReader(entity)).read();
    }

    @GET
    @Path("{id}")
    public JsonStructure getNode(@PathParam("id") String id) {

        WebTarget target = resource().path("nodes").path(id);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return Json.createReader(new StringReader(entity)).read();
    }


    @DELETE
    @Path("{id}")
    public JsonStructure deleteNode(@PathParam("id") String id,
                                    @DefaultValue("false") @QueryParam("force") String force) {

        WebTarget target = resource().path("nodes").path(id).queryParam("force", force);
        Response response = deleteResponse(target);

        JsonStructure result;
        if (response.getStatus() == 200)
            result = Json.createObjectBuilder().add("message", id + " node deleted.").build();
        else
            result = response.readEntity(JsonObject.class);
        response.close();
        return result;
    }

    @POST
    @Path("{id}/update")
    public JsonStructure updateNode(@PathParam("id") String id,
                             @QueryParam("version") String version,
                             JsonObject content) {

        WebTarget target = resource().path("nodes").path(id).path("update");

        if (Objects.nonNull(version))
            target = target.queryParam("version", version);

        Response response = postResponse(target, content);
        JsonStructure result;
        if (response.getStatus() == 200)
            result = Json.createObjectBuilder().add("message", id + " node updated.").build();
        else
            result = response.readEntity(JsonObject.class);
        response.close();
        return result;
    }
}
