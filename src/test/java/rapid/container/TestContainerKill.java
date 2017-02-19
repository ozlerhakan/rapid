package rapid.container;

import org.junit.Test;

import javax.json.JsonObject;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by hakan on 16/02/2017.
 */
public class TestContainerKill extends ContainerConfig {

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
            "  \"sleep\",\"10000\""+
            "  ],\n" +
            "  \"Entrypoint\": \"\",\n" +
            "  \"Image\": \"ubuntu\"" +
            "}";

    @Test
    public void shouldKillContainer() {
        final WebTarget create = target("containers").path("create").queryParam("name", "killjoe");
        Response createResponse = postResponse(create, body);

        // ubuntu:latest neeeded
        assertEquals(200, createResponse.getStatus());
        final JsonObject responseContent = createResponse.readEntity(JsonObject.class);
        final String containerId = responseContent.getJsonString("Id").getString();
        createResponse.close();

        final Response start = postResponse(target("containers").path(containerId).path("start"));
        assertEquals(200, start.getStatus());
        start.close();

        final Response kill = postResponse(target("containers").path(containerId).path("kill").queryParam("signal","KILL"));
        // return body is empty
        assertEquals(200, start.getStatus());
        start.close();

        final WebTarget target = target("containers").path(containerId).queryParam("v", true).queryParam("force", true);
        Response response = deleteResponse(target);

    }


}
