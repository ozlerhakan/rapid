package rapid.container;

import org.junit.Test;

import javax.json.JsonObject;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by hakan on 16/02/2017.
 */
public class TestContainerStart extends ContainerConfig {

    private static String body = "{\n" +
            "  \"Hostname\": \"\",\n" +
            "  \"Domainname\": \"\",\n" +
            "  \"User\": \"\",\n" +
            "  \"AttachStdin\": false,\n" +
            "  \"AttachStdout\": true,\n" +
            "  \"AttachStderr\": true,\n" +
            "  \"Tty\": false,\n" +
            "  \"OpenStdin\": false,\n" +
            "  \"StdinOnce\": false,\n" +
            "  \"Cmd\": [\n" +
            "    \"date\"\n" +
            "  ],\n" +
            "  \"Entrypoint\": \"\",\n" +
            "  \"Image\": \"ubuntu\"" +
            "}";

    @Test
    public void shouldStartContainer()  {
        final WebTarget create = target("containers").path("create").queryParam("name", "joedoe");
        Response createResponse = postResponse(create, body);

        // ubuntu:latest neeeded
        assertEquals(200, createResponse.getStatus());
        final JsonObject responseContent = createResponse.readEntity(JsonObject.class);
        createResponse.close();

        final String containerId = responseContent.getJsonString("Id").getString();

        final Response start = postResponse(target("containers").path(containerId).path("start"));
        assertEquals(200, start.getStatus());
        start.close();

        final Response restart = postResponse(target("containers").path(containerId).path("restart"));
        // return body is empty
        assertEquals(200, start.getStatus());
        start.close();

        final WebTarget delete = target("containers").path(containerId).queryParam("v", true).queryParam("force", true);
        Response stopped = deleteResponse(delete);
        // return body is empty
        assertEquals(200, stopped.getStatus());
        stopped.close();

    }


}
