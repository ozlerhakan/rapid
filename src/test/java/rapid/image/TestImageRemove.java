package rapid.image;

import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by hakan on 16/02/2017.
 */
public class TestImageRemove extends ImageConfig {

    @Test
    public void shouldRemoveImage() {

        // need busybox:1.26 beforehand
        final Response delete = deleteResponse(target("images").path("busybox:1.26").queryParam("force", true).queryParam("noprune", false));
        System.out.println(delete);
        assertEquals(200, delete.getStatus());
        // array for 200
        final JsonStructure responseContent = delete.readEntity(JsonStructure.class);
        System.out.println(responseContent);

    }

}