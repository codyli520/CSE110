package Message;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AppStarter extends JFrame implements ActionListener {
	String command = null;
	
	
	
    public void begin() throws FileNotFoundException, UnsupportedEncodingException{

	
	boolean start = true;
	Scanner scan = new Scanner(System.in);
	InputStreamReader ISR = new InputStreamReader(System.in);
    BufferedReader in = new BufferedReader(ISR);
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
		newtext = new CreateText(directoryPath,"subject" , "host");
		writetext = new WriteText(directoryPath+"host-subject");
		writetext.writeToText("Subject List:");
		//newtext = new CreateText(directoryPath,"subject" , "host");
	}
	
	System.out.println("App Started...");
	System.out.println("Please enter Command: ");
	
	while(start){
		try{
			if( command == null && in.ready()){
				command = in.readLine();
				if( command == null || command.equals("") ){
					command = null;
					continue;
				}
				else{
					System.out.println("terminal command " + command);
				}
			}
			else{
				if( command != null )
				{
					System.out.println("GUI command " + command);
				}
				else{
					continue;
				}
			}
		}
		catch(IOException e){
			System.out.println("well");
			continue;
		}

	    //if command is view
	    if(command.equals("view")){
	    	System.out.println("modeview");
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
	    command = null;
	}	
	
	scan.close();
	System.out.println("app quitted");
    }



	
	private JLabel label;
	
	//private JTextField command;
	
	
	//private JTextField tfServer, tfPort;
	
	private JButton view, viewsubject,viewpost,posttext,quitapp;
	
	private JTextArea process;
	
	private boolean connected;
	//private Sender Sender;
	private int defaultPort;

	
	AppStarter(){
		
		view = new JButton("View");
		viewsubject = new JButton("View Subject");
		viewpost = new JButton("View Post");
		posttext = new JButton("Post");
		quitapp = new JButton("quit");
		view.addActionListener(this);
		viewsubject.addActionListener(this);
		viewpost.addActionListener(this);
		posttext.addActionListener(this);
		quitapp.addActionListener(this);
		JPanel northPanel = new JPanel ();
		northPanel.add(view);
		northPanel.add(viewsubject);
		northPanel.add(viewpost);
		northPanel.add(posttext);
		northPanel.add(quitapp);
		
		this.add(northPanel,BorderLayout.SOUTH);
		
		process= new JTextArea("App Started...\nPlease select Command : \n", 40, 40);
		JPanel centerPanel = new JPanel(new GridLayout(1,1));
		centerPanel.add(new JScrollPane(process));
		process.setEditable(false);
		add(centerPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
		
	}
	
	void showText(String str) {
		process.append(str);
		process.setCaretPosition(process.getText().length() - 1);
	}
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o==view){
			command = "view";
			process.setText("");
		}
		else if (o== viewsubject){
			command = "viewsubject";
		}
		else if (o==viewpost){
			command = "viewpost";
		}
		else if (o == posttext){
			command = "posttext";
		}
		else if (o == quitapp){
			command = "quitapp";
			
		}
	}

	public static void main(String[] args) {
		AppStarter appstarter = new AppStarter();
		try {
			appstarter.begin();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}