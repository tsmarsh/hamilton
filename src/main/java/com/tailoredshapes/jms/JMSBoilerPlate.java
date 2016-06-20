package com.tailoredshapes.jms;

import javax.inject.Inject;
import javax.jms.*;
import java.util.Optional;


public class JMSBoilerPlate {
    private final Connection connection;
    private final Topic topic;

    @Inject
    public JMSBoilerPlate(Connection connection, Topic topic, MessageListener listener) {

        this.connection = connection;
        this.topic = topic;
        Optional<Session> session = Optional.empty();
        try {
            session = Optional.of(connection.createSession(false, Session.AUTO_ACKNOWLEDGE));
            if(session.isPresent()){
                session.get().setMessageListener(listener);
            }
        } catch (JMSException e) {
            if(session.isPresent()){
                try {
                    session.get().close();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        }
    }
}
