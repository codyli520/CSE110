package Message;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
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

public class testTextReceiver {
	
	@Test
	public void testbBlobReceive() throws JMSException{
		TextPublisher testPublisher = new TextPublisher("test2","/Users/xiaomengwang/Desktop/ChatHistory/host-subject");
		testPublisher.publishMessage();
		
		TextReceiver tr = new TextReceiver("test2");
		
		assertTrue(tr.blobReceive());
		
		assertTrue(true);

		//TextReceiver tr2 = new TextReceiver("test" );
		//assertTrue(tr2.blobReceive());
	}

	@Test
	public void testbReadMessage() throws JMSException{
		TextPublisher testPublisher = new TextPublisher("test2","/Users/xiaomengwang/Desktop/ChatHistory/host-subject");
		testPublisher.chooseFile();
		testPublisher.publishMessage();
		
		TextReceiver tr = new TextReceiver();
		assertTrue(tr.readMessage());

		
		TextReceiver tr2 = new TextReceiver("test" );
		assertTrue(tr2.readMessage());
		
		assertTrue(true);
	}
}
