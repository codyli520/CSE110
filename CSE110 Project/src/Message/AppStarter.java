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
			else if(command.equals("posttext") ){
				process.append("\n\nPlease enter the topic name");
				System.out.println("Please enter the topic name:");

				topic= input.getText() ;
				process.append("\n"+topic);

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
				newtext = new CreateText(directoryPath, topic, user1.getUsername());
				//topicSent = 1;

				process.append("\n\nPlease Enter the Post Content:");
				System.out.println("Please Enter the Post Content:");
				input.setText("");
			//}

			//else if(command.equals("sent")){
				//topic= input.getText() ;
				//newtext = new CreateText(directoryPath, topic, user1.getUsername());
				newtext.writeToFile(topic);

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

	private JTextField input;


	//private JTextField tfServer, tfPort;

	private JButton view, viewsubject,viewpost,posttext,quitapp,send;

	private JTextArea process;

	private boolean connected;
	//private Sender Sender;
	private int defaultPort;


	AppStarter(){
		input = new JTextField(10);
		view = new JButton("View");
		viewsubject = new JButton("View Subject");
		viewpost = new JButton("View Post");
		posttext = new JButton("Post");
		quitapp = new JButton("quit");
		send = new JButton("Send");


		view.addActionListener(this);
		viewsubject.addActionListener(this);
		viewpost.addActionListener(this);
		posttext.addActionListener(this);
		quitapp.addActionListener(this);
		send.addActionListener(this);


		JPanel northPanel = new JPanel (new GridLayout(2,1));
		JPanel bot = new JPanel (new GridLayout(1,2));
		JPanel bot2 = new JPanel (new GridLayout(1,5));
		bot.add(input);
		bot.add(send);
		northPanel.add(bot);
		bot2.add(view);
		bot2.add(viewsubject);
		bot2.add(viewpost);
		bot2.add(posttext);
		bot2.add(quitapp);

		northPanel.add(bot2);
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
			if(topicSent == 0)
				command = "send";
			else if(topicSent == 1)
				command = "sent";
			else if(topicSent == 2)
				command = "sent";
			//topic = input.getText().trim();
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