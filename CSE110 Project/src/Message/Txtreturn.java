package Message;
import java.io.File;

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
	public static void txtreturn(File file) throws JMSException {             		      
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(               
				"tcp://localhost:61616?jms.blobTransferPolicy.defaultUploadUrl=http://localhost:8161/fileserver/");         		   
		Connection connection = connectionFactory.createConnection();        
		connection.start();       		 
		ActiveMQSession session = (ActiveMQSession) connection.createSession(                
		false, Session.AUTO_ACKNOWLEDGE);      		 
		Destination destination = session.createTopic("txts.txt");       		     
		MessageProducer producer = session.createProducer(destination);       
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		BlobMessage blobMessage = session.createBlobMessage(file);      
		blobMessage.setStringProperty("FILE.NAME", file.getName());       
		blobMessage.setLongProperty("FILE.SIZE", file.length());        
		producer.send(blobMessage);       	      
		producer.close();        
		session.close();       
		connection.close(); 
		} }
