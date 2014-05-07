package Message;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.junit.Test;

public class ReceiverTest {

    	@Test
	public void testReceiveMessage() throws JMSException, IOException {
		Receiver testRe = new Receiver();
		String s =  new String ("testing");
		Sender testSe = new Sender(001,1);		
		
		MessageProducer[] producers = testSe.getMessageProducer();
		testSe.fullSendService("FirstQueue",s);
		testSe.sendPrep("FirstQueue");
	
		testSe.getSession().commit();

		testRe.receiverPrep();
		
		
		assertEquals(testRe.receiveMessage(),"testing");
		
	}



	@Test //SURI
	public void testReceivePrep() {
		Receiver testRe = new Receiver();
		String test = "testReceiverPrep";
	    Sender testSender = new Sender(10 , 1);
	   
	    try{
		testRe.receiverPrep();
	    }
	    catch(Exception e){
		
	    }
	    if(testRe.getConnection()==null){
		fail();
	    }

	    if(testRe.getDestinations() == null){
		fail();
	    }
	   
	    if(testRe.getSession() ==null){
		fail();
	    }
	    if(testRe.getConsumer() == null){
	    fail();
               }
	    
	    
	    return;

	}

}
