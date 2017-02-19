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
 * Created by Hakan on 2/10/2016.
 */
@Path("containers")
public class Container extends DockerClient {

    @GET
    @Path("json")
    public JsonStructure listContainers(
            @DefaultValue("false") @QueryParam("all") String all,
            @QueryParam("limit") int limit,
            @DefaultValue("false") @QueryParam("size") String size,
            @QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("containers").path("json").queryParam("all", all).queryParam("size", size);

        if (limit != 0)
            target = target.queryParam("limit", limit);
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
    // inspect
    public JsonObject inspectContainer(
            @PathParam("id") String containerId,
            @DefaultValue("false") @QueryParam("size") String size) {

        WebTarget target = resource().path("containers")
                .path(containerId).path("json")
                .queryParam("size", size);

        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}/top")
    public JsonObject containerTopProcess(
            @PathParam("id") String containerId,
            @DefaultValue("-ef") @QueryParam("ps_args") String ps) {

        WebTarget target = resource().path("containers")
                .path(containerId).path("top")
                .queryParam("ps", ps);

        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}/logs")
    public String getContainerLogs(
            @PathParam("id") String containerId,
            @DefaultValue("false") @QueryParam("stdout") String stdout,
            @DefaultValue("false") @QueryParam("stderr") String stderr,
            @DefaultValue("0") @QueryParam("since") String since,
            @DefaultValue("false") @QueryParam("timestamps") String timestamps,
            @DefaultValue("all") @QueryParam("tail") String tail) {

        WebTarget target = resource().path("containers").path(containerId).path("logs");

        // follow = false always
        target = target.queryParam("follow", false)
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
            @PathParam("id") String containerId) {

        WebTarget target = resource().path("containers").path(containerId).path("changes");

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/start")
    public String startContainer(
            @PathParam("id") String id,
            @QueryParam("detachKeys") String detachKeys) {

        WebTarget target = resource().path("containers")
                .path(id)
                .path("start");

//        if (Objects.nonNull(detachKeys))
//            target = target.queryParam("detachKeys", detachKeys);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/stop")
    public String stopContainer(
            @PathParam("id") String containerId,
            @QueryParam("t") String t) {

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
            @QueryParam("t") String t) {

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
            @DefaultValue("SIGKILL") @QueryParam("signal") String signal) {

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
            JsonObject content) {

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
            @QueryParam("name") String name) {

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
            @PathParam("id") String containerId) {

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
            @PathParam("id") String containerId) {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("unpause");

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
            @DefaultValue("false") @QueryParam("force") String force) {

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
    public JsonObject prune(
            @QueryParam("filter") String filter) {

        WebTarget target = resource().path("containers")
                .path("prune");

        if (Objects.nonNull(filter))
            target = target.queryParam("filter", filter);

        Response response = postResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @POST
    @Path("create")
    public JsonObject createContainer(
            @QueryParam("name") String name, JsonObject content) {

        WebTarget target = resource().path("containers").path("create");

        if (Objects.nonNull(name))
            target = target.queryParam("name", name);

        Response response = postResponse(target, content);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

}

