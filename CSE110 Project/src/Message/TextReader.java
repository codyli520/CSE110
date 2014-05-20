package Message;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
 
public class TextReader {


	public static void readTxtFile(File file){
        try {
              
               
                if(file.isFile() && file.exists()){ 
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file));
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        System.out.println(lineTxt);
                    }
                    read.close();
        }
        } catch (Exception e) {
          
        }     
    }     
}

