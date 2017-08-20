package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;
import com.kodcu.rapid.pojo.ResponseFrame;

import javax.json.Json;
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
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import static com.kodcu.rapid.util.Networking.deleteResponse;
import static com.kodcu.rapid.util.Networking.getResponse;
import static com.kodcu.rapid.util.Networking.postResponse;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

/**
 * Created by Hakan on 2/10/2016.
 */
@Path("containers")
public class Container extends DockerClient {

    @GET
    @Path("json")
    public Response listContainers(@DefaultValue("false") @QueryParam("all") String all,
                                   @QueryParam("limit") int limit,
                                   @DefaultValue("false") @QueryParam("size") String size,
                                   @QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("containers").path("json").queryParam("all", all).queryParam("size", size);

        if (limit != 0)
            target = target.queryParam("limit", limit);
        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);

        try {
            if (response.getStatus() == OK.getStatusCode())
                return Response.ok(response.readEntity(JsonArray.class)).build();
            else
                return Response.status(response.getStatus()).entity(response.readEntity(JsonObject.class)).build();
        } finally {
            response.close();
        }
    }

    @GET
    @Path("{id}/json")
    // inspect
    public Response inspectContainer(@PathParam("id") String containerId,
                                     @DefaultValue("false") @QueryParam("size") String size) {

        WebTarget target = resource().path("containers")
                .path(containerId).path("json")
                .queryParam("size", size);

        Response response = getResponse(target);
        try {
            return Response.status(response.getStatus()).entity(response.readEntity(JsonObject.class)).build();
        } finally {
            response.close();
        }
    }

    @GET
    @Path("{id}/top")
    public Response containerTopProcess(@PathParam("id") String containerId,
                                        @DefaultValue("-ef") @QueryParam("ps_args") String ps) {

        WebTarget target = resource().path("containers")
                .path(containerId).path("top")
                .queryParam("ps", ps);

        Response response = getResponse(target);
        try {
            return Response.status(response.getStatus())
                    .entity(response.readEntity(JsonObject.class))
                    .build();
        } finally {
            response.close();
        }
    }

    @GET
    @Path("{id}/logs")
    public ResponseFrame getContainerLogs(@PathParam("id") String containerId,
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
        ResponseFrame frame = new ResponseFrame();
        try {
            if (response.getStatus() == OK.getStatusCode()) {
                String entity = response.readEntity(String.class);
                frame.setMessage(entity);
            } else if (response.getStatus() == NOT_FOUND.getStatusCode()) {
                frame.setMessage("No such container: " + containerId);
            } else {
                frame.setMessage("Something went wrong.");
            }
        } finally {
            response.close();

        }
        return frame;
    }

    @GET
    @Path("{id}/changes")
    public Response getContainerChanges(@PathParam("id") String containerId) {

        WebTarget target = resource().path("containers").path(containerId).path("changes");
        Response response = getResponse(target);

        try {
            if (response.getStatus() == OK.getStatusCode())
                return Response.ok(response.readEntity(JsonArray.class)).build();
            else
                return Response.status(response.getStatus())
                        .entity(response.readEntity(JsonObject.class))
                        .build();
        } finally {
            response.close();
        }
    }

    @POST
    @Path("{id}/start")
    public Response startContainer(@PathParam("id") String id,
                                   @QueryParam("detachKeys") String detachKeys) {

        WebTarget target = resource().path("containers")
                .path(id)
                .path("start");

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);

        try {
            if (entity.isEmpty()) {
                return Response.ok(Json.createObjectBuilder().add("message", id + " started.").build())
                        .build();
            } else {
                return Response.status(response.getStatus())
                        .entity(Json.createReader(new StringReader(entity)).read())
                        .build();
            }
        } finally {
            response.close();

        }
    }

    @POST
    @Path("{id}/stop")
    public Response stopContainer(@PathParam("id") String containerId,
                                  @QueryParam("t") String t) {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("stop");

        if (Objects.nonNull(t))
            target = target.queryParam("t", t);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);

        try {
            if (entity.isEmpty()) {
                return Response.ok(Json.createObjectBuilder().add("message", containerId + " stopped.").build())
                        .build();
            } else {
                return Response.status(response.getStatus())
                        .entity(Json.createReader(new StringReader(entity)).read())
                        .build();
            }
        } finally {
            response.close();
        }
    }

    @POST
    @Path("{id}/restart")
    public Response restartContainer(@PathParam("id") String containerId,
                                     @QueryParam("t") String t) {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("restart");

        if (Objects.nonNull(t))
            target = target.queryParam("t", t);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);

        JsonStructure structure;
        try {
            if (entity.isEmpty()) {
                return Response.ok(Json.createObjectBuilder().add("message", containerId + " restarted.").build())
                        .build();
            } else {
                return Response.status(response.getStatus())
                        .entity(Json.createReader(new StringReader(entity)).read())
                        .build();
            }
        } finally {
            response.close();
        }
    }

    @POST
    @Path("{id}/kill")
    public Response killContainer(@PathParam("id") String containerId,
                                  @DefaultValue("SIGKILL") @QueryParam("signal") String signal) {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("kill")
                .queryParam("signal", signal);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);

        try {
            if (entity.isEmpty()) {
                return Response.ok(Json.createObjectBuilder().add("message", containerId + " killed.").build())
                        .build();
            } else {
                return Response
                        .status(response.getStatus())
                        .entity(Json.createReader(new StringReader(entity)).read())
                        .build();
            }
        } finally {
            response.close();
        }
    }

    @POST
    @Path("{id}/update")
    public Response updateContainer(@PathParam("id") String containerId,
                                    JsonObject content) {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("update");

        Response response = postResponse(target, content);
        try {
            JsonObject entity = response.readEntity(JsonObject.class);
            return Response.status(response.getStatus())
                    .entity(entity).build();
        } finally {
            response.close();
        }
    }

    @POST
    @Path("{id}/rename")
    public Response renameContainer(@PathParam("id") String containerId,
                                    @QueryParam("name") String name) {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("rename")
                .queryParam("name", name);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        try {
            if (entity.isEmpty()) {
                return Response.ok(Json.createObjectBuilder().add("message", containerId + " renamed as " + name).build())
                        .build();
            } else {
                return Response.status(response.getStatus()).entity(Json.createReader(new StringReader(entity)).read()).build();
            }
        } finally {
            response.close();
        }
    }

    @POST
    @Path("{id}/pause")
    public Response pauseContainer(@PathParam("id") String containerId) {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("pause");

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);

        JsonStructure structure;
        try {
            if (entity.isEmpty()) {
                return Response.ok(Json.createObjectBuilder().add("message", containerId + " paused.").build())
                        .build();
            } else {
                return Response.status(response.getStatus())
                        .entity(Json.createReader(new StringReader(entity)).read())
                        .build();
            }
        } finally {
            response.close();
        }
    }

    @POST
    @Path("{id}/unpause")
    public Response unpauseeContainer(@PathParam("id") String containerId) {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .path("unpause");

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);

        try {
            if (entity.isEmpty()) {
                return Response.ok(Json.createObjectBuilder().add("message", containerId + " unpaused.").build())
                        .build();
            } else {
                return Response.status(response.getStatus())
                        .entity(Json.createReader(new StringReader(entity)).read())
                        .build();
            }
        } finally {
            response.close();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteContainer(@PathParam("id") String containerId,
                                    @DefaultValue("false") @QueryParam("v") String v,
                                    @DefaultValue("false") @QueryParam("force") String force) {

        WebTarget target = resource().path("containers")
                .path(containerId)
                .queryParam("v", v)
                .queryParam("force", force);

        Response response = deleteResponse(target);
        String entity = response.readEntity(String.class);

        try {
            if (entity.isEmpty()) {
                return Response.ok(Json.createObjectBuilder().add("message", containerId + " deleted.").build())
                        .build();
            } else {
                return Response.status(response.getStatus())
                        .entity(Json.createReader(new StringReader(entity)).read())
                        .build();
            }
        } finally {
            response.close();
        }
    }

    @POST
    @Path("prune")
    public Response prune(@QueryParam("filter") String filter) {

        WebTarget target = resource().path("containers")
                .path("prune");

        if (Objects.nonNull(filter))
            target = target.queryParam("filter", filter);

        Response response = postResponse(target);
        try {
            JsonObject entity = response.readEntity(JsonObject.class);
            return Response.status(response.getStatus())
                    .entity(entity)
                    .build();
        } finally {
            response.close();
        }
    }

    @POST
    @Path("create")
    public Response createContainer(@QueryParam("name") String name, JsonObject content) {

        WebTarget target = resource().path("containers").path("create");

        if (Objects.nonNull(name))
            target = target.queryParam("name", name);

        Response response = postResponse(target, content);
        try {
            return Response.status(response.getStatus())
                    .entity(response.readEntity(JsonObject.class))
                    .build();
        } finally {
            response.close();
        }
    }

}

