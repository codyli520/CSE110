package Message;

import java.io.*;
import java.util.Scanner;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateText {

    File file;
    String filepath;
    PrintWriter writer;
    Scanner scan;
    String input;
    String user;
    String subject;
    AppStarter app;

    public CreateText(String dirpath, String subject, String user){
	this.filepath = dirpath+ user +"-"+subject;
	this.user = user;
	this.subject = subject;
    }

    public void writeToFile(JTextArea process ,JTextField text, String command) throws FileNotFoundException, UnsupportedEncodingException{

	writer = new PrintWriter( filepath, "UTF-8");
	writer.println(user + ":" + subject);
	input = text.getText();
	
	while(!AppStarter.command.equals("send")){	    
	    input = text.getText();
	}
	writer.print(input);
	System.out.println(input);
	process.append(input);
	writer.close();

    }
    public void writeToFile(JTextArea text) throws FileNotFoundException, UnsupportedEncodingException{

	writer = new PrintWriter( filepath, "UTF-8");
	writer.println(user + ":" + subject);
	scan = new Scanner(System.in);
	    input = scan.nextLine();
	while(!input.equals("postend")){
	    writer.println(input);
	    input = scan.nextLine();
	    System.out.println(input);
	    text.append(input);
	}
	writer.close();

    }
}
