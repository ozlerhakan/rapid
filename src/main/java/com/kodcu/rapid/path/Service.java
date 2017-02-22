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

/**
 * Created by hakan on 15/02/2017.
 */
@Path("services")
public class Service extends DockerClient {

    @GET
    public JsonStructure listServices(@QueryParam("filters") String filters) throws UnsupportedEncodingException {

        WebTarget target = resource().path("services");

        if (Objects.nonNull(filters))
            target = target.queryParam("filters", URLEncoder.encode(filters, "UTF-8"));

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return Json.createReader(new StringReader(entity)).read();
    }

    @POST
    @Path("create")
    public JsonStructure createService(JsonObject content) {

        WebTarget target = resource().path("services").path("create");

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return Json.createReader(new StringReader(entity)).read();
    }

    @GET
    @Path("{id}")
    public JsonObject inspectService(@PathParam("id") String id) {

        WebTarget target = resource().path("services").path(id);

        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @DELETE
    @Path("{id}")
    public JsonObject deleteService(@PathParam("id") String id) {

        WebTarget target = resource().path("services").path(id);

        Response response = deleteResponse(target);
        JsonObject result;
        if (response.getStatus() == 200)
            result = Json.createObjectBuilder().add("message", id + " service deleted.").build();
        else
            result = response.readEntity(JsonObject.class);
        response.close();
        return result;
    }

    @POST
    @Path("{id}/update")
    public JsonObject updateService(@PathParam("id") String id,
                                    @QueryParam("version") int version,
                                    @DefaultValue("spec") @QueryParam("registryAuthFrom") String registryAuthFrom,
                                    JsonObject content) {

        WebTarget target = resource().path("services").path(id).path("update").queryParam("registryAuthFrom", registryAuthFrom);

        if (version != 0)
            target = target.queryParam("version", version);

        Response response = postResponse(target, content);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
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
        String entity = response.readEntity(String.class);
        int status = response.getStatus();
        JsonObject result;
        if (status == 200 || status == 101)
            result = Json.createObjectBuilder().add("message", response.readEntity(String.class)).build();
        else
            result = response.readEntity(JsonObject.class);
        response.close();
        return result;
    }


}
