package rapid.image;

import org.junit.Test;

import javax.json.JsonArray;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by hakan on 16/02/2017.
 */
public class TestImageRemove extends ImageConfig {

    @Test
    public void shouldRemoveImage() {

        final Response create = postResponse(target("images").path("create").queryParam("fromImage", "busybox").queryParam("tag", "1.26"));
        System.out.println(create);
        assertEquals(200, create.getStatus());

        final Response delete = deleteResponse(target("images").path("busybox:1.26").queryParam("force", true).queryParam("noprune", false));
        System.out.println(delete);
        assertEquals(200, delete.getStatus());
        // array for 200
        final JsonArray responseContent = delete.readEntity(JsonArray.class);
        System.out.println(responseContent);
        create.close();
        delete.close();

    }

}