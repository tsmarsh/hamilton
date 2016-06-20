package com.tailoredshapes.jms;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpVersion;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.nio.NHttpClientConnection;
import org.apache.http.nio.NHttpConnection;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.function.Predicate;


public class HTTPForwarder implements MessageListener{

    private Predicate<TextMessage> predicate;
    private NHttpClientConnection connection;
    private String uri;

    @Inject
    public HTTPForwarder(Predicate<TextMessage> predicate,
                         NHttpClientConnection connection,
                         @Named("uri") String uri) {
        this.predicate = predicate;
        this.connection = connection;
        this.uri = uri;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;

        if(predicate.test(textMessage)){
            BasicHttpRequest request = new BasicHttpRequest("GET", uri, HttpVersion.HTTP_1_1);
            try {
                connection.submitRequest(request);
            } catch (IOException | HttpException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
