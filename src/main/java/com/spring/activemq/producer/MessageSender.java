package com.spring.activemq.producer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;

// This class is used to send a text message to the queue
public class MessageSender {

    // URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    // DEFAULT_BROKER_URL: tcp://localhost:61616
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // Queue Name
    // You can create any/many queue names as per requirement
    private static String queueName = "MESSAGE_QUEUE";

    public static void main(String[] args) throws JMSException {
        System.out.println("Producer URL: " + url);

        // Getting JMS connection from JMS server and starting it
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Creating a non  transactional session to send/receive JMS message
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Queue will be created automatically on server
        // Destination represents queue "MESSAGE_QUEUE" on the JMS server
        Destination destination = session.createQueue(queueName);

        // MessageProducer is used for sending message to the queue
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage("Hi, this is first message");

        // Sending the message;
        producer.send(message);

        System.out.println("Message: " + message.getText() + ", sent successfully to the queue!");
        connection.close();
    }
}
