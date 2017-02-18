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
                {name: 'List images', example: ""},
                {name: 'Create an image'},
                {name: 'Inspect an image'},
                {name: 'Get the history of an image'},
                {name: 'Tag an image'},
                {name: 'Remove an image'},
                {name: 'Search images'},
                {name: 'Delete unused images'},
                {name: 'Create a new image from a container'},
                {name: 'Delete unused images'}
            ]
        },
        {
            name: 'Containers',
            children: [
                {name: 'List containers'},
                {name: 'Create a container'},
                {name: 'Inspect a container'},
                {name: 'List processes running inside a container'},
                {name: 'Get container logs'},
                {name: 'Get changes on a container’s filesystem'},
                {name: 'Start a container'},
                {name: 'Stop a container'},
                {name: 'Kill a container'},
                {name: 'Update a container'},
                {name: 'Rename a container'},
                {name: 'Pause a container'},
                {name: 'Unpause a container'},
                {name: 'Remove a container'},
                {name: 'Delete stopped containers'}
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