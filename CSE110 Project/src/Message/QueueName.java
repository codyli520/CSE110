package Message;

public class QueueName{
	//mode 1 POST
	//mode 2 REPLY TO A USER
	//mode 3 REPLY TO AN ARTICLE
  public static String queuename(int id, int mode){
	  
    if (mode==3)  
     return Integer.toString(id)+"Post";
	if (mode==2)
	 return Integer.toString(id);
	if (mode==3)
	 return Integer.toString(id)+"Reply";
	return null;
  } 		
}
