package Message;

import java.io.*;
import java.util.Scanner;

public class CreateText {

	File file;
	String filepath;
	PrintWriter writer;
	Scanner scan;
	String input;
	String user;
	String subject;


	public CreateText(String dirpath, String subject, String user){
		this.filepath = dirpath+ user +"-"+subject;
		this.user = user;
		this.subject = subject;
	}

	public void writeToFile(String input) throws FileNotFoundException, UnsupportedEncodingException{

		
		if(!input.equals("postend")){
			writer = new PrintWriter( filepath, "UTF-8");
			writer.println(user + ":" + subject);
			scan = new Scanner(System.in);
			input = scan.nextLine();
			while(!input.equals("postend")){
				writer.print(input);
				input = scan.nextLine();
			}
		}
		else{
			//writer.close();
		}
		
	}
}
