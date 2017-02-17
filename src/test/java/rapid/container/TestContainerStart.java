package rapid.container;

import com.kodcu.rapid.util.Networking;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.json.JsonObject;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by hakan on 16/02/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void shouldStartContainer() throws ExecutionException, InterruptedException {
        final WebTarget create = target("containers").path("create").queryParam("name", "joedoe");
        Response createResponse = Networking.postResponse(create, body);

        // ubuntu:latest neeeded
        assertEquals(200, createResponse.getStatus());
        final JsonObject responseContent = createResponse.readEntity(JsonObject.class);
        createResponse.close();
        final String containerId = responseContent.getJsonString("Id").getString();

        final Response start = Networking.postResponse(target("containers").path(containerId).path("start"));
        assertEquals(200, start.getStatus());
        start.close();

        final Response restart = Networking.postResponse(target("containers").path(containerId).path("restart"));
        // return body is empty
        assertEquals(200, start.getStatus());
        start.close();

        final WebTarget delete = target("containers").path(containerId).queryParam("v", true).queryParam("force", true);
        Response stopped = Networking.deleteResponse(delete);
        // return body is empty
        assertEquals(200, stopped.getStatus());
        stopped.close();

    }


}
