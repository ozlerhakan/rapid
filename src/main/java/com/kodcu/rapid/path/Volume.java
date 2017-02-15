package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by Hakan on 2/12/2016.
 */
@Path("volumes")
public class Volume extends DockerClient {

    @GET
    public String getVolumes(
            @QueryParam("filters") String filters)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("volumes");
        if (Objects.nonNull(filters))
            target = target.queryParam("filters", filters);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}")
    public String inspectVolume(
            @PathParam("id") String id)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("volumes").path(id);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @DELETE
    @Path("{id}")
    public String deleteVolume(
            @PathParam("id") String id,
            @DefaultValue("false") @QueryParam("force") boolean force)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("volumes").path(id).queryParam("force", force);

        Response response = deleteResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("prune")
    public String pruneVolume(
            @QueryParam("filters") String filters)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("volumes");

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", filters);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("create")
    public String createVolume(JsonObject content)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("volumes").path("create");

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }
}
