package Message;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AppStarter extends JFrame implements ActionListener {
    public static String command = null;
    String command2 = null;
    Object o;
    String topic;
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
    int topicSent= 0;
    boolean sw = false;
    SenderGUI sg;
    //	Appstarter app;




    public void begin() throws IOException{




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
			//System.out.println("terminal command " + command);
		    }
		}
		else{
		    if( command != null )
		    {
			//System.out.println("GUI command " + command);
		    }
		    else{
			continue;
		    }
		}
	    }
	    catch(IOException e){
		//System.out.println("well");
		continue;
	    }

	    //if command is view
	    if(command.equals("view")){
		process.setText("");
		fileName = "host-subject";
		filePath = directoryPath+fileName;
		file = new File(filePath);   

		try {
		    reader = new BufferedReader(new FileReader(file));

		    while ((text = reader.readLine()) != null) {
			process.append("\n"+text);
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
		process.setText("");
		System.out.println("Please enter the subject name: ");
		process.append("Please enter the subject name: \n");
		String subject=null;
		if(sw == false){
		    subject = input.getText().trim();
		    while(subject.length()==0 || !command.equals("send"))
			subject = input.getText();
		}
		else{
		    subject = scan.next();
		}
		input.setText("");
		if(subject.length()!=0){
		    fileName = subject;
		    filePath = directoryPath + subject;
		    file = new File(filePath);   

		    try {
			reader = new BufferedReader(new FileReader(file));

			while ((text = reader.readLine()) != null) {
			    process.append(text+"\n");
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
		else{
		    continue;
		}
	    }
	    else if(command.equals("viewpost")){
		process.setText("");
		System.out.println("Please enter the topic name: ");
		process.append("Please enter the topic name: ");
		String topicname = null;
		if(sw == false){
		    topicname = input.getText().trim();
		    while(topicname.length()==0 || !command.equals("send"))
			topicname = input.getText();
		}
		else{
		    topicname = scan.next();
		}
		System.out.println("The topic name is: "+topicname);
		process.append("The topic name is: "+topicname+"\n");
		input.setText("");
		fileName = topicname;
		filePath = directoryPath + fileName;
		if(topicname != null)
		    user1.receiveMessage(topicname);
		else
		    System.out.println("topic name cannot be empty");

	    }
	    else if(command.equals("posttext") ){
		process.setText("");
		command = "posttext";
		System.out.println("Please enter the topic name:");
		process.append("Please enter the topic name:\n");
		String topic;
		//System.out.println(sw);
		
		if(sw == false){
		    topic = input.getText();
		    while(topic.length()==0 || !command.equals("send"))
			topic = input.getText();
		}
		else{
		    topic = scan.nextLine();
		    while(topic==null || topic.equals("")){
			topic = scan.nextLine();
			if(topic == null){
			    System.out.println("Topic cannot be null");
			}

		    }
		}
		
		System.out.println("The topic is: " + topic);
		process.append("The topic is: "+topic+"\n");
		input.setText("");
		command = "posttext";
		System.out.println("Please Enter the Post Content:");
		process.append("Please Enter the Post Content:\n");
		filePath = directoryPath + user1.getUsername()+"-"+topic;
		writetext = new WriteText(directoryPath+"host-subject");
		writetext.writeToText(user1.getUsername()+"-"+topic);
		newtext = new CreateText(directoryPath, topic, user1.getUsername());
		if(sw==false){
		    newtext.writeToFile(process,input,command);
		}
		else{
		    newtext.writeToFile(process);
		}
		if(topic != null){
		    user1.publishMessage(user1.getUsername()+"-"+topic, filePath);
		}
		input.setText("");
	    }
	    else if(command.equals("switchUI")){
		sw = !sw;
		System.out.println(sw);
		
		
		//continue;
	    }
	    else if(command.equals("reply")){
			
			sg=new SenderGUI("localhost", 8161);
			
			//continue;
		    }
	    else if(command.equals("quitapp")){
		break;
	    }
	    command = null;
	    System.out.println("Please enter Command: ");
	}	

	scan.close();
	System.out.println("app quitted");
    }




    //private JLabel label;

    private JTextField input;


    //private JTextField tfServer, tfPort;

    private JButton view, viewsubject,viewpost,posttext,quitapp,send,switchUI,reply;

    private JTextArea process;

    //private boolean connected;
    //private Sender Sender;
    //private int defaultPort;


    AppStarter(){
	input = new JTextField(10);
	view = new JButton("View");
	viewsubject = new JButton("View Subject");
	viewpost = new JButton("View Post");
	posttext = new JButton("Post");
	quitapp = new JButton("quit");
	send = new JButton("Send");
	switchUI = new JButton("switchUI");
	reply = new JButton("reply");

	view.addActionListener(this);
	viewsubject.addActionListener(this);
	viewpost.addActionListener(this);
	posttext.addActionListener(this);
	quitapp.addActionListener(this);
	send.addActionListener(this);
	switchUI.addActionListener(this);
	reply.addActionListener(this);
	
	JPanel northPanel = new JPanel (new GridLayout(3,2));
	JPanel bot = new JPanel (new GridLayout(1,2));
	JPanel bot2 = new JPanel (new GridLayout(1,3));
	JPanel bot3 = new JPanel (new GridLayout(1,4));
	bot.add(input);
	bot.add(send);
	northPanel.add(bot);
	bot2.add(view);
	bot2.add(viewsubject);
	bot2.add(viewpost);
	bot3.add(posttext);
	bot3.add(switchUI);
	bot3.add(reply);
	bot3.add(quitapp);
	

	northPanel.add(bot2);
	northPanel.add(bot3);
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
	o = e.getSource();
	if(o==view){
	    command = "view";
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
	    System.exit(0);
	}
	else if( o ==  send){
	    command = "send";
	    topic = input.getText().trim();
	}
	else if (o == switchUI){

	    command = "switchUI";

	}
	else if(o== reply){
		command = "reply";
	}
    }


    public static void main(String[] args) throws IOException {
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
