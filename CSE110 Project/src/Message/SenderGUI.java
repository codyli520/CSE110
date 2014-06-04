package Message;

import javax.jms.MessageProducer;
import javax.swing.*;

import org.omg.CORBA.portable.InputStream;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;



public class SenderGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	// will first hold "Username:", later on "Enter message"
	private JLabel label;
	
	private JTextField tf;
	public JTextField messageTf;
	
	private JTextField tfServer, tfPort;
	
	private JButton login, logout,send,receive;
	
	private JTextArea ta;
	
	private boolean connected;
	private Sender Sender;
	private int defaultPort;
	private String defaultHost, user;
	
	
	SenderGUI(String host, int port) {

		super("Sender");
		defaultPort = port;
		defaultHost = host;
		
		
		JPanel northPanel = new JPanel(new GridLayout(2,1));
		JPanel top = new JPanel(new GridLayout(1,3));
		
		JPanel serverAndPort = new JPanel(new GridLayout(1,5, 1, 3));
		
		login = new JButton("Login");
		login.addActionListener(this);
		logout = new JButton("Logout");
		logout.addActionListener(this);
		logout.setEnabled(false);
		
		tfServer = new JTextField(host);
		tfPort = new JTextField("" + port);
		tfPort.setHorizontalAlignment(SwingConstants.RIGHT);

		serverAndPort.add(new JLabel("Server Address:  "));
		serverAndPort.add(tfServer);
		serverAndPort.add(new JLabel("Port Number:  "));
		serverAndPort.add(tfPort);
		serverAndPort.add(new JLabel(""));
		
		northPanel.add(serverAndPort);

		label = new JLabel("Enter your username");
		
		top.add(label);
		
		tf = new JTextField();
		tf.setBackground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		top.add(tf);
		top.add(login);
		top.add(logout);
		northPanel.add(top);
		add(northPanel, BorderLayout.NORTH);

		
		ta = new JTextArea("Welcome to CSE110 Chat room\n", 80, 80);
		JPanel centerPanel = new JPanel(new GridLayout(1,1));
		centerPanel.add(new JScrollPane(ta));
		ta.setEditable(false);
		add(centerPanel, BorderLayout.CENTER);

		messageTf = new JTextField();
		messageTf.setBackground(Color.WHITE);
				// you have to login before being able to logout
	    send = new JButton("Send");
        send.addActionListener(this);
        receive = new JButton("Receive");
        receive.addActionListener(this);
        
		JPanel southPanel = new JPanel(new GridLayout(2,1));
		JPanel buttonPanel = new JPanel();
		southPanel.add(messageTf);
		buttonPanel.add(send);
		buttonPanel.add(receive);
		southPanel.add(buttonPanel);
		add(southPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
		tf.requestFocus();

	}

	
	void showText(String str) {
		ta.append(str);
		ta.setCaretPosition(ta.getText().length() - 1);
	}
	
	
	void connectionFailed() {
		login.setEnabled(true);
		logout.setEnabled(false);
		label.setText("Enter your username below");
		tf.setText("Anonymous");
		
		tfPort.setText("" + defaultPort);
		tfServer.setText(defaultHost);
		
		tfServer.setEditable(false);
		tfPort.setEditable(false);
	
		tf.removeActionListener(this);
		connected = false;
	}
		
	/*
	* Button or JTextField clicked
	*/
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		// if it is the Logout button
		/*if(o == logout) {
			return;
		}
		*/
		
		/*if(connected) {
			// just have to send the message
			try {
				Sender.fullSendService(defaultHost, tf.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}				
			tf.setText("");
			return;
		}*/
		
		//TODO
		if(o == send){
			if(connected == false){
				ta.setText("Please login to start chat.");
				
			}
			else{
					try {
						
						ta.setText("");
						Sender.fullSendService(user, messageTf.getText());
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}	
			
		}
		
		if(o == receive){
			
		}
		
		if(o == login) {
			
			String username = tf.getText().trim();
			user = tf.getText().trim();
			int usr = Integer.parseInt(username);
			
			if(username.length() == 0)
				return;
			
			String server = tfServer.getText().trim();
			
			if(server.length() == 0)
				return;
			
			String portNumber = tfPort.getText().trim();
			//int rev = Integer.parseInt(portNumber);
			if(portNumber.length() == 0)
				return;
			int port = 0;
			try {
				port = Integer.parseInt(portNumber);
			}
			catch(Exception en) {
				return;   
			}

			// try creating a new Sender with GUI
			Sender = new Sender(usr, 0, port, this);
			// test if we can start the Sender
			//String id = tf.getText();
			tf.setText("");
			ta.setText("Enter your message below, click Send when finish");
			
			connected = true;
			
			
			
			login.setEnabled(false);
			
			logout.setEnabled(true);
			
			tfServer.setEditable(false);
			tfPort.setEditable(false);
			
			messageTf.addActionListener(this);
		}
		

	}

	// to start the whole thing the server
	public static void main(String[] args) {
		new SenderGUI("localhost", 8161);
	}

}
