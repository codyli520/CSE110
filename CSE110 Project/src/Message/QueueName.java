package Message;

public class QueueName{
	//mode 1  ������
	//mode 2 �ظ�������
	//mode 3 ��������
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
