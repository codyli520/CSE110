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
    // Session arrayList：
    Session session;
    // Destination array ：
    Destination[] destinations;
    // MessageProducer array：
    MessageProducer[] producers;
    // Sender ID (SID)
    int sid;
    // String[] queues
    String[] queues;
    // int numOfQueue, number of established queue;
    int numOfQueue;
    // Max number of queues
    static final int maxQueue = 1000;
    int mode; //0, default mode, take system in, other, take inputString
    
    public static void main(String args[]) throws IOException{
    	if( args.length >= 2 && args[0] != null && args[1] != null){
    		Sender sender = new Sender(Integer.parseInt(args[0]), 0);
    		sender.fullSendService(args[1], null);
    	}
    	else{
    		Sender sender = new Sender();
    		sender.fullSendService("FirstQueue", null);
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
        //sessions = new Session[maxQueue]; 
        producers = new MessageProducer[maxQueue];
        numOfQueue = 0;
        mode = 0;
    }
    
    public Sender(int new_sid, int new_mode){
    	// TextMessage message;
        // ConnectionFactory
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        sid = new_sid;
        queues = new String[maxQueue];
        destinations = new Destination[maxQueue];
        //sessions = new Session[maxQueue]; 
        producers = new MessageProducer[maxQueue];
        numOfQueue = 0;
        mode = new_mode;
    }
    
    public void sendPrep(String queue) throws IOException{
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            if( numOfQueue < maxQueue ){
            	destinations[numOfQueue] = session.createQueue(queue);
            	//System.out.println(destinations[numOfQueue] == null);
            	//System.out.println(session.createProducer(destinations[numOfQueue])==null);
            	producers[numOfQueue] = session.createProducer(destinations[numOfQueue]);
            	producers[numOfQueue].setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public String grabMessage(String inputString) throws IOException{
    	if( mode == 0 ){
    		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    		return br.readLine();
    	}
    	else{
    		return inputString;
    	}
    }

    public void sendMessage( MessageProducer producer, String inputString){   
    	try{
    		String inputstring = grabMessage(inputString);
            
    		TextMessage message = session
    	            .createTextMessage(inputstring);
	        System.out.println("try to send：" + inputstring);
    		
    	        producer.send(message);
    	}
    	catch(Exception e){
    		System.err.println(e.toString() + "Message not send successfully.");
    	}
    	
    }
    
    public void fullSendService(String queue, String inputString) throws IOException{
    	int currentNumOfQueue = numOfQueue;
    	try{
    		
    		sendPrep(queue);
    		sendMessage(producers[numOfQueue], inputString);
    		numOfQueue++;
    		session.commit();
    	}catch (Exception e) {
    		numOfQueue = currentNumOfQueue;
    		e.printStackTrace();
    	}
    	try {
    		if (null != connection)
    			connection.close();
    		} catch (Throwable ignore) {
    		}
    }
    
    public ConnectionFactory getConnectionFactory(){
    	return connectionFactory;
        }
        
        public Connection getConnection(){
    	return connection;
        }
        
        public Session getSession(){
    	return session;
        }
        
        public Destination[] getDestinations(){
    	return destinations;
        }
        
        public MessageProducer[] getMessageProducer(){
    	return producers;
        }
        
        public int getSid(){
    	return sid;
        }
        
        public String[] getQueue(){
    	return queues;
        }
        
        public int getNumOfQueue(){
    	return numOfQueue;
        }
        
    

}
