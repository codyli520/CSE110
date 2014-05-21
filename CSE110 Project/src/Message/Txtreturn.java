package Message;
import java.io.File;
import java.io.FileNotFoundException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;

public class Txtreturn {
	
	ConnectionFactory connectionFactory;
	Connection connection;
	ActiveMQSession session;
	Destination destination;
	MessageProducer producer;
	BlobMessage blobMessage;
	String topic;
	File file;
	
	public Txtreturn(File newFile, String newTopic) throws FileNotFoundException{
		if(newFile.isFile() && newFile.exists()){
			file = newFile;
			topic = newTopic;
		}
		else{
			throw new FileNotFoundException();
		}
	}
	
	public boolean txtReturnSetup() {  
		try{
			connectionFactory = new ActiveMQConnectionFactory(               
					"tcp://localhost:61616?jms.blobTransferPolicy.defaultUploadUrl=http://localhost:8161/fileserver/");         		   
			connection = connectionFactory.createConnection();        
			connection.start();       		 
			session = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);      		 
			destination = session.createQueue(topic);       		     
			producer = session.createProducer(destination);       
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			return true;
		}
		catch(JMSException e){
			return false;
		}
	}
	
	public boolean returnText(){
		if( !txtReturnSetup() ){
			return false;
		}
		try{
			BlobMessage blobMessage = session.createBlobMessage(file);      
			blobMessage.setStringProperty("FILE.NAME", file.getName());       
			blobMessage.setLongProperty("FILE.SIZE", file.length());        
			producer.send(blobMessage);       	      
			producer.close();        
			session.close();       
			connection.close(); 
			return true;
		}
		catch(JMSException e){
			return false;
		}
	}
}


