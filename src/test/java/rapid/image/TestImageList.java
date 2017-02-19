package rapid.image;

import com.kodcu.rapid.util.Networking;
import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by hakan on 16/02/2017.
 */
public class TestImageList extends ImageConfig {

    @Test
    public void shouldListImages() {
        final Response response = getResponse(target("images").path("json"));
        assertEquals(200, response.getStatus());
        final JsonArray responseContent = response.readEntity(JsonArray.class);
        response.close();
    }

    @Test
    public void shouldListUbuntuImages() {
        try {
            String filter = "{\"reference\":{\"ubuntu\":true}}";
            String encode = URLEncoder.encode(filter, "UTF-8");
            final Response response = getResponse(target("images").path("json").queryParam("filters", encode));
            System.out.println(response);
            assertEquals(200, response.getStatus());
            final JsonArray responseContent = response.readEntity(JsonArray.class);
            response.close();

        } catch (UnsupportedEncodingException e) {
            fail();
        }
    }

    @Test
    public void shouldImagesListEmpty() {
        try {

            String filter = "{\"reference\":{\"ubuntuss\":true}}";
            String encode = URLEncoder.encode(filter, "UTF-8");
            final Response response = getResponse(target("images").path("json").queryParam("filters", encode));
            System.out.println(response);
            assertEquals(200, response.getStatus());
            final JsonArray responseContent = response.readEntity(JsonArray.class);
            assertThat(0, is(responseContent.size()));
            response.close();

        } catch (UnsupportedEncodingException e) {
            fail();
        }

    }

}