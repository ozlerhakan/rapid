package com.kodcu.rapid.app;

import com.kodcu.rapid.path.Container;
import com.kodcu.rapid.path.Image;
import com.kodcu.rapid.path.Network;
import com.kodcu.rapid.path.Node;
import com.kodcu.rapid.path.Plugin;
import com.kodcu.rapid.path.Service;
import com.kodcu.rapid.path.Swarm;
import com.kodcu.rapid.path.System;
import com.kodcu.rapid.path.Task;
import com.kodcu.rapid.path.Volume;
import com.kodcu.rapid.provider.JsonProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * Created by Hakan on 2/10/2016.
 */
@ApplicationPath("docker")
public class AppConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(Container.class);
        resources.add(Image.class);
        resources.add(Volume.class);
        resources.add(Network.class);
        resources.add(System.class);
        resources.add(Swarm.class);
        resources.add(Node.class);
        resources.add(Service.class);
        resources.add(Task.class);
        resources.add(Plugin.class);
        resources.add(JsonProvider.class);
        return resources;
    }


}