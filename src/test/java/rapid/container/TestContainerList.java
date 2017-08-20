package rapid.container;

import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by hakan on 16/02/2017.
 */
public class TestContainerList extends ContainerConfig {

    @Test
    public void shouldListRunningContainers() {
        final Response response = getResponse(target("containers").path("json"));
        assertEquals(OK.getStatusCode(), response.getStatus());
        final JsonArray responseContent = response.readEntity(JsonArray.class);
        int expected = 1;
        assertEquals(expected, responseContent.size());
        response.close();
    }

    @Test
    public void shouldListAllContainers() {
        final Response response = getResponse(target("containers").path("json").queryParam("all", true));
        assertEquals(OK.getStatusCode(), response.getStatus());
        final JsonArray responseContent = response.readEntity(JsonArray.class);
        int expected = 1;
        assertThat(expected, is(responseContent.size()));
        response.close();
    }

    @Test
    public void shouldListContainersWithSize() {
        final Response response = getResponse(target("containers").path("json").queryParam("size", true));
        assertEquals(OK.getStatusCode(), response.getStatus());
        final JsonArray responseContent = response.readEntity(JsonArray.class);
        int expected = 1;
        assertEquals(expected, responseContent.size());
        //JsonObject obj = (JsonObject) responseContent.get(0);
        //assertThat(true, is(obj.containsKey("SizeRw")));
        response.close();
    }

    @Test
    public void shouldListContainersWithLimit() {
        final Response response = getResponse(target("containers").path("json").queryParam("limit", 1));
        assertEquals(OK.getStatusCode(), response.getStatus());
        final JsonArray responseContent = response.readEntity(JsonArray.class);
        int expected = 1;
        assertEquals(responseContent.size(), expected);
        response.close();
    }
}
