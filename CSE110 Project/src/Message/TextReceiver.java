package Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.JFileChooser;



import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.BlobMessage;

public class TextReceiver {  
	ConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	Destination destination;
	MessageConsumer consumer;
	BlobMessage blobMessage;
	TextReader textReader;
	Txtreturn txtreturn;
	String topic;
	
	public TextReceiver() throws JMSException{
		this("default");
	}
	
	public TextReceiver(String newTopic) throws JMSException{
		topic = newTopic;
		connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");        
		connection = connectionFactory.createConnection();         
		connection.start();       
		
		session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);       
		destination = session.createQueue(topic);
		consumer = session.createConsumer(destination);
	}
	
	public boolean blobReceive() throws JMSException{
		blobMessage = (BlobMessage)consumer.receive();
		if (blobMessage==null){
			return false;
		}
		else{
			return true;
		}
	}
	
	//this method should only be called if blobReceive returns true
	public boolean readMessage(){
		if (blobMessage!=null){
			return false;
		}
		else{
			try{
				String fileName = blobMessage.getStringProperty("FILE.NAME");                       	                          
				File file = new File("ToRead.txt");                            
				OutputStream os = new FileOutputStream(file);                           
				System.out.println("StartReading：" + fileName);                            
				InputStream inputStream = blobMessage.getInputStream();                            
				//写文件，你也可以使用其他方式                        
				byte[] buff = new byte[256];                          
				int len = 0;                            
				while ((len = inputStream.read(buff))>0) {                                
					os.write(buff, 0, len);                             
				}                            
				os.close();
				textReader = new TextReader(file);
				textReader.readTxtFile();
				txtreturn = new Txtreturn(file, topic);
				txtreturn.returnText();
	            return true;  
			} catch (Exception e) {                        
				e.printStackTrace();
				return false;
			}      
		}
	}	
       
}  


