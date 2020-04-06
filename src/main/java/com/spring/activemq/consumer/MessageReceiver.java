package com.spring.activemq.consumer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// This class is used to receive the text message to the queue
public class MessageReceiver {

    // URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    // DEFAULT_BROKER_URL: tcp://localhost:61616
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // Queue Name that we will be receiving messages from
    private static String queueName = "MESSAGE_QUEUE";

    public static void main(String[] args) throws JMSException {
        System.out.println("Consumer URL: " + url);

        // Getting JMS connection from JMS server and starting it
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Creating a non  transactional session to send/receive JMS message
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Queue will be created automatically on server
        // Destination represents queue "MESSAGE_QUEUE" on the JMS server
        Destination destination = session.createQueue(queueName);

        // MessageConsumer is used for receiving message from the queue
        MessageConsumer consumer = session.createConsumer(destination);

        // Receiving the message
        Message message = consumer.receive();

        if(message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Received message: " + textMessage.getText());
        }

        connection.close();
    }
}
