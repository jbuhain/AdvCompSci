import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MyReader implements Runnable{
  
  private String message;
  private Scanner scan;
  private String username;
  private boolean messageReceived;
  public MyReader(){
    message = null;
    scan = new Scanner(System.in);
	messageReceived = false;
  }
  public String getUsername(){
	  String username = scan.nextLine();
	  return username;
  }
  public void run(){
	  
	while(true){
		scan.reset();
		if(!messageReceived){
			message = scan.nextLine();
			if(message!=null){
				messageReceived = true;
			}
		}
	}
  }
  public boolean messageAvailable(){
      return messageReceived;
  }
  public synchronized String get(){
    String temp = message;
	//System.out.println("Message: " + temp);
	messageReceived = false;
    return temp;
  }
  
  
}