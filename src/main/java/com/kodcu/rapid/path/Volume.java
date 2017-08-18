package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.Json;
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
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import static com.kodcu.rapid.util.Networking.deleteResponse;
import static com.kodcu.rapid.util.Networking.getResponse;
import static com.kodcu.rapid.util.Networking.postResponse;

/**
 * Created by Hakan on 2/12/2016.
 */
@Path("volumes")
public class Volume extends DockerClient {

    @GET
    public Response listVolumes(
            @QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("volumes");
        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

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
    @Path("{id}")
    public Response inspectVolume(
            @PathParam("id") String id) {

        WebTarget target = resource().path("volumes").path(id);

        Response response = getResponse(target);
        try {
            return Response.status(response.getStatus())
                    .entity(response.readEntity(JsonObject.class))
                    .build();
        } finally {
            response.close();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteVolume(
            @PathParam("id") String id,
            @DefaultValue("false") @QueryParam("force") boolean force) {

        WebTarget target = resource().path("volumes").path(id).queryParam("force", force);

        Response response = deleteResponse(target);
        String value = response.readEntity(String.class);

        try {
            if (value.isEmpty()) {
                return Response.status(response.getStatus())
                        .entity(Json.createObjectBuilder().add("message", id + " deleted.").build())
                        .build();
            } else {
                return Response.status(response.getStatus())
                        .entity(Json.createReader(new StringReader(value)).readObject())
                        .build();
            }
        } finally {
            response.close();
        }
    }

    @POST
    @Path("prune")
    public Response pruneVolume(
            @QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("volumes");

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = postResponse(target);
        try {
            return Response.status(response.getStatus())
                    .entity(response.readEntity(JsonObject.class))
                    .build();
        } finally {
            response.close();
        }
    }

    @POST
    @Path("create")
    public Response createVolume(JsonObject content) {

        WebTarget target = resource().path("volumes").path("create");
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
