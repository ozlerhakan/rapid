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
import static javax.ws.rs.core.Response.Status.ACCEPTED;

/**
 * Created by hakan on 15/02/2017.
 */
@Path("swarm")
public class Swarm extends DockerClient {

    @GET
    public Response inspectSwarm() {

        WebTarget target = resource().path("swarm");
        Response response = getResponse(target);
        try {
            return Response.status(response.getStatus()).entity(response.readEntity(JsonObject.class)).build();
        } finally {
            response.close();
        }
    }

    @POST
    @Path("init")
    public Response initSwarm(JsonObject content) {

        WebTarget target = resource().path("swarm").path("init");
        Response response = postResponse(target, content);

        try {
            if (response.getStatus() == ACCEPTED.getStatusCode()) {
                return Response.ok(Json.createObjectBuilder().add("message", response.readEntity(String.class)).build())
                        .build();
            } else
                return Response.status(response.getStatus())
                        .entity(response.readEntity(JsonObject.class))
                        .build();
        } finally {
            response.close();
        }
    }

    @POST
    @Path("join")
    public Response joinSwarm(JsonObject content) {

        WebTarget target = resource().path("swarm").path("join");
        Response response = postResponse(target, content);

        try {
            if (response.getStatus() == ACCEPTED.getStatusCode()) {
                return Response.ok(Json.createObjectBuilder().add("message", "OK").build()).build();
            } else {
                return Response.status(response.getStatus()).entity(response.readEntity(JsonObject.class)).build();
            }
        } finally {
            response.close();
        }
    }

    @POST
    @Path("leave")
    public Response leaveSwarm(@DefaultValue("false") @QueryParam("force") boolean force) {

        WebTarget target = resource().path("swarm").path("leave").queryParam("force", force);
        Response response = postResponse(target);
        String entity = response.readEntity(String.class);

        try {
            if (entity.isEmpty()) {
                return Response.status(response.getStatus())
                        .entity(Json.createObjectBuilder().add("message", "Node left.").build())
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
    @Path("update")
    public Response updateSwarm(@QueryParam("version") String version,
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

        try {
            if (entity.isEmpty()) {
                return Response.status(response.getStatus())
                        .entity(Json.createObjectBuilder().add("message", "Swarm updated.").build())
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

    @GET
    @Path("unlockkey")
    public Response unluckKeySwarm() {
        WebTarget target = resource().path("swarm").path("unlockkey");

        Response response = getResponse(target);
        String entity = response.readEntity(String.class);

        try {
            if (entity.isEmpty()) {
                return Response.status(response.getStatus())
                        .entity(Json.createObjectBuilder().add("message", "Swarm unlockkey.").build())
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
    @Path("unlock")
    public Response unlockSwarm(JsonObject content) {

        WebTarget target = resource().path("swarm").path("unlock");

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);

        try {
            if (entity.isEmpty()) {
                return Response.status(response.getStatus())
                        .entity(Json.createObjectBuilder().add("message", "Swarm unlock.").build())
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
