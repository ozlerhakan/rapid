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
                "    \"Dns\": [\n" +
                "      \"8.8.8.8\"\n" +
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
                {name: 'List networks', example: "GET networks?filters={\"name\":{\"host\":true}}"},
                {name: 'Inspect a network', example: "GET networks/id-name"},
                {name: 'Remove a network', example: "DELETE networks/id-name"},
                {
                    name: 'Create a network', example: "POST networks/create\n{\n" +
                "  \"Name\": \"isolated_nw\",\n" +
                "  \"CheckDuplicate\": false,\n" +
                "  \"Driver\": \"bridge\",\n" +
                "  \"EnableIPv6\": true,\n" +
                "  \"IPAM\": {\n" +
                "    \"Driver\": \"default\",\n" +
                "    \"Config\": [\n" +
                "      {\n" +
                "        \"Subnet\": \"172.20.0.0/16\",\n" +
                "        \"IPRange\": \"172.20.10.0/24\",\n" +
                "        \"Gateway\": \"172.20.10.11\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"Subnet\": \"2001:db8:abcd::/64\",\n" +
                "        \"Gateway\": \"2001:db8:abcd::1011\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"Options\": {\n" +
                "      \"foo\": \"bar\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"Internal\": true,\n" +
                "  \"Attachable\": false,\n" +
                "  \"Options\": {\n" +
                "    \"com.docker.network.bridge.default_bridge\": \"true\",\n" +
                "    \"com.docker.network.bridge.enable_icc\": \"true\",\n" +
                "    \"com.docker.network.bridge.enable_ip_masquerade\": \"true\",\n" +
                "    \"com.docker.network.bridge.host_binding_ipv4\": \"0.0.0.0\",\n" +
                "    \"com.docker.network.bridge.name\": \"docker0\",\n" +
                "    \"com.docker.network.driver.mtu\": \"1500\"\n" +
                "  },\n" +
                "  \"Labels\": {\n" +
                "    \"com.example.some-label\": \"some-value\",\n" +
                "    \"com.example.some-other-label\": \"some-other-value\"\n" +
                "  }\n" +
                "}"
                },
                {
                    name: 'Connect a container to a network', example: "POST networks/id/connect\n{\n" +
                "  \"Container\": \"3613f73ba0e4\",\n" +
                "  \"EndpointConfig\": {\n" +
                "    \"IPAMConfig\": {\n" +
                "      \"IPv4Address\": \"172.24.56.89\",\n" +
                "      \"IPv6Address\": \"2001:db8::5689\"\n" +
                "    }\n" +
                "  }\n" +
                "}"
                },
                {
                    name: 'Disconnect a container from a network',
                    example: "POST networks/id/disconnect\n{\n   \"Container\":\"string\"\n,   \"Force\":true\n}"
                },
                {name: 'Delete unused networks', example: "POST networks/prune"}
            ]
        },
        {
            name: 'Volumes',
            children: [
                {name: 'List volumes', example: "GET volumes?filters={\"dangling\":{\"true\":false}}"},
                {
                    name: 'Create a volume', example: "POST volumes/create\n{\n" +
                "  \"Name\": \"tardis\",\n" +
                "  \"Labels\": {\n" +
                "    \"com.example.some-label\": \"some-value\",\n" +
                "    \"com.example.some-other-label\": \"some-other-value\"\n" +
                "  },\n" +
                "  \"Driver\": \"local\"\n" +
                "}"
                },
                {name: 'Inspect a volume', example: "GET volumes/volume-name"},
                {name: 'Remove a volume', example: "DELETE volumes/volume-name?force=true"},
                {name: 'Delete unused volumes', example: "DELETE volume/prune"}
            ]
        },
        {
            name: 'System',
            children: [
                {
                    name: 'Check auth configuration', example: "GET /auth\n{\n" +
                "  \"username\": \"hannibal\",\n" +
                "  \"password\": \"xxxx\",\n" +
                "  \"serveraddress\": \"https://index.docker.io/v1/\"\n" +
                "}"
                },
                {name: 'Get system information', example: "GET info"},
                {name: 'Get version', example: "GET version"},
                {name: 'Ping', example: "GET _ping"},
                {name: 'Get data usage information', example: "GET system/df"}
            ]
        },
        {
            name: 'Secrets',
            children: [
                {name: 'List secrets', example: "GET secrets"},
                {
                    name: 'Create a secret', example: "POST secrets/create\n{\n" +
                "  \"Name\": \"app-key.crt\",\n" +
                "  \"Labels\": {\n" +
                "    \"foo\": \"bar\"\n" +
                "  },\n" +
                "  \"Data\": \"VEhJUyBJUyBOT1QgQSBSRUFMIENFUlRJRklDQVRFCg==\"\n" +
                "}"
                },
                {name: 'Inspect a secret', example: "GET secrets/id"},
                {name: 'Delete a secret', example: "DELETE secrets/id"},
                {
                    name: 'Update a Secret', example: "POST secrets/id/update/n{\n" +
                "  \"Name\": \"string\",\n" +
                "  \"Labels\": {\n" +
                "    \"property1\": \"string\",\n" +
                "    \"property2\": \"string\"\n" +
                "  },\n" +
                "  \"Data\": [\n" +
                "    \"string\"\n" +
                "  ]\n" +
                "}"
                }
            ]
        },
        {
            name: 'Swarm',
            children: [
                {name: 'Inspect swarm', example: "GET swarm"},
                {
                    name: 'Initialize a new swarm', example: "POST swarm/init\n{\n" +
                "  \"ListenAddr\": \"0.0.0.0:2377\",\n" +
                "  \"AdvertiseAddr\": \"192.168.1.1:2377\",\n" +
                "  \"ForceNewCluster\": false,\n" +
                "  \"Spec\": {\n" +
                "    \"Orchestration\": {},\n" +
                "    \"Raft\": {},\n" +
                "    \"Dispatcher\": {},\n" +
                "    \"CAConfig\": {},\n" +
                "    \"EncryptionConfig\": {\n" +
                "      \"AutoLockManagers\": false\n" +
                "    }\n" +
                "  }\n" +
                "}"
                },
                {
                    name: 'Join an existing swarm', example: "POST swarm/join\n{\n" +
                "  \"ListenAddr\": \"0.0.0.0:2377\",\n" +
                "  \"AdvertiseAddr\": \"192.168.1.1:2377\",\n" +
                "  \"RemoteAddrs\": [\n" +
                "    \"node1:2377\"\n" +
                "  ],\n" +
                "  \"JoinToken\": \"SWMTKN-1-3pu6hszjas19xyp7ghgosyx9k8atbfcr8p2is99znpy26u2lkl-7p73s1dx5in4tatdymyhg9hu2\"\n" +
                "}"
                },
                {name: 'Leave a swarm', example: "POST swarm/leave?force=true"},
                {
                    name: 'Update a swarm',
                    example: "POST swarm/update?version=1&rotateWorkerToken=false&rotateManagerToken=false\n{\n" +
                    "  \"Name\": \"default\",\n" +
                    "  \"Orchestration\": {\n" +
                    "    \"TaskHistoryRetentionLimit\": 10\n" +
                    "  },\n" +
                    "  \"Raft\": {\n" +
                    "    \"SnapshotInterval\": 10000,\n" +
                    "    \"LogEntriesForSlowFollowers\": 500,\n" +
                    "    \"HeartbeatTick\": 1,\n" +
                    "    \"ElectionTick\": 3\n" +
                    "  },\n" +
                    "  \"Dispatcher\": {\n" +
                    "    \"HeartbeatPeriod\": 5000000000\n" +
                    "  },\n" +
                    "  \"CAConfig\": {\n" +
                    "    \"NodeCertExpiry\": 7776000000000000\n" +
                    "  },\n" +
                    "  \"JoinTokens\": {\n" +
                    "    \"Worker\": \"SWMTKN-1-3pu6hszjas19xyp7ghgosyx9k8atbfcr8p2is99znpy26u2lkl-1awxwuwd3z9j1z3puu7rcgdbx\",\n" +
                    "    \"Manager\": \"SWMTKN-1-3pu6hszjas19xyp7ghgosyx9k8atbfcr8p2is99znpy26u2lkl-7p73s1dx5in4tatdymyhg9hu2\"\n" +
                    "  },\n" +
                    "  \"EncryptionConfig\": {\n" +
                    "    \"AutoLockManagers\": false\n" +
                    "  }\n" +
                    "}"
                },
                {name: 'Get the unlock key', example: "GET swarm/unlockkey"},
                {
                    name: 'Unlock a locked manager', example: "POST swarm/unlock\n{\n" +
                "  \"UnlockKey\": \"SWMKEY-1-7c37Cc8654o6p38HnroywCi19pllOnGtbdZEgtKxZu8\"\n" +
                "}"
                }
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