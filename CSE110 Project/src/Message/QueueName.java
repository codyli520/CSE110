package Message;

public class QueueName{
	//mode 1  发文章
	//mode 2 回复给别人
	//mode 3 评论文章
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
