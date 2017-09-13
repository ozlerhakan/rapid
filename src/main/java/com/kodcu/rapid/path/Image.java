package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;
import com.kodcu.rapid.pojo.ResponseFrame;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kodcu.rapid.util.Constants.FILTERS;
import static com.kodcu.rapid.util.Constants.IMAGES;
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
    public Response listImages(@DefaultValue("false") @QueryParam("all") String all,
                               @DefaultValue("false") @QueryParam("digests") String digests,
                               @QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path(IMAGES).path("json")
                .queryParam("all", all)
                .queryParam("digests", digests);

        if (Objects.nonNull(filters))
            target = target.queryParam(FILTERS, URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        try {
            String raw = response.readEntity(String.class);
            JsonReader reader = Json.createReader(new StringReader(raw));
            return Response.status(response.getStatus()).entity(reader.read()).build();
        } finally {
            response.close();
        }
    }

    @GET
    @Path("{id}/json")
    public Response inspectImage(@PathParam("id") String id) {

        WebTarget target = resource().path(IMAGES).path(id).path("json");

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
    @Path("{id}/history")
    public Response historyImage(@PathParam("id") String id) {

        WebTarget target = resource().path(IMAGES).path(id).path("history");

        Response response = getResponse(target);

        try {
            String raw = response.readEntity(String.class);
            return Response
                    .status(response.getStatus())
                    .entity(Json.createReader(new StringReader(raw)).read())
                    .build();
        } finally {
            response.close();
        }
    }

    @GET
    @Path("search")
    public Response searchImages(@QueryParam("term") String term,
                                 @DefaultValue("25") @QueryParam("limit") int limit,
                                 @QueryParam(FILTERS) String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path(IMAGES).path("search").queryParam("term", term).queryParam("limit", limit);

        if (Objects.nonNull(filters))
            target = target.queryParam(FILTERS, URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        try {
            String raw = response.readEntity(String.class);
            return Response.status(response.getStatus())
                    .entity(Json.createReader(new StringReader(raw)).read())
                    .build();
        } finally {
            response.close();

        }
    }

    @POST
    @Path("commit")
    public Response commitImage(@QueryParam("container") String container,
                                @QueryParam("repo") String repo,
                                @QueryParam("tag") String tag,
                                @QueryParam("comment") String comment,
                                @QueryParam("author") String author,
                                @QueryParam("changes") String changes,
                                @DefaultValue("false") @QueryParam("pause") String pause,
                                JsonObject content) {

        WebTarget target = resource().path(IMAGES).path("commit").queryParam("pause", pause);

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
        try {
            JsonObject entity = response.readEntity(JsonObject.class);
            return Response.status(response.getStatus()).entity(entity).build();
        } finally {
            response.close();
        }
    }

    @POST
    @Path("{id}/tag")
    public ResponseFrame tagImage(@PathParam("id") String id,
                                  @QueryParam("repo") String repo,
                                  @QueryParam("tag") String tag) {

        WebTarget target = resource().path(IMAGES)
                .path(id).path("tag")
                .queryParam("tag", tag).queryParam("repo", repo);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        ResponseFrame frame = new ResponseFrame();
        frame.setId(id);

        if (entity.isEmpty())
            frame.setMessage("tagged as " + tag);
        else {
            JsonReader reader = Json.createReader(new StringReader(entity));
            JsonObject o = reader.readObject();
            frame.setMessage(o.getJsonString("message").getString());
        }
        return frame;
    }

    @POST
    @Path("prune")
    public Response pruneImages(@QueryParam(FILTERS) String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path(IMAGES).path("prune");

        if (Objects.nonNull(filters))
            target = target.queryParam(FILTERS, URLEncoder.encode(filters, "UTF-8"));

        Response response = postResponse(target);
        try {
            JsonObject entity = response.readEntity(JsonObject.class);
            return Response.status(response.getStatus()).entity(entity).build();
        } finally {
            response.close();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteImage(@PathParam("id") String id,
                                @DefaultValue("false") @QueryParam("force") String force,
                                @DefaultValue("false") @QueryParam("noprune") String noprune) {

        WebTarget target = resource().path(IMAGES)
                .path(id)
                .queryParam("force", force)
                .queryParam("noprune", noprune);

        Response response = deleteResponse(target);
        try {
            String raw = response.readEntity(String.class);
            return Response.status(response.getStatus())
                    .entity(Json.createReader(new StringReader(raw)).read())
                    .build();
        } finally {
            response.close();
        }
    }

    @POST
    @Path("create")
    public ResponseFrame createImage(@QueryParam("fromImage") String fromImage,
                                     @QueryParam("tag") String tag) {

        WebTarget target = resource().path(IMAGES).path("create")
                .queryParam("fromImage", fromImage)
                .queryParam("tag", tag);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();

        ResponseFrame f = new ResponseFrame();
        f.setId("");
        f.setMessage("No such image: " + fromImage + ":" + tag);

        if (!entity.isEmpty()) {
            String pattern = "\\{\\\"status\\\":\\\"(Status:.*)\\\"\\}";
            Pattern compile = Pattern.compile(pattern);
            Matcher matcher = compile.matcher(entity);

            if (matcher.find()) {
                String group = matcher.group(1);
                f.setMessage(group);
            } else
                f.setMessage("");
        }

        return f;
    }
}
