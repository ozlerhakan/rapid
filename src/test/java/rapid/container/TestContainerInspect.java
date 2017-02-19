package rapid.container;

import com.kodcu.rapid.util.Networking;
import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by hakan on 17/02/2017.
 */
public class TestContainerInspect extends ContainerConfig {

    @Test
    public void inspectContainer() {
        final Response listContainers = target("containers").path("json").request(MediaType.APPLICATION_JSON).get();
        final JsonArray containers = listContainers.readEntity(JsonArray.class);
        final JsonObject container = (JsonObject) containers.get(0);
        final JsonString expectedId = container.getJsonString("Id");

        Response inspect = getResponse(target("containers").path(expectedId.getString()).path("json"));
        assertEquals(200, inspect.getStatus());

        final JsonObject actualContainer  = inspect.readEntity(JsonObject.class);
        assertEquals(expectedId.getString(), actualContainer.getJsonString("Id").getString());
    }
}
