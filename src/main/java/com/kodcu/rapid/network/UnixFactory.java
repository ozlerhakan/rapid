package com.kodcu.rapid.network;

import jnr.unixsocket.UnixSocketAddress;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;

/**
 * Created by Hakan on 2/11/2016.
 */
@Immutable
public class UnixFactory implements ConnectionSocketFactory {

    private File socketFile;

    public UnixFactory(final URI socketUri) {
        super();
        final String filename = socketUri.toString()
                .replaceAll("^unix:///", "unix://localhost/")
                .replaceAll("^unix://localhost", "");
        // file name will be the docker socket: /var/run/docker.sock
        this.socketFile = new File(filename);
    }

    public static URI sanitizeUri(final URI uri) {
        if (uri.getScheme().equals("unix")) {
            return URI.create("unix://localhost:80");
        } else {
            return uri;
        }
    }

    @Override
    public Socket createSocket(HttpContext context) throws IOException {
        return new UnixSocket();
    }

    @Override
    public Socket connectSocket(
            int connectTimeout,
            Socket sock,
            HttpHost host,
            InetSocketAddress remoteAddress,
            InetSocketAddress localAddress,
            HttpContext context) throws IOException {
        try {
            sock.connect(new UnixSocketAddress(socketFile), connectTimeout);
        } catch (SocketTimeoutException e) {
            throw new ConnectTimeoutException(e, null, remoteAddress.getAddress());
        }
        return sock;
    }
}
