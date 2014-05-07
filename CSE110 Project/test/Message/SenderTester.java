package Message;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.junit.Test;

public class SenderTester {

    @Test
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


    @Test
    public void testGrabMessage() throws IOException
    {
	String test = "test";
	Sender testSender1 = new Sender(001,1);
	assertEquals(testSender1.grabMessage("testttt"), "testttt");
    }


    @Test
    public void testSendMessage() throws IOException, Exception
    {
	String test = "test1111";
	Sender testSender = new Sender(001,1);

	String inputstring = testSender.grabMessage(test);
	assertEquals(testSender.grabMessage(test), test);

	MessageProducer[] producers = testSender.getMessageProducer();
	testSender.sendPrep("FirstQueue");
	testSender.sendMessage(producers[0], test);
	testSender.getSession().commit();

	Receiver rev = new Receiver();
	rev.receiverPrep();
	TextMessage message = (TextMessage) rev.getConsumer().receive(1000);

	assertEquals(message.getText(),test);

    }


}

