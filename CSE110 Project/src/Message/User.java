package Message;


public class User {
    
    private TextPublisher textpublisher;
    private TextReceiver textreceiver;
    private int userid;
    private String username;
    private String password;
    
    public User(int in_userid, String in_username,String in_password){
	
	userid = in_userid;
	username = in_username;
	password = in_password;
    }
    
    public User(){
	
	userid = 0;
	username = null;
	password = null;
	
    }
    
    public void setUsername (String in_username){
	
	username = in_username;
	
    }
    
    public String getUsername(){
	
	return username;
    }
    
    public void setPassword(String in_password){
	password = in_password;
    }
    
    public String getPassword(){
	
	return password;
    }
    
    public void publishMessage(String topic, String filepath){
	
	if(topic != null){
		System.out.println("init topic: "+topic);
		textpublisher = new TextPublisher(topic,filepath);
	    }
	    
	else{
		textpublisher = new TextPublisher();
	    }
	    
	
	this.textpublisher.publishMessage();
	System.out.println("message posted");
    }
    
    public void receiveMessage(String topic){
	
	try{
	    if(topic != null){
		textreceiver = new TextReceiver(topic);
	    }
	    else{
		textreceiver = new TextReceiver();
	    }
	}
	catch(Exception e){
	    System.err.println("text receiver construction fails");
	}
	
	this.textreceiver.readMessage();
	System.out.println("message received");
    }
    
}
