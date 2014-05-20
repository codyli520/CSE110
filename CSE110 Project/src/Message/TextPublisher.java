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
public static void main(String[] args) throws JMSException {       

JFileChooser fileChooser = new JFileChooser();        
fileChooser.setDialogTitle("Choose the text you want to post");       
if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {         
return;         
}       
File file = fileChooser.getSelectedFile();        
      
ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(               
		"tcp://localhost:61616?jms.blobTransferPolicy.defaultUploadUrl=http://localhost:8161/fileserver/");         
   
Connection connection = connectionFactory.createConnection();        
connection.start();       
 
ActiveMQSession session = (ActiveMQSession) connection.createSession(                
false, Session.AUTO_ACKNOWLEDGE);      
 
Destination destination = session.createQueue("txts.txt");       
     
MessageProducer producer = session.createProducer(destination);       
producer.setDeliveryMode(DeliveryMode.PERSISTENT);

BlobMessage blobMessage = session.createBlobMessage(file);      
blobMessage.setStringProperty("FILE.NAME", file.getName());       
blobMessage.setLongProperty("FILE.SIZE", file.length());        
producer.send(blobMessage);       
System.out.println("Text posted!" + file.getName());        
producer.close();        
session.close();       
connection.close(); 
} }
