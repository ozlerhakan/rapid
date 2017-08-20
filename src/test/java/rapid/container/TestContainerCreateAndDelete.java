package rapid.container;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.json.JsonObject;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by hakan on 16/02/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestContainerCreateAndDelete extends ContainerConfig {

    private static String body = "{\n" +
            "  \"Hostname\": \"\",\n" +
            "  \"Domainname\": \"\",\n" +
            "  \"User\": \"\",\n" +
            "  \"AttachStdin\": false,\n" +
            "  \"AttachStdout\": true,\n" +
            "  \"AttachStderr\": true,\n" +
            "  \"Tty\": false,\n" +
            "  \"OpenStdin\": false,\n" +
            "  \"StdinOnce\": false,\n" +
            "  \"Env\": [\n" +
            "    \"FOO=bar\",\n" +
            "    \"BAZ=quux\"\n" +
            "  ],\n" +
            "  \"Cmd\": [\n" +
            "    \"date\"\n" +
            "  ],\n" +
            "  \"Entrypoint\": \"\",\n" +
            "  \"Image\": \"ubuntu\",\n" +
            "  \"Labels\": {\n" +
            "    \"com.example.vendor\": \"Acme\",\n" +
            "    \"com.example.license\": \"GPL\",\n" +
            "    \"com.example.version\": \"1.0\"\n" +
            "  },\n" +
            "  \"Volumes\": {\n" +
            "  },\n" +
            "  \"WorkingDir\": \"\",\n" +
            "  \"NetworkDisabled\": false,\n" +
            "  \"ExposedPorts\": {\n" +
            "    \"22/tcp\": {}\n" +
            "  },\n" +
            "  \"StopSignal\": \"SIGTERM\",\n" +
            "  \"StopTimeout\": 10,\n" +
            "  \"HostConfig\": {\n" +
            "    \"Binds\": [\n" +
            "      \"/tmp:/tmp\"\n" +
            "    ],\n" +
            "    \"Links\": [\n" +
            "    ],\n" +
            "    \"MemoryReservation\": 0,\n" +
            "    \"BlkioWeightDevice\": [\n" +
            "      {}\n" +
            "    ],\n" +
            "    \"BlkioDeviceReadBps\": [\n" +
            "      {}\n" +
            "    ],\n" +
            "    \"BlkioDeviceReadIOps\": [\n" +
            "      {}\n" +
            "    ],\n" +
            "    \"BlkioDeviceWriteBps\": [\n" +
            "      {}\n" +
            "    ],\n" +
            "    \"BlkioDeviceWriteIOps\": [\n" +
            "      {}\n" +
            "    ],\n" +
            "    \"MemorySwappiness\": 60,\n" +
            "    \"OomKillDisable\": false,\n" +
            "    \"OomScoreAdj\": 500,\n" +
            "    \"PidMode\": \"\",\n" +
            "    \"PidsLimit\": -1,\n" +
            "    \"PortBindings\": {\n" +
            "      \"22/tcp\": [\n" +
            "        {\n" +
            "          \"HostPort\": \"11022\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    \"PublishAllPorts\": false,\n" +
            "    \"Privileged\": false,\n" +
            "    \"ReadonlyRootfs\": false,\n" +
            "    \"Dns\": [\n" +
            "      \"8.8.8.8\"\n" +
            "    ],\n" +
            "    \"DnsOptions\": [\n" +
            "      \"\"\n" +
            "    ],\n" +
            "    \"DnsSearch\": [\n" +
            "      \"\"\n" +
            "    ],\n" +
            "    \"CapAdd\": [\n" +
            "      \"NET_ADMIN\"\n" +
            "    ],\n" +
            "    \"CapDrop\": [\n" +
            "      \"MKNOD\"\n" +
            "    ],\n" +
            "    \"GroupAdd\": [\n" +
            "      \"newgroup\"\n" +
            "    ],\n" +
            "    \"RestartPolicy\": {\n" +
            "      \"Name\": \"\",\n" +
            "      \"MaximumRetryCount\": 0\n" +
            "    },\n" +
            "    \"AutoRemove\": true,\n" +
            "    \"NetworkMode\": \"bridge\",\n" +
            "    \"Devices\": [],\n" +
            "    \"Ulimits\": [\n" +
            "      {}\n" +
            "    ],\n" +
            "    \"LogConfig\": {\n" +
            "      \"Type\": \"json-file\",\n" +
            "      \"Config\": {}\n" +
            "    },\n" +
            "    \"SecurityOpt\": [],\n" +
            "    \"StorageOpt\": {},\n" +
            "    \"CgroupParent\": \"\",\n" +
            "    \"VolumeDriver\": \"\",\n" +
            "    \"ShmSize\": 0\n" +
            "  },\n" +
            "  \"NetworkingConfig\": {\n" +
            "    \"EndpointsConfig\": {\n" +
            "      \"isolated_nw\": {\n" +
            "        \"IPAMConfig\": {\n" +
            "          \"IPv4Address\": \"172.20.30.33\"" +
            "        },\n" +
            "        \"Links\": [\n" +
            "        ],\n" +
            "        \"Aliases\": [\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    private static String id = "";

    @Test
    public void shouldCreateContainer() throws ExecutionException, InterruptedException {
        final WebTarget target = target("containers").path("create").queryParam("name", "mysample");
        Response response = postResponse(target, body);

        // ubuntu:latest needed
        assertEquals(CREATED.getStatusCode(), response.getStatus());
        final JsonObject responseContent = response.readEntity(JsonObject.class);

        assertThat(responseContent.containsKey("Id"), is(true));

        id = responseContent.getString("Id");
        response.close();
    }

    @Test
    public void shouldDeleteContainer() throws ExecutionException, InterruptedException {
        final WebTarget target = target("containers").path(id).queryParam("v", true).queryParam("force", true);
        Response response = deleteAsycResponse(target);
        // ubuntu:latest neeeded
        assertEquals(OK.getStatusCode(), response.getStatus());
        response.close();
    }

}
