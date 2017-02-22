package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import static com.kodcu.rapid.util.Networking.getResponse;

/**
 * Created by hakan on 15/02/2017.
 */
@Path("tasks")
public class Task extends DockerClient {

    @GET
    public JsonStructure listTasks(@QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("tasks");

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return Json.createReader(new StringReader(entity)).read();
    }

    @GET
    @Path("{id}")
    public JsonObject inspectTask(@PathParam("id") String id) {

        WebTarget target = resource().path("tasks").path(id);

        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }
}
