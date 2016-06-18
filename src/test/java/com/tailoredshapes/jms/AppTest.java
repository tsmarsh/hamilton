package com.tailoredshapes.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.Destination;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jms.*;

import static org.junit.Assert.*;

/**
 * Created by tsmar on 6/16/2016.
 */
public class AppTest {

    private ActiveMQConnectionFactory connectionFactory;
    private BrokerService brokerService;
    private Connection connection;
    private Session session;

    @Before
    public void setUp() throws Exception {
        brokerService = new BrokerService();
        brokerService.setPersistent(false);
        brokerService.start();

        connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    }

    @After
    public void tearDown() throws Exception {
        brokerService.stop();

    }


    @Test
    public void shouldSoakUpTheFireHose() throws Exception {
        Topic topic = session.createTopic("TEST.FOO");

        MessageConsumer consumer = session.createConsumer(topic);

        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        TextMessage textMessage = session.createTextMessage("Hello, World!");
        producer.send(textMessage);

        TextMessage message = (TextMessage) consumer.receive(1000);

        assertEquals("Hello, World!", message.getText());
    }
}