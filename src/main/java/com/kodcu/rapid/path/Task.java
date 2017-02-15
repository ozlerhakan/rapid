package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.ws.rs.GET;
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
@Path("tasks")
public class Task extends DockerClient {

    @GET
    public String listTasks(@QueryParam("filters") String filters)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("tasks");

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", filters);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}")
    public String inspectTask(@PathParam("id") String id)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("tasks").path(id);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }
}
