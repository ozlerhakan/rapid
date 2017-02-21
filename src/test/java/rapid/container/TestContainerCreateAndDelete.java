package rapid.container;

import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.json.JsonObject;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

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
            "  \"MacAddress\": \"12:34:56:78:9a:bc\",\n" +
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
            "    \"Memory\": 0,\n" +
            "    \"MemorySwap\": 0,\n" +
            "    \"MemoryReservation\": 0,\n" +
            "    \"KernelMemory\": 0,\n" +
            "    \"CpuPercent\": 80,\n" +
            "    \"CpuShares\": 512,\n" +
            "    \"CpuPeriod\": 100000,\n" +
            "    \"CpuRealtimePeriod\": 1000000,\n" +
            "    \"CpuRealtimeRuntime\": 10000,\n" +
            "    \"CpuQuota\": 50000,\n" +
            "    \"CpusetCpus\": \"0\",\n" +
            "    \"CpusetMems\": \"0\",\n" +
            "    \"MaximumIOps\": 0,\n" +
            "    \"MaximumIOBps\": 0,\n" +
            "    \"BlkioWeight\": 300,\n" +
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
            "          \"IPv4Address\": \"172.20.30.33\",\n" +
            "          \"IPv6Address\": \"2001:db8:abcd::3033\",\n" +
            "          \"LinkLocalIPs\": [\n" +
            "          ]\n" +
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
        final WebTarget target = target("containers").path("create").queryParam("name", "haci");
        Response response = postAsycResponse(target, body);

        // ubuntu:latest neeeded
        assertEquals(200, response.getStatus());
        final JsonObject responseContent = response.readEntity(JsonObject.class);
        assertThat(responseContent.containsKey("Id"), is(true));

        id = responseContent.getString("Id");
        response.close();
    }

    @Test
    public void shouldDeleteContainer() throws ExecutionException, InterruptedException {
        final WebTarget target = target("containers").path(id).queryParam("v", true).queryParam("force", true);
        Response response = deleteAsycResponse(target);
        System.out.println(response);
        // ubuntu:latest neeeded
        assertEquals(200, response.getStatus());
        final JsonObject responseContent = response.readEntity(JsonObject.class);
        response.close();
    }

}
