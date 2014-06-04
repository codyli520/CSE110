package Message;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
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

public class testTextReader {
	File file = new File("/Users/xiaomengwang/Documents/UCSD/CSE110/test.txt");	
	File file2 = new File("/Users/xiaomengwang/Documents/UCSD/test.txt");

	@Test
	public void testReadTxtFile()throws FileNotFoundException{
		TextReader tr = new TextReader(file );
		assertTrue(tr.readTxtFile());
		
		try{
			TextReader tr2 = new TextReader(file2);
			fail();
		}
		catch(FileNotFoundException e)
		{
			assertTrue(true);
		}
		
	}
	
}
