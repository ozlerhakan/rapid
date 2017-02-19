package rapid.image;

import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * Created by hakan on 16/02/2017.
 */
public class TestImageHistory extends ImageConfig {

    @Test
    public void shouldDisplayImageHistory() throws ExecutionException, InterruptedException {
        final Response response = getAsycResponse(target("images").path("ubuntu:latest").path("history"));
        System.out.println(response);
        assertEquals(200, response.getStatus());
        final JsonArray responseContent = response.readEntity(JsonArray.class);
        //response.close();
    }

}