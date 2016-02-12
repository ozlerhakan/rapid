package com.kodcu.rapid.config;

import com.kodcu.rapid.network.UnixFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

/**
 * Created by Hakan on 2/11/2016.
 */
public abstract class DockerClient {

    private static final String DEFAULT_UNIX_ENDPOINT = "unix:///var/run/docker.sock";
    private Client client;
    private URI originalUri;
    private URI sanitizeUri;

    public DockerClient() {
        this.init();
    }

    private void init (){
        originalUri = URI.create(DEFAULT_UNIX_ENDPOINT);
        sanitizeUri = UnixFactory.sanitizeUri(originalUri);

        final RegistryBuilder<ConnectionSocketFactory> registryBuilder =
                RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("unix", new UnixFactory(originalUri));

        final PoolingHttpClientConnectionManager cm =
                new PoolingHttpClientConnectionManager(registryBuilder.build());

        final RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout((int) SECONDS.toMillis(5))
                .setConnectTimeout((int) SECONDS.toMillis(5))
                .setSocketTimeout((int) SECONDS.toMillis(30))
                .build();

        final ClientConfig config = new ClientConfig()
                .connectorProvider(new ApacheConnectorProvider())
                .property(ApacheClientProperties.CONNECTION_MANAGER, cm)
                .property(ApacheClientProperties.REQUEST_CONFIG, requestConfig);

        client = ClientBuilder.newBuilder().withConfig(config).build();
    }

    protected WebTarget resource() {
        final WebTarget target = client.target(sanitizeUri);
        return target;
    }


    protected Response getResponse(WebTarget target) {
        try {
            return target.request(APPLICATION_JSON_TYPE).async().method("GET").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Response postResponse(WebTarget target, JsonObject content) throws ExecutionException, InterruptedException {
        return target.request(APPLICATION_JSON_TYPE).async().method("POST", Entity.entity(content, MediaType.APPLICATION_JSON)).get();
    }

    protected Response postResponse(WebTarget target) throws ExecutionException, InterruptedException {
        return target.request(APPLICATION_JSON_TYPE).async().method("POST").get();
    }

    protected Response deleteResponse(WebTarget target) throws ExecutionException, InterruptedException {
        return target.request().async().method("DELETE").get();
    }


}
