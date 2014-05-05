package Message;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {


    public static void main(String[] args) throws IOException
    {
        // ConnectionFactory 
        ConnectionFactory connectionFactory;
        // Connection 
        Connection connection = null;
        // Session£º
        Session session;
        // Destination £º
        Destination destination;
        // MessageProducer£º
        MessageProducer producer;
        // TextMessage message;
        // ConnectionFactory
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        try {
            // 
            connection = connectionFactory.createConnection();
            // 
            connection.start();
            // 
            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            // 
            destination = session.createQueue("FirstQueue");
            // 
            producer = session.createProducer(destination);
            // 
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // 
            sendMessage(session, producer);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection)
                    connection.close();
            } catch (Throwable ignore) {
            }
        }
    }

    public static void sendMessage(Session session, MessageProducer producer)
            throws Exception {
    	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	    String inputstring=br.readLine();
            TextMessage message = session
                    .createTextMessage(inputstring);
           
            System.out.println("Send£º" + inputstring);
            producer.send(message);
        }
    }
