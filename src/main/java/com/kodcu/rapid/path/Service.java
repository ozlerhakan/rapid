package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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
@Path("services")
public class Service extends DockerClient {

    @GET
    public String listServices(@QueryParam("filters") String filters)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("services");

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", filters);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("create")
    public String createService(JsonObject content)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("services").path("create");

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}")
    public String inspectService(@PathParam("id") String id)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("services").path(id);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @DELETE
    @Path("{id}")
    public String deleteService(@PathParam("id") String id)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("services").path(id);

        Response response = deleteResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/update")
    public String updateService(@PathParam("id") String id,
                                @QueryParam("version") int version,
                                @DefaultValue("spec") @QueryParam("registryAuthFrom") String registryAuthFrom,
                                JsonObject content)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("services").path(id).path("update").queryParam("registryAuthFrom", registryAuthFrom);

        if (version != 0)
            target = target.queryParam("version", version);

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}/logs")
    public String serviceLiogs(@PathParam("id") String id,
                               @DefaultValue("false") @QueryParam("details") boolean details,
                               @DefaultValue("false") @QueryParam("follow") boolean follow,
                               @DefaultValue("false") @QueryParam("stdout") boolean stdout,
                               @DefaultValue("false") @QueryParam("stderr") boolean stderr,
                               @DefaultValue("0") @QueryParam("since") String since,
                               @DefaultValue("false") @QueryParam("timestamps") boolean timestamps,
                               @DefaultValue("all") @QueryParam("tail") String tail)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("services").path(id).path("logs")
                .queryParam("details", details)
                .queryParam("follow", follow)
                .queryParam("stdout", stdout)
                .queryParam("stderr", stderr)
                .queryParam("since", since)
                .queryParam("timestamps", timestamps)
                .queryParam("tail", tail);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }


}
