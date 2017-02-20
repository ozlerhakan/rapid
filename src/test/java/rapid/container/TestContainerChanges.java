package rapid.container;

import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by hakan on 17/02/2017.
 */
public class TestContainerChanges extends ContainerConfig {

    @Test
    public void shouldInspectContainer() {
        final Response listContainers = target("containers").path("json").request(MediaType.APPLICATION_JSON).get();
        final JsonArray containers = listContainers.readEntity(JsonArray.class);
        final JsonObject runningCurrentContainer = (JsonObject) containers.get(0);
        final JsonString expectedId = runningCurrentContainer.getJsonString("Id");

        Response changes = getResponse(target("containers").path(expectedId.getString()).path("changes"));
        assertEquals(200, changes.getStatus());
    }
}
