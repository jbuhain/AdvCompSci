

import java.io.*;
import java.net.*;

public class Server{

  public static void main(String[] args){
    
    Manager m = new Manager();
    int portNumber = 444;
    int referenceNum = 1000;
	try{
		ServerSocket serverSocket = new ServerSocket(portNumber);
		 while(true){
		  System.out.println("Waiting for Connection");
		  Socket clientSocket = serverSocket.accept();
		  referenceNum++;
		  ServerThread st = new ServerThread(clientSocket,m,referenceNum);
		  Thread thread = new Thread(st);
		  thread.start();
		  m.addThread(st);
		}
	} catch (IOException ex){
		System.out.println("Error listening for a connection");
		System.out.println(ex.getMessage());
	}
	
   
    
  }
}
