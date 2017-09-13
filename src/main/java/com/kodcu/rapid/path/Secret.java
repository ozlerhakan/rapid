package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
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
 * Created by hakan on 15/02/2017.
 */
@Path("secrets")
public class Secret extends DockerClient {

    @GET
    public Response getSecrets(@QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("secrets");

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);

        try {
            return Response.status(response.getStatus())
                    .entity(Json.createReader(new StringReader(entity)).read())
                    .build();
        } finally {
            response.close();
        }
    }

    @POST
    @Path("create")
    public Response createSecret(JsonObject content) {

        WebTarget target = resource().path("secrets").path("create");
        Response response = postResponse(target, content);

        try {
            return Response.status(response.getStatus())
                    .entity(response.readEntity(JsonObject.class)).build();
        } finally {
            response.close();
        }
    }

    @GET
    @Path("{id}")
    public Response inspectSecret(@PathParam("id") String id) {

        WebTarget target = resource().path("secrets").path(id);
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
    public Response deleteSecret(@PathParam("id") String id) {

        WebTarget target = resource().path("secrets").path(id);

        Response response = deleteResponse(target);
        String entity = response.readEntity(String.class);

        try {
            if (entity.isEmpty()) {
                return Response.ok(Json.createObjectBuilder().add("message", id + " removed.").build())
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
    @Path("{id}/update")
    public Response updateSecret(@PathParam("id") String id,
                                 @QueryParam("version") String version,
                                 JsonObject content) {

        WebTarget target = resource().path("secrets").path(id).path("update").queryParam("version", version);

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);

        try {
            if (entity.isEmpty()) {
                return Response.ok(Json.createObjectBuilder().add("message", id + " updated.").build())
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

}
