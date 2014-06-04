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
	String command = null;
	String command2 = null;
	Object o;
	String topic,content;
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
	boolean terminal =  false;
	//	Appstarter app;


	public void begin() throws FileNotFoundException, UnsupportedEncodingException{




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
						terminal = true;
					}
				}
				else{
					if( command != null )
					{
						System.out.println("GUI command " + command);
						terminal = false;
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
               if(terminal == false){
            	   process.append("\n\nsubject name: \n"+input.getText());
            	   String GUIsubject = input.getText();
   				fileName = GUIsubject;
   				filePath = directoryPath + GUIsubject;
   				file = new File(filePath);  
   				try {
   					reader = new BufferedReader(new FileReader(file));

   					while ((text = reader.readLine()) != null) {
   						process.append("\n"+text);
   						
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
				System.out.println("Please enter the subject name: ");
				
				 
			   //while(command.equals("send")){
				String subject = scan.next();
				fileName = subject;
				filePath = directoryPath + subject;
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
			}
			else if(command.equals("viewpost")){

				//System.out.println("Please enter the topic name: ");
				//process.append("\n\nPlease enter the topic name: ");
				if(terminal == false){
					String GUItopic = input.getText();
					process.append("\n\nTopic name entered\n" + GUItopic);
					fileName = GUItopic;
					filePath = directoryPath + fileName;
					if(GUItopic != null)
						user1.receiveMessage(GUItopic);
					else
						System.out.println("topic name cannot be empty");
				}
				
				else if (terminal == true){
				System.out.println("Please enter the topic name: ");
				String topicname = scan.next();
				
				fileName = topicname;
				filePath = directoryPath + fileName;
				if(topicname != null)
					user1.receiveMessage(topicname);
				else
					System.out.println("topic name cannot be empty");
				}

			}
			else if(command.equals("posttext") ){
				if(terminal == false){
				
				topic= input.getText(); 
						while(topic==null || topic.equals("")){

							if((topic = input.getText())== null){
								process.setText("Topic cannot be null");
							}

						}
						
						filePath = directoryPath + user1.getUsername()+"-"+topic;
						
						
						content = input2.getText();
						process.append("\n\ntopic name\n"+topic);
						process.append("\n\ncontent\n"+content);
						writetext = new WriteText(directoryPath+"host-subject");
						writetext.writeToText(user1.getUsername()+"-"+content);
						newtext = new CreateText(directoryPath, topic, user1.getUsername());
						newtext.writeToFile(topic);

						if(topic != null){
							user1.publishMessage(user1.getUsername()+"-"+topic, filePath);
						}
						
				}
				
				
				else{
				System.out.println("Please enter the topic name:");

				
				//process.append("\n"+topic);

				//input.addActionListener(this);

				while(topic==null || topic.equals("")){

					if((topic = scan.nextLine())== null){
						process.setText("Topic cannot be null");
						System.out.println("Topic cannot be null");
					}

				}
				
				filePath = directoryPath + user1.getUsername()+"-"+topic;
				writetext = new WriteText(directoryPath+"host-subject");
				writetext.writeToText(user1.getUsername()+"-"+topic);
				//newtext = new CreateText(directoryPath, topic, user1.getUsername());
				//topicSent = 1;

				process.append("\n\nPlease Enter the Post Content:");
				System.out.println("Please Enter the Post Content:");
				input.setText("");
			//}

			//else if(command.equals("sent")){
				//topic= input.getText() ;
				newtext = new CreateText(directoryPath, topic, user1.getUsername());
				newtext.writeToFile(topic);

				if(topic != null){
					user1.publishMessage(user1.getUsername()+"-"+topic, filePath);
				}
				//newtext = new CreateText(directoryPath, topic, user1.getUsername());
			}
			}


			else if(command.equals("quitapp")){
				break;
			}
			command = null;
		}	

		scan.close();
		System.out.println("app quitted");
	}




	private JLabel label1,label2;

	private JTextField input,input2;


	//private JTextField tfServer, tfPort;

	private JButton view, viewsubject,viewpost,posttext,quitapp;

	private JTextArea process;

	//private boolean connected;
	//private Sender Sender;
	//private int defaultPort;


	AppStarter(){
		input = new JTextField(10);
		input2 = new JTextField(10);
		view = new JButton("View");
		viewsubject = new JButton("View Subject");
		viewpost = new JButton("View Post");
		posttext = new JButton("Post");
		quitapp = new JButton("quit");
		//send = new JButton("Send");
		viewsubject.setEnabled(false);
		viewpost.setEnabled(false);
		label1 = new JLabel("Input topic here");
		label2 = new JLabel ("Enter content here");
		

		view.addActionListener(this);
		viewsubject.addActionListener(this);
		viewpost.addActionListener(this);
		posttext.addActionListener(this);
		quitapp.addActionListener(this);
		//send.addActionListener(this);


		JPanel southPanel = new JPanel (new GridLayout(3,1));
		JPanel bot = new JPanel (new GridLayout(1,2));
		JPanel bot2 = new JPanel (new GridLayout(1,5));
		JPanel bot3 = new JPanel (new GridLayout(1,2));
		
		bot3.add(label1);
		bot3.add(label2);
		southPanel.add(bot3);
		bot.add(input);
		bot.add(input2);
		southPanel.add(bot);
		bot2.add(view);
		bot2.add(viewsubject);
		bot2.add(viewpost);
		bot2.add(posttext);
		bot2.add(quitapp);

		southPanel.add(bot2);
		
		
		this.add(southPanel,BorderLayout.SOUTH);

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
			viewsubject.setEnabled(true);
			viewpost.setEnabled(true);
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
		/*else if( o ==  send){
			command = "send";
			topic = input.getText();
			input.setText("");
		}*/
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