package Message;

import javax.jms.MessageProducer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;



public class SenderGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	// will first hold "Username:", later on "Enter message"
	private JLabel label;
	
	private JTextField tf;
	
	private JTextField tfServer, tfPort;
	
	private JButton login, logout;
	
	private JTextArea ta;
	
	private boolean connected;
	private Sender Sender;
	private int defaultPort;
	private String defaultHost;
	
	
	SenderGUI(String host, int port) {

		super("Sender");
		defaultPort = port;
		defaultHost = host;
		
		
		JPanel northPanel = new JPanel(new GridLayout(2,1));
		JPanel top = new JPanel(new GridLayout(1,2));
		
		JPanel serverAndPort = new JPanel(new GridLayout(1,5, 1, 3));
		
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
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		top.add(tf);
		northPanel.add(top);
		add(northPanel, BorderLayout.NORTH);

		
		ta = new JTextArea("Welcome to CSE110 Chat room\n", 80, 80);
		JPanel centerPanel = new JPanel(new GridLayout(1,1));
		centerPanel.add(new JScrollPane(ta));
		ta.setEditable(false);
		add(centerPanel, BorderLayout.CENTER);

		// the 3 buttons
		login = new JButton("Login");
		login.addActionListener(this);
		logout = new JButton("Logout");
		logout.addActionListener(this);
		logout.setEnabled(false);		// you have to login before being able to logout
				

		JPanel southPanel = new JPanel();
		southPanel.add(login);
		southPanel.add(logout);
		
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
		
		if(connected) {
			// just have to send the message
			try {
				Sender.fullSendService(defaultHost, tf.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}				
			tf.setText("");
			return;
		}
		

		if(o == login) {
			
			String username = tf.getText().trim();
			int usr = Integer.parseInt(username);
			
			if(username.length() == 0)
				return;
			
			String server = tfServer.getText().trim();
			int sev = Integer.parseInt(server);
			if(server.length() == 0)
				return;
			
			String portNumber = tfPort.getText().trim();
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
			Sender = new Sender(usr, port, sev, this);
			// test if we can start the Sender
			
			tf.setText("");
			label.setText("Enter your message below");
			connected = true;
			
			
			login.setEnabled(false);
			
			logout.setEnabled(true);
			
			tfServer.setEditable(false);
			tfPort.setEditable(false);
			
			tf.addActionListener(this);
		}
		

	}

	// to start the whole thing the server
	public static void main(String[] args) {
		new SenderGUI("localhost", 1500);
	}

}
