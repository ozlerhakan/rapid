package rapid.container;

import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.ACCEPTED;
import static org.junit.Assert.assertEquals;

/**
 * Created by hakan on 17/02/2017.
 */
public class TestContainerLog extends ContainerConfig {

    @Test
    public void inspectContainer() {
        final Response listContainers = target("containers").path("json").request(MediaType.APPLICATION_JSON).get();
        final JsonArray containers = listContainers.readEntity(JsonArray.class);
        final JsonObject container = (JsonObject) containers.get(0);
        final JsonString expectedId = container.getJsonString("Id");

        Response log = getResponse(target("containers").path(expectedId.getString()).path("logs").queryParam("stdout", true));
        assertEquals(ACCEPTED.getStatusCode(), log.getStatus());
    }
}
