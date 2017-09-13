package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
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

import static com.kodcu.rapid.util.Constants.CONFIGS;
import static com.kodcu.rapid.util.Networking.deleteResponse;
import static com.kodcu.rapid.util.Networking.getResponse;
import static com.kodcu.rapid.util.Networking.postResponse;

/**
 * Created by hakan on 30/08/2017
 */
@Path("configs")
public class Config extends DockerClient {

    @GET
    public Response listConfigs(@QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path(CONFIGS);

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        String raw = response.readEntity(String.class);

        try (JsonReader json = Json.createReader(new StringReader(raw))) {
            return Response.status(response.getStatus())
                    .entity(json.read())
                    .build();
        } finally {
            response.close();
        }
    }

    @GET
    @Path("{id}")
    public Response inspectConfig(@PathParam("id") String configId) {

        WebTarget target = resource().path(CONFIGS).path(configId);

        Response response = getResponse(target);
        try {
            JsonObject entity = response.readEntity(JsonObject.class);
            return Response.status(response.getStatus())
                    .entity(entity)
                    .build();
        } finally {
            response.close();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteConfig(@PathParam("id") String configId) {

        WebTarget target = resource().path(CONFIGS).path(configId);
        Response response = deleteResponse(target);

        String entity = response.readEntity(String.class);

        if (entity.isEmpty()) {
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("id", configId);
            jsonObject.add("message", "the config is deleted.");

            return Response.ok(jsonObject.build()).build();
        }

        try (JsonReader json = Json.createReader(new StringReader(entity))) {
            return Response.status(response.getStatus()).entity(json.read()).build();
        }
    }

    @POST
    @Path("create")
    public Response createConfig(JsonObject content) {

        WebTarget target = resource().path(CONFIGS).path("create");

        Response response = postResponse(target, content);
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
    @Path("{id}/update")
    public Response updateConfig(@PathParam("id") String configId,
                                 @QueryParam("version") String version,
                                 JsonObject content) {

        WebTarget target = resource().path(CONFIGS)
                .path(configId)
                .path("update")
                .queryParam("version", version);

        Response response = postResponse(target, content);
        try {

            String entity = response.readEntity(String.class);

            if (entity.isEmpty()) {
                JsonObjectBuilder jsonObject = Json.createObjectBuilder();
                jsonObject.add("id", configId);
                jsonObject.add("message", "the config is updated.");

                return Response.ok(jsonObject.build()).build();
            }

            try (JsonReader json = Json.createReader(new StringReader(entity))) {
                return Response.status(response.getStatus()).entity(json.read()).build();
            }
        } finally {
            response.close();
        }
    }

}
