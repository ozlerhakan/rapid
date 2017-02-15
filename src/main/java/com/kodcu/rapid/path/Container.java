package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by Hakan on 2/10/2016.
 */
@Path("containers")
public class Container extends DockerClient {

    @GET
    @Path("json")
    public String getContainers(
            @DefaultValue("false") @QueryParam("all") String all,
            @QueryParam("limit") int limit,
            @DefaultValue("false") @QueryParam("size") String size,
            @QueryParam("filters") String filters) throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers").path("json").queryParam("all", all).queryParam("size", size);

        if (limit != 0)
            target = target.queryParam("limit", limit);
        if (Objects.nonNull(size))
            target = target.queryParam("size", size);
        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}/json")
    // inspect
    public String getContainer(
            @PathParam("id") String containerId,
            @DefaultValue("false") @QueryParam("size") String size)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId).path("json")
                .queryParam("size", size);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}/top")
    public String getContainerTopProcess(
            @PathParam("id") String containerId,
            @DefaultValue("-ef") @QueryParam("ps_args") String ps)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId).path("top")
                .queryParam("ps", ps);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}/logs")
    public String getContainerLogs(
            @PathParam("id") String containerId,
            @DefaultValue("false") @QueryParam("follow") String follow,
            @DefaultValue("false") @QueryParam("stdout") String stdout,
            @DefaultValue("false") @QueryParam("stderr") String stderr,
            @DefaultValue("0") @QueryParam("since") String since,
            @DefaultValue("false") @QueryParam("timestamps") String timestamps,
            @DefaultValue("all") @QueryParam("tail") String tail)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers").path(containerId).path("logs");

        target = target.queryParam("follow", follow)
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

    @GET
    @Path("{id}/changes")
    public String getContainerChanges(
            @PathParam("id") String containerId)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers").path(containerId).path("changes");

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}/stats")
    public String exportContainer(
            @PathParam("id") String containerId,
            @DefaultValue("true") @QueryParam("stream") String stream)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("stats")
                .queryParam("stream", stream);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/start")
    public String startContainer(
            @PathParam("id") String containerId,
            @QueryParam("detachKeys") String detachKeys)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("start");

        if (Objects.nonNull(detachKeys))
            target = target.queryParam("detachKeys", detachKeys);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/stop")
    public String stopContainer(
            @PathParam("id") String containerId,
            @QueryParam("t") String t)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("stop");

        if (Objects.nonNull(t))
            target = target.queryParam("t", t);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/restart")
    public String restartContainer(
            @PathParam("id") String containerId,
            @QueryParam("t") String t)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("restart");

        if (Objects.nonNull(t))
            target = target.queryParam("t", t);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/kill")
    public String killContainer(
            @PathParam("id") String containerId,
            @DefaultValue("SIGKILL") @QueryParam("signal") String signal)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("kill")
                .queryParam("signal", signal);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/update")
    public String updateContainer(
            @PathParam("id") String containerId,
            JsonObject content)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("update");

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/rename")
    public String renameContainer(
            @PathParam("id") String containerId,
            @QueryParam("name") String name)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("rename")
                .queryParam("name", name);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/pause")
    public String pauseContainer(
            @PathParam("id") String containerId)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("pause");

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/unpause")
    public String unpauseeContainer(
            @PathParam("id") String containerId)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("unpause");

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/wait")
    public String waitContainer(
            @PathParam("id") String containerId)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("wait");

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @DELETE
    @Path("{id}")
    public String deleteContainer(
            @PathParam("id") String containerId,
            @DefaultValue("false") @QueryParam("v") String v,
            @DefaultValue("false") @QueryParam("force") String force)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .queryParam("v", v)
                .queryParam("force", force);

        Response response = deleteResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("prune")
    public String prune(
            @QueryParam("filter") String filter)
            throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path("prune");

        if (Objects.nonNull(filter))
            target = target.queryParam("filter", filter);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @HEAD
    @Path("{id}/archive")
    public String archiveInfo(
            @PathParam("id") String containerId,
            @QueryParam("path") String path) throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("archive")
                .queryParam("path", path);

        Response response = headResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }


    @POST
    @Path("create")
    public String getContainers(
            @QueryParam("name") String name, JsonObject content) throws IOException, ExecutionException, InterruptedException {

        WebTarget target = resource().path("containers").path("create");

        if (Objects.nonNull(name))
            target = target.queryParam("name", name);

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

}

