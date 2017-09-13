package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.Json;
import javax.json.JsonArray;
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

import static com.kodcu.rapid.util.Constants.NETWORKS;
import static com.kodcu.rapid.util.Networking.deleteResponse;
import static com.kodcu.rapid.util.Networking.getResponse;
import static com.kodcu.rapid.util.Networking.postResponse;
import static javax.ws.rs.core.Response.Status.ACCEPTED;

/**
 * Created by Hakan on 2/12/2016.
 */
@Path(NETWORKS)
public class Network extends DockerClient {

    @GET
    public Response listNetworks(@QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path(NETWORKS);

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        try {
            if (response.getStatus() == ACCEPTED.getStatusCode())
                return Response.ok(response.readEntity(JsonArray.class)).build();
            else
                return Response.status(response.getStatus()).entity(response.readEntity(JsonObject.class)).build();
        } finally {
            response.close();
        }
    }

    @GET
    @Path("{id}")
    public Response inspectNetwork(@PathParam("id") String id) {

        WebTarget target = resource().path(NETWORKS).path(id);

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

    @DELETE
    @Path("{id}")
    public Response deleteNetwork(@PathParam("id") String id) {

        WebTarget target = resource().path(NETWORKS).path(id);

        Response response = deleteResponse(target);
        String entity = response.readEntity(String.class);

        try {
            if (entity.isEmpty()) {
                return Response.status(response.getStatus())
                        .entity(Json.createObjectBuilder().add("message", id + " removed.").build())
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
    public Response pruneNetwork() {

        WebTarget target = resource().path(NETWORKS);
        Response response = postResponse(target);
        try {
            return Response.status(response.getStatus()).entity(response.readEntity(JsonObject.class)).build();
        } finally {
            response.close();
        }
    }

    @POST
    @Path("{id}/connect")
    public Response connectToNetwork(@PathParam("id") String id,
                                     JsonObject content) {

        WebTarget target = resource().path(NETWORKS).path(id).path("connect");

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);

        try {
            if (entity.isEmpty()) {
                return Response.status(response.getStatus())
                        .entity(Json.createObjectBuilder().add("message", id + " connected.").build())
                        .build();
            } else {
                return Response.status(response.getStatus()).entity(Json.createReader(new StringReader(entity)).read()).build();
            }
        } finally {
            response.close();
        }
    }

    @POST
    @Path("{id}/disconnect")
    public Response disconnectToNetwork(@PathParam("id") String id,
                                        JsonObject content) {

        WebTarget target = resource().path(NETWORKS).path(id).path("disconnect");

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        try {
            if (entity.isEmpty()) {
                return Response.status(response.getStatus())
                        .entity(Json.createObjectBuilder().add("message", id + " disconnected.").build())
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
    @Path("create")
    public Response createNetwork(JsonObject content) {

        WebTarget target = resource().path(NETWORKS).path("create");

        Response response = postResponse(target, content);
        try {
            return Response.status(response.getStatus()).entity(response.readEntity(JsonObject.class)).build();
        } finally {
            response.close();
        }
    }
}
