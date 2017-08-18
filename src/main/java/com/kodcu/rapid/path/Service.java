package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.Json;
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
import static javax.ws.rs.core.Response.Status.ACCEPTED;

/**
 * Created by hakan on 15/02/2017.
 */
@Path("services")
public class Service extends DockerClient {

    @GET
    public JsonStructure listServices(@QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("services");

        if (Objects.nonNull(filters)) {
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));
        }

        Response response = getResponse(target);
        try {
            String entity = response.readEntity(String.class);
            return Json.createReader(new StringReader(entity)).read();
        } finally {
            response.close();
        }
    }

    @POST
    @Path("create")
    public JsonStructure createService(JsonObject content) {

        WebTarget target = resource().path("services").path("create");

        Response response = postResponse(target, content);
        try {
            String entity = response.readEntity(String.class);
            return Json.createReader(new StringReader(entity)).read();
        } finally {
            response.close();
        }
    }

    @GET
    @Path("{id}")
    public JsonObject inspectService(@PathParam("id") String id) {

        WebTarget target = resource().path("services").path(id);

        Response response = getResponse(target);
        try {
            JsonObject entity = response.readEntity(JsonObject.class);
            return entity;
        } finally {
            response.close();
        }
    }

    @DELETE
    @Path("{id}")
    public JsonObject deleteService(@PathParam("id") String id) {

        WebTarget target = resource().path("services").path(id);

        Response response = deleteResponse(target);
        try {
            if (response.getStatus() == ACCEPTED.getStatusCode())
                return Json.createObjectBuilder().add("message", id + " service deleted.").build();
            else
                return response.readEntity(JsonObject.class);
        } finally {
            response.close();
        }
    }

    @POST
    @Path("{id}/update")
    public JsonObject updateService(@PathParam("id") String id,
                                    @QueryParam("version") int version,
                                    @DefaultValue("spec") @QueryParam("registryAuthFrom") String registryAuthFrom,
                                    JsonObject content) {

        WebTarget target = resource().path("services").path(id).path("update").queryParam("registryAuthFrom", registryAuthFrom);

        if (version != 0) {
            target = target.queryParam("version", version);
        }

        Response response = postResponse(target, content);
        try {
            JsonObject entity = response.readEntity(JsonObject.class);
            return entity;
        } finally {
            response.close();
        }
    }

    @GET
    @Path("{id}/logs")
    public JsonObject serviceLiogs(@PathParam("id") String id,
                                   @DefaultValue("false") @QueryParam("details") boolean details,
                                   @DefaultValue("false") @QueryParam("stdout") boolean stdout,
                                   @DefaultValue("false") @QueryParam("stderr") boolean stderr,
                                   @DefaultValue("0") @QueryParam("since") String since,
                                   @DefaultValue("false") @QueryParam("timestamps") boolean timestamps,
                                   @DefaultValue("all") @QueryParam("tail") String tail) {

        WebTarget target = resource().path("services").path(id).path("logs")
                .queryParam("details", details)
                .queryParam("follow", false)
                .queryParam("stdout", stdout)
                .queryParam("stderr", stderr)
                .queryParam("since", since)
                .queryParam("timestamps", timestamps)
                .queryParam("tail", tail);

        Response response = getResponse(target);
        try {
            int status = response.getStatus();
            if (status == ACCEPTED.getStatusCode()) {
                return Json.createObjectBuilder().add("message", response.readEntity(String.class)).build();
            } else {
                return response.readEntity(JsonObject.class);
            }
        } finally {
            response.close();
        }
    }


}
