package Message;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
 
public class TextReader {
	
	File file;
	InputStreamReader read;
	BufferedReader bufferedReader;
	
	public TextReader(File newFile) throws FileNotFoundException{
		if(newFile.isFile() && newFile.exists()){
			file = newFile;
			read = new InputStreamReader(new FileInputStream(file));
			bufferedReader = new BufferedReader(read);
		}
		else{
			throw new FileNotFoundException();
		}
	}
	
	public boolean readTxtFile(){
		String lineTxt = null;
		try{
			while((lineTxt = bufferedReader.readLine()) != null){
				System.out.println(lineTxt);
			}
			read.close();
		}
		catch(Exception e){
			return false;
		}
		return true;
    }     
}



