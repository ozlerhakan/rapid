package rapid.image;

import org.junit.Test;

import javax.json.JsonArray;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * Created by hakan on 16/02/2017.
 */
public class TestImageSearch extends ImageConfig {

    @Test
    public void shouldSearchImage() throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        String filter = "{\"is-automated\":{\"true\":true}}";
        String encode = URLEncoder.encode(filter, "UTF-8");
        final Response response = getAsycResponse(target("images").path("search")
                .queryParam("term", "ozlerhakan")
                .queryParam("limit", 1)
                .queryParam("filters", encode));

        System.out.println(response);

        assertEquals(200, response.getStatus());
        final JsonArray responseContent = response.readEntity(JsonArray.class);
        response.close();

    }

}