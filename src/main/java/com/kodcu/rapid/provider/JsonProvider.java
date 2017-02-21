package com.kodcu.rapid.provider;

import com.kodcu.rapid.pojo.ResponseFrame;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by Hakan on 2/10/2016.
 */
@Provider
@Produces(value = "application/json")
@Consumes(value = "application/json")
public class JsonProvider implements MessageBodyWriter, MessageBodyReader {

    // @Produces 3
    @Override
    public long getSize(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(Object o, Class type, Type genericType, Annotation[] annotations,
                        MediaType mediaType, MultivaluedMap httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {

        JsonStructure jObj;
        if (o instanceof ResponseFrame) {
            ResponseFrame f = (ResponseFrame) o;
            JsonObjectBuilder b = Json.createObjectBuilder();

            if (!f.getId().isEmpty())
                b.add("id", f.getId());

            b.add("message", f.getMessage());
            jObj = b.build();
        } else
            jObj = (JsonStructure) o;


        entityStream.write(jObj.toString().getBytes());
    }

    // @Consumes - MessageBodyReader
    @Override
    public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public Object readFrom(Class type, Type genericType, Annotation[] annotations,
                           MediaType mediaType, MultivaluedMap httpHeaders,
                           InputStream entityStream) throws IOException, WebApplicationException {
        // from HTML to methods having @Consumes
        JsonReader jsonReader = Json.createReader(entityStream);
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        entityStream.close();
        return object;
    }
}
