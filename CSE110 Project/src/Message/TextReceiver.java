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

public static void main(String[] args) throws JMSException {    
       
ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(                 
"tcp://localhost:61616");        
   
Connection connection = connectionFactory.createConnection();         
connection.start();       
     
Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);       
    
Destination destination = session.createQueue("txts.txt");       
      
MessageConsumer consumer = session.createConsumer(destination);   
BlobMessage blobMessage =(BlobMessage)consumer.receive();
if (blobMessage!=null){
try {    
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
	TextReader.readTxtFile(file);
	Txtreturn.txtreturn(file);
	                  
	} catch (Exception e) {                        
	e.printStackTrace();                    
	}               
	}	
       
}  
}


/*consumer.setMessageListener(new MessageListener() { 

public void onMessage(javax.jms.Message message) {
	if (message instanceof BlobMessage) {                    
		BlobMessage blobMessage = (BlobMessage) message;                  
		try {    
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
	TextReader.readTxtFile(file);
	Txtreturn.txtreturn(file);
	                  
	} catch (Exception e) {                        
	e.printStackTrace();                    
	}               
	}	
}        
});   
} }
*/