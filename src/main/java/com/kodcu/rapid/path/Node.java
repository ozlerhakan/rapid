package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by hakan on 15/02/2017.
 */
@Path("nodes")
public class Node extends DockerClient {

    @GET
    public String listNodes(@QueryParam("filters") String filters)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("nodes");

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", filters);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}")
    public String getNode(@PathParam("id") String id)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("nodes").path(id);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }


    @DELETE
    @Path("{id}")
    public String deleteNode(@PathParam("id") String id)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("nodes").path(id);

        Response response = deleteResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/update")
    public String updateNode(@PathParam("id") String id,
                             @QueryParam("version") String version,
                             JsonObject content)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("nodes").path(id).path("update");

        if (Objects.nonNull(version))
            target = target.queryParam("version", version);

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }
}
