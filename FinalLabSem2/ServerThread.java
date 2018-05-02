
import java.net.*;
import java.io.*;

public class ServerThread implements Runnable{
    private Socket clientSocket;
    private Manager m1;
    private PrintWriter out; //not using this
  
	private ObjectOutputStream outObj;
	private PushbackInputStream inObj;
	private ObjectInputStream inObjREAL;
	private String threadCode;
	
    public ServerThread(Socket clientSocket, Manager m1, int referenceNum){
        this.clientSocket = clientSocket;
		this.m1 = m1;
        this.threadCode = String.valueOf(referenceNum);
		
		try{
              out = new PrintWriter(clientSocket.getOutputStream(), true);
			  outObj = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex){
            m1.removeThread(this);
            System.out.println("Error listening for a connection");
            System.out.println(ex.getMessage());
        }
    }
    public void run(){
        System.out.println(Thread.currentThread().getName() + ": connection opened");
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //not using rn
            inObj = new PushbackInputStream(clientSocket.getInputStream());		
			inObjREAL = new ObjectInputStream(clientSocket.getInputStream());

            while(true){
                if(inObj.available()>0){
					try{
						Player temp = (Player) inObjREAL.readObject();
						temp.setIDCode(threadCode);
						//System.out.println("RUN AT SERVERTHREAD");
						//System.out.println(temp.toString());
						m1.broadcast(temp);
					}catch (ClassNotFoundException e) {
						System.err.println("Class does not exist" + e);
						System.exit(1);
					}catch (IOException ex){
						m1.removeThread(this);
						System.out.println("Error listening for a connection");
						System.out.println(ex.getMessage());
					}
                    
                }
  
            }            
            
            
            //out.close();
            //System.out.println(Thread.currentThread().getName() + ": connection closed.");
        } catch (IOException ex){
            m1.removeThread(this);
            System.out.println("Error listening for a connection");
            System.out.println(ex.getMessage());
        }
    }
  
    
    public void send(Player temp){
		//System.out.println("Sending " + msg);
		//outObj.println(temp);
		try{
			outObj.reset();
			outObj.writeObject(temp);
		}catch (IOException ex){
			m1.removeThread(this);
			System.out.println("Error listening for a connection");
			System.out.println(ex.getMessage());
		}
    }
    
    
    
    
}