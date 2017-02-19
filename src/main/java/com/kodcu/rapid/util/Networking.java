package com.kodcu.rapid.util;

import org.eclipse.jetty.http.MimeTypes;

import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

/**
 * Created by hakan on 07/01/2017.
 */
public final class Networking {

    private Networking() {
    }

    public static Response getResponse(WebTarget target) {
        return target.request(String.valueOf(MimeTypes.Type.APPLICATION_JSON_UTF_8)).get();
    }

    public static Response postResponse(WebTarget target, JsonObject content) {
        return target.request(String.valueOf(MimeTypes.Type.APPLICATION_JSON_UTF_8)).method("POST", Entity.entity(content, MediaType.APPLICATION_JSON));
    }

    public static Response postResponse(WebTarget target, String content) {
        return target.request(String.valueOf(MimeTypes.Type.APPLICATION_JSON_UTF_8)).method("POST", Entity.entity(content, MediaType.APPLICATION_JSON));
    }

    public static Response postResponse(WebTarget target) {
        return target.request(String.valueOf(MimeTypes.Type.APPLICATION_JSON_UTF_8)).method("POST");
    }

    public static Response deleteResponse(WebTarget target){
        return target.request().method("DELETE");
    }

    public static Response getAsycResponse(WebTarget target) throws ExecutionException, InterruptedException {
        return target.request(APPLICATION_JSON_TYPE).async().method("GET").get();
    }

    public static Response postAsycResponse(WebTarget target, String content) throws ExecutionException, InterruptedException {
        return target.request(APPLICATION_JSON_TYPE).async().method("POST", Entity.entity(content, MediaType.APPLICATION_JSON)).get();
    }

    public static Response deleteAsycResponse(WebTarget target) throws ExecutionException, InterruptedException {
        return target.request().async().method("DELETE").get();
    }
}
