package com.kodcu.rapid.pojo;

import java.io.Serializable;

/**
 * Created by hakan on 20/02/2017.
 */
public class ResponseFrame implements Serializable {

    public ResponseFrame() {
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
