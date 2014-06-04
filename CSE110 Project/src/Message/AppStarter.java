package Message;
import java.io.*;
import java.util.*;

public class AppStarter {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{

	String command = null;
	boolean start = true;
	Scanner scan = new Scanner(System.in);
	File file;
	File dir;
	User user1 = new User(100,"user1","0");
	BufferedReader reader = null;
	String text = null;
	String directoryPath = null;
	String fileName = null;
	String filePath = null;
	String system_username = System.getProperty("user.name");
	CreateText newtext;
	WriteText writetext;
	
	directoryPath = "/Users/" + system_username + "/Desktop/ChatHistory/";
	dir = new File(directoryPath);
	if (!dir.exists()) {
	      dir.mkdir(); 
	}
	
	filePath = directoryPath + "host-subject";
	file = new File(filePath);
	if(!file.exists()){
	    newtext = new CreateText(directoryPath, "subject", "host");
	}
	
	System.out.println("App Started...");
	System.out.println("Please enter Command: ");
	while(start){
	    
	    command = scan.next();
	    //if command is view
	    if(command.equals("view")){
		fileName = "host-subject";
		filePath = directoryPath+fileName;
		file = new File(filePath);   

		try {
		    reader = new BufferedReader(new FileReader(file));

		    while ((text = reader.readLine()) != null) {
			System.out.println(text);
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
			if (reader != null) {
			    reader.close();
			}
		    } catch (IOException e) {
		    }
		}

	    }
	    else if(command.equals("viewsubject")){
		
		System.out.println("Please enter the subject name: ");
		String subject = scan.next();
		fileName = subject;
		filePath = directoryPath + subject;
		file = new File(filePath);   

		try {
		    reader = new BufferedReader(new FileReader(file));

		    while ((text = reader.readLine()) != null) {
			System.out.println(text);
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
			if (reader != null) {
			    reader.close();
			}
		    } catch (IOException e) {
		    }
		}
		
	    }
	    else if(command.equals("viewpost")){
		
		System.out.println("Please enter the topic name: ");
		String topicname = scan.next();
		fileName = topicname;
		filePath = directoryPath + fileName;
		if(topicname != null)
		    user1.receiveMessage(topicname);
		else
		    System.out.println("topic name cannot be empty");
		
	    }
	    else if(command.equals("posttext")){
		
		System.out.println("Please enter the topic name:");
		String topic = scan.nextLine();
		while(topic==null || topic.equals("")){
		    topic = scan.nextLine();
		    if(topic == null){
			System.out.println("Topic cannot be null");
		    }
		    
		}
		System.out.println("Please Enter the Post Content:");
		filePath = directoryPath + user1.getUsername()+"-"+topic;
		writetext = new WriteText(directoryPath+"host-subject");
		writetext.writeToText(user1.getUsername()+"-"+topic);
		newtext = new CreateText(directoryPath, topic, user1.getUsername());
		newtext.writeToFile();
		if(topic != null){
		    user1.publishMessage(user1.getUsername()+"-"+topic, filePath);
		}
		//newtext = new CreateText(directoryPath, topic, user1.getUsername());
	    }

	    else if(command.equals("quitapp")){
		break;
	    }
	}	
	
	scan.close();
	System.out.println("app quitted");
    }
}
