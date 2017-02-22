package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.Objects;

import static com.kodcu.rapid.util.Networking.getResponse;
import static com.kodcu.rapid.util.Networking.postResponse;

/**
 * Created by hakan on 15/02/2017.
 */
@Path("swarm")
public class Swarm extends DockerClient {

    @GET
    public JsonObject inspectSwarm() {

        WebTarget target = resource().path("swarm");
        Response response = getResponse(target);
        JsonObject entity = response.readEntity(JsonObject.class);
        response.close();
        return entity;
    }

    @POST
    @Path("init")
    public JsonObject initSwarm(JsonObject content) {

        WebTarget target = resource().path("swarm").path("init");
        Response response = postResponse(target, content);

        JsonObject message;
        if (response.getStatus() == 200) {
            message = Json.createObjectBuilder().add("message", response.readEntity(String.class)).build();
        } else
            message = response.readEntity(JsonObject.class);

        response.close();
        return message;
    }

    @POST
    @Path("join")
    public JsonObject joinSwarm(JsonObject content) {

        WebTarget target = resource().path("swarm").path("join");
        Response response = postResponse(target, content);

        JsonObject message;
        if (response.getStatus() == 200) {
            message = Json.createObjectBuilder().add("message", "OK").build();
        } else
            message = response.readEntity(JsonObject.class);

        response.close();
        return message;
    }

    @POST
    @Path("leave")
    public JsonStructure leaveSwarm(@DefaultValue("false") @QueryParam("force") boolean force) {

        WebTarget target = resource().path("swarm").path("leave").queryParam("force", force);
        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure structure;
        if (entity.isEmpty()) {
            structure = Json.createObjectBuilder().add("message", "Node left.").build();
        } else {
            structure = Json.createReader(new StringReader(entity)).read();
        }
        return structure;
    }

    @POST
    @Path("update")
    public JsonStructure updateSwarm(
            @QueryParam("version") String version,
            @DefaultValue("false") @QueryParam("rotateWorkerToken") boolean rotateWorkerToken,
            @DefaultValue("false") @QueryParam("rotateManagerToken") boolean rotateManagerToken,
            @DefaultValue("false") @QueryParam("rotateManagerUnlockKey") boolean rotateManagerUnlockKey,
            JsonObject content) {

        WebTarget target = resource().path("swarm").path("update")
                .queryParam("rotateWorkerToken", rotateWorkerToken)
                .queryParam("rotateManagerToken", rotateManagerToken)
                .queryParam("rotateManagerUnlockKey", rotateManagerUnlockKey);

        if (Objects.nonNull(version))
            target = target.queryParam("version", version);

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure structure;
        if (entity.isEmpty()) {
            structure = Json.createObjectBuilder().add("message", "Swarm updated.").build();
        } else {
            structure = Json.createReader(new StringReader(entity)).read();
        }
        return structure;
    }

    @GET
    @Path("unlockkey")
    public JsonStructure unluckKeySwarm() {
        WebTarget target = resource().path("swarm").path("unlockkey");
        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure structure;
        if (entity.isEmpty()) {
            structure = Json.createObjectBuilder().add("message", "Swarm unlockkey.").build();
        } else {
            structure = Json.createReader(new StringReader(entity)).read();
        }
        return structure;
    }

    @POST
    @Path("unlock")
    public JsonStructure unlockSwarm(JsonObject content) {

        WebTarget target = resource().path("swarm").path("unlock");
        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();

        JsonStructure structure;
        if (entity.isEmpty()) {
            structure = Json.createObjectBuilder().add("message", "Swarm unlock.").build();
        } else {
            structure = Json.createReader(new StringReader(entity)).read();
        }
        return structure;
    }
}
