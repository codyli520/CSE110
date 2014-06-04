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
public class testTxtreturn {
	
	File file = new File("/Users/xiaomengwang/Documents/UCSD/CSE110/test.txt");	
	File file2 = new File("/Users/xiaomengwang/Documents/UCSD/test.txt");

	@Test
	public void testTxtReturnSetup() throws FileNotFoundException{
		Txtreturn tr = new Txtreturn(file,"test" );
		assertTrue(tr.txtReturnSetup());
		try{
			Txtreturn tr2 = new Txtreturn(file2,"test" );
			fail();
		}
		catch(FileNotFoundException e)
		{
			assertTrue(true);
		}
		
	}

	public void testReturnText() throws FileNotFoundException{
		Txtreturn tr = new Txtreturn(file,"test" );
		assertTrue(tr.returnText());
		try{
			Txtreturn tr2 = new Txtreturn(file2,"test" );
			fail();
		}
		catch(FileNotFoundException e)
		{
			assertTrue(true);
		}		
	}
}
