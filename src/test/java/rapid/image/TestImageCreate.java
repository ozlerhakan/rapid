package rapid.image;

import org.junit.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * Created by hakan on 16/02/2017.
 */
public class TestImageCreate extends ImageConfig {

    @Test
    public void shouldCreateImage() {
        final Response response = postResponse(target("images").path("create").queryParam("fromImage","alpine:3.2").queryParam("tag","3.2"));
        assertEquals(200, response.getStatus());
        response.close();
    }

}