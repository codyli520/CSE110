package Message;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.JFileChooser;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;
import org.junit.Test;

public class testSender {

	public void testSenderPrep(){
	    String test = "test";
	    Sender testSender = new Sender(10 , 1);
	    try{
		testSender.sendPrep(test);
	    }
	    catch(Exception e){
		
	    }
	    if(testSender.getConnection()==null){
		fail();
	    }
	    if(testSender.getNumOfQueue()!=0){
		fail();
	    }
	    if(testSender.getDestinations()[0] == null){
		fail();
	    }
	    if(testSender.getMessageProducer()[0]==null){
		fail();
	    }
	    if(testSender.getSession() ==null){
		fail();
	    }
	    
	    return;
	}
//xm finish 

	@Test
	public void testGrabMessage() throws IOException
	{
		String test = "test";
		/*Sender testSender = new Sender(001,0);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			//BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			//assertEquals(br2.readLine(),testSender.grabMessage());
			assertEquals(br.readLine(),testSender.grabMessage(""));
			
			fail();
		}
		catch (IOException e)
		{
			assertTrue(true);
		}
	*/
		Sender testSender1 = new Sender(001,1);
		assertEquals(testSender1.grabMessage("testttt"), "testttt");
	}
	
	
	@Test
	public void testSendMessage() throws IOException, Exception
	{
		String test = "test1111";
		Sender testSender = new Sender(001,1);
       // Session session = testSender.getSession();
		
    	String inputstring = testSender.grabMessage(test);
		assertEquals(testSender.grabMessage(test), test);
		
		MessageProducer[] producers = testSender.getMessageProducer();
		//testSender.fullSendService("FirstQueue", test);
		testSender.sendPrep("FirstQueue");
		testSender.sendMessage(producers[0], test);
		testSender.getSession().commit();
		
		Receiver rev = new Receiver();
		rev.receiverPrep();
    	//rev.receiveMessage();
		TextMessage message = (TextMessage) rev.consumer.receive(1000);
    	assertEquals(message.getText(),test);
		
		
	}

}
