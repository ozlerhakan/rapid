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
@Path("plugins")
public class Plugin extends DockerClient {

    @GET
    public JsonStructure listPlugins() {

        WebTarget target = resource().path("plugins");

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return Json.createReader(new StringReader(entity)).read();
    }

    @GET
    @Path("privileges")
    public JsonStructure privilegedPlugins(@QueryParam("name") String name) throws UnsupportedEncodingException {

        WebTarget target = resource().path("plugins").path("privileges");

        if (Objects.nonNull(name))
            target = target.queryParam("name", name);

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return Json.createReader(new StringReader(entity)).read();
    }

    @GET
    @Path("{id}/json")
    public JsonObject inspectPlugin(@PathParam("id") String id) {

        WebTarget target = resource().path("plugins").path(id).path("json");

        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @DELETE
    @Path("{id}")
    public JsonObject deletePlugin(@PathParam("id") String id,
                                   @DefaultValue("false") @QueryParam("force") boolean force) {

        WebTarget target = resource().path("plugins").path(id).queryParam("force", force);

        Response response = deleteResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/enable")
    public JsonStructure enablePlugin(@PathParam("id") String id,
                                      @DefaultValue("0") @QueryParam("timeout") int timeout) {

        WebTarget target = resource().path("plugins").path(id).path("enable").queryParam("timeout", timeout);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure structure;
        if (entity.isEmpty()) {
            structure = Json.createObjectBuilder().add("message", id + " plugin enabled.").build();
        } else {
            structure = Json.createReader(new StringReader(entity)).read();
        }
        return structure;
    }

    @POST
    @Path("{id}/disable")
    public JsonStructure disablePlugin(@PathParam("id") String id) {

        WebTarget target = resource().path("plugins").path(id).path("disable");

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure structure;
        if (entity.isEmpty()) {
            structure = Json.createObjectBuilder().add("message", id + " plugin disabled.").build();
        } else {
            structure = Json.createReader(new StringReader(entity)).read();
        }
        return structure;
    }

    @POST
    @Path("pull")
    public JsonStructure pullPlugin(@QueryParam("remote") String remote,
                                    @QueryParam("name") String name,
                                    JsonStructure content) {

        WebTarget target = resource().path("plugins").path("pull");

        if (Objects.nonNull(remote))
            target = target.queryParam("remote", remote);
        if (Objects.nonNull(name))
            target = target.queryParam("name", name);

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure result;
        if (entity.isEmpty()) {
            result = Json.createObjectBuilder().add("message", "plugin pulled.").build();
        } else {
            result = Json.createReader(new StringReader(entity)).read();
        }
        return result;
    }

    @POST
    @Path("{id}/push")
    public JsonStructure pushPlugin(@PathParam("id") String id) {

        WebTarget target = resource().path("plugins").path(id).path("push");

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure result;
        if (entity.isEmpty()) {
            result = Json.createObjectBuilder().add("message", id + " plugin pushed.").build();
        } else {
            result = Json.createReader(new StringReader(entity)).read();
        }
        return result;
    }

    @POST
    @Path("{id}/upgrade")
    public JsonStructure upgradePlugin(@PathParam("id") String id,
                                       @QueryParam("remote") String remote,
                                       JsonStructure content) {

        WebTarget target = resource().path("plugins").path(id).path("upgrade");

        if (Objects.nonNull(remote))
            target = target.queryParam("remote", remote);

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure result;
        if (entity.isEmpty()) {
            result = Json.createObjectBuilder().add("message", id + " plugin upgraded.").build();
        } else {
            result = Json.createReader(new StringReader(entity)).read();
        }
        return result;
    }

    @POST
    @Path("{id}/set")
    public JsonStructure settingPlugin(@PathParam("id") String id,
                                       JsonStructure content) {

        WebTarget target = resource().path("plugins").path(id).path("set");

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure result;
        if (entity.isEmpty()) {
            result = Json.createObjectBuilder().add("message", id + " plugin configuration set.").build();
        } else {
            result = Json.createReader(new StringReader(entity)).read();
        }
        return result;
    }

}
