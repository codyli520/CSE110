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

	// ConnectionFactory 
    ConnectionFactory connectionFactory;
    // Connection 
    Connection connection = null;
    // Session arrayList£º
    Session[] sessions;
    // Destination array £º
    Destination[] destinations;
    // MessageProducer array£º
    MessageProducer[] producers;
    // Sender ID (SID)
    int sid;
    // String[] queues
    String[] queues;
    // int numOfQueue, number of established queue;
    int numOfQueue;
    // Max number of queues
    static final int maxQueue = 1000;
    
    public static void main(String args[]) throws IOException{
    	if( args.length >= 2 && args[0] != null && args[1] != null){
    		Sender sender = new Sender(Integer.parseInt(args[0]));
    		sender.fullSendService(args[1]);
    	}
    	else{
    		Sender sender = new Sender();
    		sender.fullSendService("FirstQueue");
    	}
    }
    
    public Sender()
    {
        // TextMessage message;
        // ConnectionFactory
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        sid = 0000;
        queues = new String[maxQueue];
        destinations = new Destination[maxQueue];
        sessions = new Session[maxQueue]; 
        producers = new MessageProducer[maxQueue];
        numOfQueue = 0;
    }
    
    public Sender(int new_sid){
    	// TextMessage message;
        // ConnectionFactory
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        sid = new_sid;
        queues = new String[maxQueue];
        destinations = new Destination[maxQueue];
        sessions = new Session[maxQueue]; 
        producers = new MessageProducer[maxQueue];
        numOfQueue = 0;
    }
    
    public void sendPrep(String queue) throws IOException{
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            if( numOfQueue < maxQueue ){
            	destinations[numOfQueue] = session.createQueue(queue);
            	//System.out.println(destinations[numOfQueue] == null);
            	//System.out.println(session.createProducer(destinations[numOfQueue])==null);
            	producers[numOfQueue] = session.createProducer(destinations[numOfQueue]);
            	producers[numOfQueue].setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            	sendMessage(session, producers[numOfQueue]);
            	numOfQueue++;
            	session.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String grabMessage() throws IOException{
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    return br.readLine();
    }

    public void sendMessage(Session session, MessageProducer producer){   
    	try{
    		String inputstring = grabMessage();
    		TextMessage message = session
    	            .createTextMessage(inputstring);
    	           
    	        System.out.println("Send£º" + inputstring);
    	        producer.send(message);
    	}
    	catch(Exception e){
    		System.err.println("Message not send successfully.");
    	}
    	
    }
    
    public void fullSendService(String queue) throws IOException{
    	sendPrep(queue);
    	try {
    		if (null != connection)
    			connection.close();
    		} catch (Throwable ignore) {
    		}
    }
}