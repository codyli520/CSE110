package Message;

import java.io.File;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.swing.JFileChooser;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;


public class TextPublisher {  
	
	JFileChooser fileChooser;
	File file;
	ConnectionFactory connectionFactory;
	Connection connection;
	ActiveMQSession session;
	Destination destination;
	MessageProducer producer;
	BlobMessage blobMessage;
	String topic;
	String filepath;
	
	public TextPublisher(){
		
	}
	
	public TextPublisher(String newTopic, String filepath){ 
	    topic = newTopic;
	    this.filepath = filepath;		
	}

	public boolean chooseFile(){
//		fileChooser.setDialogTitle("Choose the text you want to post");       
//		if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {         
//			return false;         
//		}    
		file = new File(filepath); 
		return true;
	}

	public boolean initPublisher(){
		try{
			connectionFactory = new ActiveMQConnectionFactory(
					"tcp://localhost:61616?jms.blobTransferPolicy.defaultUploadUrl=http://localhost:8161/fileserver/");         
			connection = connectionFactory.createConnection();        
			connection.start();
			session = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(topic);
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		}
		catch(JMSException e){
			return false;
		}
		return true;
	}
	
	public boolean publishMessage(){
		if( chooseFile() && initPublisher() ){
			try{
				blobMessage = session.createBlobMessage(file);
				blobMessage.setStringProperty("FILE.NAME", file.getName());
				blobMessage.setLongProperty("FILE.SIZE", file.length());
				producer.send(blobMessage);       
				System.out.println("Text posted!" + file.getName());
				producer.close();        
				session.close();       
				connection.close(); 
				return true;
			}
			catch(JMSException e){
				return false;
			}
		}
		else{
			return false;
		}
	}
}



