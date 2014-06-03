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
// 选择文件       
JFileChooser fileChooser = new JFileChooser();        
fileChooser.setDialogTitle("请选择要传送的文件");       
if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {         
return;         
}       
File file = fileChooser.getSelectedFile();        
// 获取 ConnectionFactory         
ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(               
"tcp://10.80.38.10:61616?jms.blobTransferPolicy.defaultUploadUrl=http://10.80.38.10:8161/fileserver/");         
// 创建 Connection       
Connection connection = connectionFactory.createConnection();        
connection.start();       
// 创建 Session     
ActiveMQSession session = (ActiveMQSession) connection.createSession(                
false, Session.AUTO_ACKNOWLEDGE);      
// 创建 Destination      
Destination destination = session.createQueue("File.Transport");       
// 创建 Producer      
MessageProducer producer = session.createProducer(destination);       
producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);// 设置为非持久性        
// 设置持久性的话，文件也可以先缓存下来，接收端离线再连接也可以收到文件       
// 构造 BlobMessage，用来传输文件   
BlobMessage blobMessage = session.createBlobMessage(file);      
blobMessage.setStringProperty("FILE.NAME", file.getName());       
blobMessage.setLongProperty("FILE.SIZE", file.length());       
System.out.println("开始发送文件：" + file.getName() + "，文件大小："                
+ file.length() + " 字节");       
// 7. 发送文件     
producer.send(blobMessage);       
System.out.println("完成文件发送：" + file.getName());        
producer.close();        
session.close();       
connection.close(); // 不关闭 Connection, 程序则不退出     
} }

