package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.Json;
import javax.json.JsonObject;
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
    public Response listTasks(@QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("tasks");

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);

        try {
            return Response.status(response.getStatus())
                    .entity(Json.createReader(new StringReader(entity)).read())
                    .build();
        } finally {
            response.close();
        }
    }

    @GET
    @Path("{id}")
    public Response inspectTask(@PathParam("id") String id) {

        WebTarget target = resource().path("tasks").path(id);

        Response response = getResponse(target);

        try {
            return Response.status(response.getStatus())
                    .entity(response.readEntity(JsonObject.class)).build();
        } finally {
            response.close();

        }
    }
}
