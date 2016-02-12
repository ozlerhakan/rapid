package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by Hakan on 2/12/2016.
 */
@Path("images")
public class Image extends DockerClient {

    @GET
    @Path("json")
    @Produces(value = "application/json")
    public JsonArray getImages(
            @DefaultValue("false") @QueryParam("all") String all,
            @QueryParam("filter") String filter,
            @QueryParam("filters") String filters) throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("images").path("json").queryParam("all", all);

        if (Objects.nonNull(filter))
            target = target.queryParam("filter", filter);
        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        JsonArray entity = response.readEntity(JsonArray.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}/json")
    public String inspectImage(@PathParam("id") String id)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("images").path(id).path("json");

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}/history")
    public String historyImage(@PathParam("id") String id)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("images").path(id).path("history");

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/tag")
    public String tagImage(
            @PathParam("id") String id,
            @QueryParam("repo") String repo,
            @DefaultValue("false") @QueryParam("force") String force,
            @QueryParam("tag") String tag) throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("images")
                .path(id).path("tag")
                .queryParam("repo", repo)
                .queryParam("force", force);

        if (Objects.nonNull(tag))
            target = target.queryParam("tag", tag);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @DELETE
    @Path("{id}")
    public String deleteContainer(
            @PathParam("id") String id,
            @DefaultValue("false") @QueryParam("force") String force,
            @DefaultValue("false") @QueryParam("noprune") String noprune)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("images")
                .path(id)
                .queryParam("force", force)
                .queryParam("noprune", noprune);

        Response response = deleteResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("create")
    public String createImage(@QueryParam("fromImage") String fromImage)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("images").path("create")
                .queryParam("fromImage", fromImage);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }
}
