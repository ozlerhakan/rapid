package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.JsonObject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by hakan on 15/02/2017.
 */
@Path("swarm")
public class Swarm extends DockerClient {

    @GET
    public String inspectSwarm()
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("swarm");
        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("init")
    public String initSwarm(JsonObject content)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("swarm").path("init");
        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("join")
    public String joinSwarm(JsonObject content)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("swarm").path("join");
        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("leave")
    public String leaveSwarm(@DefaultValue("false") @QueryParam("force") boolean force)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("swarm").path("leave").queryParam("force", force);
        Response response = postResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("update")
    public String updateSwarm(
            @DefaultValue("false") @QueryParam("force") boolean force,
            @QueryParam("version") String version,
            @DefaultValue("false") @QueryParam("rotateWorkerToken") boolean rotateWorkerToken,
            @DefaultValue("false") @QueryParam("rotateManagerToken") boolean rotateManagerToken,
            @DefaultValue("false") @QueryParam("rotateManagerUnlockKey") boolean rotateManagerUnlockKey,
            JsonObject content)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("swarm").path("update")
                .queryParam("rotateWorkerToken", rotateWorkerToken)
                .queryParam("rotateManagerToken", rotateManagerToken)
                .queryParam("rotateManagerUnlockKey", rotateManagerUnlockKey);

        if (Objects.nonNull(version))
            target = target.queryParam("version", version);

        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @GET
    @Path("unlockkey")
    public String unluckKeySwarm()
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("swarm").path("unlockkey");
        Response response = getResponse(target);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }

    @POST
    @Path("unlock")
    public String unlockSwarm(JsonObject content)
            throws ExecutionException, InterruptedException {

        WebTarget target = resource().path("swarm").path("unlock");
        Response response = postResponse(target, content);
        String entity = response.readEntity(String.class);
        response.close();
        return entity;
    }
}
