package com.kodcu.rapid.path;

import com.kodcu.rapid.config.DockerClient;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

import static com.kodcu.rapid.util.Networking.getResponse;

/**
 * Created by hakan on 11/09/2017
 */
@Path("distribution")
public class Distribution extends DockerClient {

    @GET
    @Path("{params:.+}/json")
    public Response listContainers(@PathParam("params") List<PathSegment> params) {

        String path = params.stream().map(PathSegment::getPath).collect(Collectors.joining("/"));
        WebTarget target = resource().path("distribution").path(path).path("json");

        Response response = getResponse(target);

        try {
            return Response.status(response.getStatus()).entity(response.readEntity(JsonObject.class)).build();
        } finally {
            response.close();
        }
    }
}
