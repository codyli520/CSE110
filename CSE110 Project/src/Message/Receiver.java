package Message;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {
	  // ConnectionFactory 
    ConnectionFactory connectionFactory;
    // Connection 
    Connection connection = null;
    // Session
    Session session;
    // Destination 
    Destination destination;
    // Consumer
    MessageConsumer consumer;
    
    String queue = null;
    public SenderGUI sg;
    
    public Receiver(){
    connectionFactory = new ActiveMQConnectionFactory(
            ActiveMQConnection.DEFAULT_USER,
            ActiveMQConnection.DEFAULT_PASSWORD,
            "tcp://localhost:61616");
    queue = "FirstQueue";
    sg = new SenderGUI("localhost", 8161);
    }
    
    public Receiver(String user){
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        queue = user;
        sg = new SenderGUI("localhost", 8161);
     }
    
    public Receiver(String user, SenderGUI gui){
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        queue = user;
        sg = gui;
     }
    
    
    public void receiverPrep() throws JMSException{
    	connection = connectionFactory.createConnection();
        // 
        connection.start();
        // 
        session = connection.createSession(Boolean.FALSE,
                Session.AUTO_ACKNOWLEDGE);
        // 
        //take user input, decide which queue it should be
        destination = session.createQueue(queue);
        consumer =session.createConsumer(destination);	
    	
    }
    public String receiveMessage() throws JMSException{
    	TextMessage message = (TextMessage) consumer.receive(1000);
        if (null != message) {
            System.out.println("Receive from : " + queue + " Message: " + message.getText());
            sg.showText("Receive from : " + queue + " Message: " + message.getText());
            return message.getText();
        } else {
            return null;
        }
    	
    }
    public static void main(String[] args) {
    	Receiver rev=new Receiver("456");
        try {
            rev.receiverPrep();
                while (true) {
                if(rev.receiveMessage()==null){
                	break;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rev.connection)
                    rev.connection.close();
            } catch (Throwable ignore) {
            }
        }
    }
    
    public void fullServiceReceive(){
        try {
            this.receiverPrep();
                while (true) {
                if(this.receiveMessage()==null){
                	break;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != this.connection)
                    this.connection.close();
            } catch (Throwable ignore) {
            }
        }
    }
public Connection getConnection(){
    	return connection;
        }
        
        public Session getSession(){
    	return session;
        }
        
        public Destination getDestinations(){
    	return destination;
        }

    public MessageConsumer getConsumer(){
	return consumer;
    }

    
}