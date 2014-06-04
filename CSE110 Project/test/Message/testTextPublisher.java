package Message;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.junit.Test;

import javax.swing.JFileChooser;

public class testTextPublisher {
	
	@Test
	public void testChooseFile(){

		TextPublisher newtp2 = new TextPublisher("test","/Users/xiaomengwang/Desktop/ChatHistory/host-subject");
		assertTrue(newtp2.chooseFile());
		
	/*fileChooser.setDialogTitle("Choose the text you want to post");       
	if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {         
		return false;         
	}    
	file = fileChooser.getSelectedFile(); 
	return true;*/
	}
	
	@Test
	public void testInitPublisher(){

		TextPublisher newtp2 = new TextPublisher("test2","/Users/xiaomengwang/Desktop/ChatHistory/host-subject");
		assertTrue(newtp2.chooseFile());
		assertTrue(newtp2.initPublisher());

		
	/*public boolean initPublisher(){
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
		return true;*/
		
	}
	@Test
	public void testPublishMessage(){

		TextPublisher newtp2 = new TextPublisher("test3","/Users/xiaomengwang/Desktop/ChatHistory/host-subject");
		assertTrue(newtp2.publishMessage());	
	}
}
