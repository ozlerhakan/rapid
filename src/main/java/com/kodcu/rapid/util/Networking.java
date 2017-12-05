package com.kodcu.rapid.util;

import javax.json.JsonStructure;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

/**
 * Created by hakan on 07/01/2017.
 */
public final class Networking {

    private Networking() {
    }

    public static Response getResponse(WebTarget target) {
        return target.request(APPLICATION_JSON).get();
    }

    public static Response postResponse(WebTarget target, JsonStructure content) {
        return target.request(APPLICATION_JSON).post(Entity.entity(content, APPLICATION_JSON));
    }

    public static Response postResponse(WebTarget target, String content) {
        return target.request(APPLICATION_JSON).post(Entity.entity(content, APPLICATION_JSON));
    }

    public static Response postResponse(WebTarget target) {
        return target.request(APPLICATION_JSON).method("POST");
    }

    public static Response deleteResponse(WebTarget target){
        return target.request().delete();
    }

    public static Response getAsycResponse(WebTarget target) throws ExecutionException, InterruptedException {
        return target.request(APPLICATION_JSON_TYPE).async().get().get();
    }

    public static Response postAsycResponse(WebTarget target, String content) throws ExecutionException, InterruptedException {
        return target.request(APPLICATION_JSON_TYPE).async().post(Entity.entity(content, APPLICATION_JSON)).get();
    }

    public static Response postAsycResponse(WebTarget target) throws ExecutionException, InterruptedException {
        return target.request(APPLICATION_JSON_TYPE).async().method("POST").get();
    }

    public static Response deleteAsycResponse(WebTarget target) throws ExecutionException, InterruptedException {
        return target.request().async().delete().get();
    }
}
