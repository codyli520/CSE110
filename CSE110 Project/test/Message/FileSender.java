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

public class FileSender {   
/**    
* @param args    
* @throws JMSException     
*/   
public static void main(String[] args) throws JMSException {       
// ѡ���ļ�       
JFileChooser fileChooser = new JFileChooser();        
fileChooser.setDialogTitle("��ѡ��Ҫ���͵��ļ�");       
if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {         
return;         
}       
File file = fileChooser.getSelectedFile();        
// ��ȡ ConnectionFactory         
ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(               
"tcp://10.80.38.10:61616?jms.blobTransferPolicy.defaultUploadUrl=http://10.80.38.10:8161/fileserver/");         
// ���� Connection       
Connection connection = connectionFactory.createConnection();        
connection.start();       
// ���� Session     
ActiveMQSession session = (ActiveMQSession) connection.createSession(                
false, Session.AUTO_ACKNOWLEDGE);      
// ���� Destination      
Destination destination = session.createQueue("File.Transport");       
// ���� Producer      
MessageProducer producer = session.createProducer(destination);       
producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);// ����Ϊ�ǳ־���        
// ���ó־��ԵĻ����ļ�Ҳ�����Ȼ������������ն�����������Ҳ�����յ��ļ�       
// ���� BlobMessage�����������ļ�   
BlobMessage blobMessage = session.createBlobMessage(file);      
blobMessage.setStringProperty("FILE.NAME", file.getName());       
blobMessage.setLongProperty("FILE.SIZE", file.length());       
System.out.println("��ʼ�����ļ���" + file.getName() + "���ļ���С��"                
+ file.length() + " �ֽ�");       
// 7. �����ļ�     
producer.send(blobMessage);       
System.out.println("����ļ����ͣ�" + file.getName());        
producer.close();        
session.close();       
connection.close(); // ���ر� Connection, �������˳�     
} }

