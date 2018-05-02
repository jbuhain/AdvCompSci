

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Manager{
  private ArrayList<ServerThread> threadList;
  
  public Manager(){
    threadList = new ArrayList<ServerThread>();
  }
  
  public void broadcast(Player temp){
	  System.out.println(threadList.size());
	  for(int i = 0; i<threadList.size();i++){
		  threadList.get(i).send(temp);
	  }
    
  }
  
  public synchronized void addThread(ServerThread t){
    threadList.add(t);
  }
  
  public synchronized void removeThread(ServerThread t){
    threadList.remove(t);
  }
  
}













