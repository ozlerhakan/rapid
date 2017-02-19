package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import static com.kodcu.rapid.util.Networking.deleteResponse;
import static com.kodcu.rapid.util.Networking.getResponse;
import static com.kodcu.rapid.util.Networking.postResponse;

/**
 * Created by Hakan on 2/12/2016.
 */
@Path("images")
public class Image extends DockerClient {

    @GET
    @Path("json")
    public JsonStructure listImages(
            @DefaultValue("false") @QueryParam("all") String all,
            @DefaultValue("false") @QueryParam("digests") String digests,
            @QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("images").path("json").queryParam("all", all).queryParam("digests", digests);

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
    @Path("{id}/json")
    public String inspectImage(@PathParam("id") String id) {

        WebTarget target = resource().path("images").path(id).path("json");

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}/history")
    public String historyImage(@PathParam("id") String id) {

        WebTarget target = resource().path("images").path(id).path("history");

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("search")
    public String searchImages(@QueryParam("term") String term,
                               @QueryParam("limit") int limit,
                               @QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("images").path("search").queryParam("term", term);

        if (limit != 0)
            target = target.queryParam("limit", limit);
        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("commit")
    public String commitImage(
            @QueryParam("container") String container,
            @QueryParam("repo") String repo,
            @QueryParam("tag") String tag,
            @QueryParam("comment") String comment,
            @QueryParam("author") String author,
            @QueryParam("changes") String changes,
            @DefaultValue("false") @QueryParam("pause") String pause,
            JsonObject content) {

        WebTarget target = resource().path("images").path("commit").queryParam("pause", pause);

        if (Objects.nonNull(container))
            target = target.queryParam("container", container);
        if (Objects.nonNull(repo))
            target = target.queryParam("repo", repo);
        if (Objects.nonNull(tag))
            target = target.queryParam("tag", tag);
        if (Objects.nonNull(comment))
            target = target.queryParam("comment", comment);
        if (Objects.nonNull(author))
            target = target.queryParam("author", author);
        if (Objects.nonNull(changes))
            target = target.queryParam("changes", changes);

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/tag")
    public String tagImage(
            @PathParam("id") String id,
            @QueryParam("repo") String repo,
            @QueryParam("tag") String tag) {

        WebTarget target = resource().path("images")
                .path(id).path("tag");

        if (Objects.nonNull(tag))
            target = target.queryParam("tag", tag);
        if (Objects.nonNull(repo))
            target = target.queryParam("repo", repo);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("prune")
    public String pruneImages(
            @QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("images").path("prune");

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @DELETE
    @Path("{id}")
    public String deleteImage(
            @PathParam("id") String id,
            @DefaultValue("false") @QueryParam("force") String force,
            @DefaultValue("false") @QueryParam("noprune") String noprune) {

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
    public String createImage(@QueryParam("fromImage") String fromImage) {

        WebTarget target = resource().path("images").path("create")
                .queryParam("fromImage", fromImage);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }
}
