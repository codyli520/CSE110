package Message;

import java.io.*;

public class WriteText {

    PrintWriter out;
    String filepath;
    String input;

    public WriteText(String filepath){
	this.filepath = filepath;
    }

    public void writeToText(String input){
	try {
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filepath, true)));
	    out.println(input);
	    out.close();
	} catch (IOException e) {
	    //exception handling left as an exercise for the reader
	}
    }
}
