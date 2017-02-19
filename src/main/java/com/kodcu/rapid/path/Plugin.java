package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

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
    public String listPlugins() {

        WebTarget target = resource().path("plugins");

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("privileges")
    public String privilegedPlugins(@QueryParam("filters") String filters) {

        WebTarget target = resource().path("plugins").path("privileges");

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("{id}/json")
    public String inspectPlugin(@PathParam("id") String id) {

        WebTarget target = resource().path("plugins").path(id).path("json");

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @DELETE
    @Path("{id}")
    public String deletePlugin(@PathParam("id") String id,
                               @DefaultValue("false") @QueryParam("force") boolean force) {

        WebTarget target = resource().path("plugins").path(id).queryParam("force", force);

        Response response = deleteResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/enable")
    public String enablePlugin(@PathParam("id") String id,
                               @DefaultValue("0") @QueryParam("timeout") int timeout) {

        WebTarget target = resource().path("plugins").path(id).path("enable").queryParam("timeout", timeout);

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/disable")
    public String disablePlugin(@PathParam("id") String id) {

        WebTarget target = resource().path("plugins").path(id).path("disable");

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("pull")
    public String pullPlugin(@QueryParam("remote") String remote,
                             @QueryParam("name") String name,
                             JsonObject content) {

        WebTarget target = resource().path("plugins").path("pull");

        if (Objects.nonNull(remote))
            target = target.queryParam("remote", remote);
        if (Objects.nonNull(name))
            target = target.queryParam("name", name);

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/push")
    public String pushPlugin(@PathParam("id") String id) {

        WebTarget target = resource().path("plugins").path(id).path("push");

        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("{id}/upgrade")
    public String upgradePlugin(
            @PathParam("id") String id,
            @QueryParam("remote") String remote,
            JsonObject content) {

        WebTarget target = resource().path("plugins").path(id).path("upgrade");

        if (Objects.nonNull(remote))
            target = target.queryParam("remote", remote);

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

}
