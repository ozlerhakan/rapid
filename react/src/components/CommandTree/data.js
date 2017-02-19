/**
 * Created by hakan on 16/02/2017.
 */

export default {
    name: 'Queries',
    toggled: true,
    children: [
        {
            name: 'Images',
            toggled: true,
            children: [
                {name: 'List images', example: "GET images/json?all=true&filters={\"reference\":{\"ubuntu\":true}}"},
                {name: 'Create an image', example: "POST images/create?fromImage=alpine&tag=3.2"},
                {name: 'Inspect an image', example: "GET images/ubuntu:latest/json"},
                {name: 'Get the history of an image', example: "GET images/ubuntu:latest/history"},
                {name: 'Tag an image', example: "POST images/ubuntu:latest/tag?repo=ubuntu&tag=mynewtag"},
                {name: 'Remove an image', example: "DELETE images/busybox:1.26?force=true&noprune=false"},
                {
                    name: 'Search images',
                    example: "GET images/search?term=java&filters={\"is-automated\":{\"true\":true}}"
                },
                {name: 'Delete unused images', example: "DELETE images/prune?filters={\"dangling\":{\"true\":true}}"},
                {
                    name: 'Create a new image from a container',
                    example: "POST images/commit?container=mongo&comment=firstcommit&author=goksel\n{\n" +
                    " " +
                    "  \"Hostname\": \"string\",\n" +
                    "  \"Domainname\": \"string\",\n" +
                    "  \"User\": \"string\",\n" +
                    "  \"AttachStdin\": false,\n" +
                    "  \"AttachStdout\": true,\n" +
                    "  \"AttachStderr\": true,\n" +
                    "  \"ExposedPorts\": {\n" +
                    "    \"property1\": {},\n" +
                    "    \"property2\": {}\n" +
                    "  },\n" +
                    "  \"Cmd\": [\n" +
                    "    \"date\"\n" +
                    "  ],\n" +
                    "  \"Tty\": false,\n" +
                    "  \"OpenStdin\": false,\n" +
                    "  \"StdinOnce\": false,\n" +
                    "  \"Image\": \"ubuntu\",\n" +
                    "  \"NetworkDisabled\": true,\n" +
                    "  \"MacAddress\": \"string\",\n" +
                    "  \"Labels\": {\n" +
                    "    \"property1\": \"string\",\n" +
                    "  },\n" +
                    "  \"StopSignal\": \"SIGTERM\",\n" +
                    "  \"StopTimeout\": 10,\n" +
                    "}"
                },
            ]
        },
        {
            name: 'Containers',
            children: [
                {name: 'List containers', example: "GET containers/json?size=true"},
                {
                    name: 'Create a container', example: "POST containers/create?name=mycontainer\n{\n" +
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
                "}"
                },
                {name: 'Inspect a container', example: "GET containers/container-id/json"},
                {
                    name: 'List processes running inside a container',
                    example: "GET containers/container-id/top?ps_args=-ef"
                },
                {name: 'Get container logs', example: "GET containers/container-id/logs?stdout=true&tail=all"},
                {name: 'Get changes on a container’s filesystem', example: "GET containers/container-id/changes"},
                {name: 'Start a container', example: "POST containers/container-id/start"},
                {name: 'Stop a container', example: "POST containers/container-id/stop"},
                {name: 'Kill a container', example: "POST containers/container-id/kill?signal=KILL"},
                {name: 'Restart a container', example: "POST containers/container-id/restart?t=5"},
                {
                    name: 'Update a container', example: "POST containers/container-id/update\n{\n" +
                "  \"BlkioWeight\": 300,\n" +
                "  \"CpuShares\": 512,\n" +
                "  \"CpuPeriod\": 100000,\n" +
                "  \"CpuQuota\": 50000,\n" +
                "  \"CpuRealtimePeriod\": 1000000,\n" +
                "  \"CpuRealtimeRuntime\": 10000,\n" +
                "  \"CpusetCpus\": \"0,1\",\n" +
                "  \"CpusetMems\": \"0\",\n" +
                "  \"Memory\": 314572800,\n" +
                "  \"MemorySwap\": 514288000,\n" +
                "  \"MemoryReservation\": 209715200,\n" +
                "  \"KernelMemory\": 52428800,\n" +
                "  \"RestartPolicy\": {\n" +
                "    \"MaximumRetryCount\": 4,\n" +
                "    \"Name\": \"on-failure\"\n" +
                "  }\n" +
                "}\n"
                },
                {name: 'Rename a container', example: "POST containers/container-id/rename?name=newName"},
                {name: 'Pause a container', example: "POST containers/container-id/pause"},
                {name: 'Unpause a container', example: "POST containers/container-id/unpause"},
                {name: 'Remove a container', example: "DELETE containers/container-id?v=true&force=true"},
                {name: 'Delete stopped containers', example: "DELETE containers/prune"}
            ]
        },
        {
            name: 'Networks',
            children: [
                {name: 'List networks'},
                {name: 'Inspect a network'},
                {name: 'Remove a network'},
                {name: 'Create a network'},
                {name: 'Connect a container to a network'},
                {name: 'Disconnect a container from a network'},
                {name: 'Delete unused networks'}
            ]
        },
        {
            name: 'Volumes',
            children: [
                {name: 'List volumes'},
                {name: 'Create a volume'},
                {name: 'Inspect a volume'},
                {name: 'Remove a volume'},
                {name: 'Delete unused volumes'}
            ]
        },
        {
            name: 'System',
            children: [
                {name: 'Check auth configuration'},
                {name: 'Get system information'},
                {name: 'Get version'},
                {name: 'Ping'},
                {name: 'Get data usage information'}
            ]
        },
        {
            name: 'Secrets',
            children: [
                {name: 'List secrets'},
                {name: 'Create a secret'},
                {name: 'Inspect a secret'},
                {name: 'Delete a secret'},
                {name: 'Update a Secret'}
            ]
        },
        {
            name: 'Swarm',
            children: [
                {name: 'Inspect swarm'},
                {name: 'Initialize a new swarm'},
                {name: 'Join an existing swarm'},
                {name: 'Leave a swarm'},
                {name: 'Update a swarm'},
                {name: 'Get the unlock key'},
                {name: 'Unlock a locked manager'}
            ]
        },
        {
            name: 'Nodes',
            children: [
                {name: 'List nodes'},
                {name: 'Inspect a node'},
                {name: 'Delete a node'},
                {name: 'Update a node'}
            ]
        },
        {
            name: 'Services',
            children: [
                {name: 'List services'},
                {name: 'Create a service'},
                {name: 'Inspect a service'},
                {name: 'Delete a service'},
                {name: 'Update a service'},
                {name: 'Get service logs'}
            ]
        },
        {
            name: 'Tasks',
            children: [
                {name: 'List tasks'},
                {name: 'Inspect a task'}
            ]
        },
        {
            name: 'Plugins',
            children: [
                {name: 'List plugins'},
                {name: 'Get plugin privileges'},
                {name: 'Install a plugin'},
                {name: 'Inspect a plugin'},
                {name: 'Remove a plugin'},
                {name: 'Enable a plugin'},
                {name: 'Disable a plugin'},
                {name: 'Upgrade a plugin'},
                {name: 'Push a plugin'}
            ]
        }
    ]
};