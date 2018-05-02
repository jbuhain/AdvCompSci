import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.util.ArrayList;
import java.util.ListIterator;

//forimage
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.Font;
import java.awt.BorderLayout;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Iterator;
import java.util.*;
import java.net.*;

import javax.swing.JFrame;

import javax.swing.ImageIcon;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.net.*;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ClientScreen extends JPanel implements ActionListener{ 
	private JTextField msgTextfield;
	private JTextArea chatView;
	private JScrollPane chatViewPaneSet;
	private boolean firstLogIn;
	private String username;
	private String msg;
	
	private String hostName;
	private int portNumber;
	private Socket serverSocket;
	private BufferedReader in;
	private PrintWriter out;
	
	private PushbackInputStream inObj;		
	private ObjectInputStream inObjREAL;	
	private ObjectOutputStream outObj;	
	private Player p1; //don't actually draw this
	private Input input;
	private ArrayList<Player> playerList;
    public ClientScreen() throws IOException{
        this.setLayout(null);
        
		firstLogIn = true;
		chatView = new JTextArea(400,300);
		username = "";
		chatViewPaneSet = new JScrollPane(chatView); 
        chatViewPaneSet.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatViewPaneSet.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatViewPaneSet.setBounds(0,5,400,300);
		this.add(chatViewPaneSet);
		msg = "Enter username and hit enter." + "\n";
		chatView.setText(msg);
		
		playerList = new ArrayList<Player>();
		
		hostName = "localhost";
        portNumber = 444;
        serverSocket = new Socket(hostName,portNumber);
        
		inObj = new PushbackInputStream(serverSocket.getInputStream());		
		inObjREAL = new ObjectInputStream(serverSocket.getInputStream());	
		outObj = new ObjectOutputStream(serverSocket.getOutputStream());
		
        in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
		out = new PrintWriter(serverSocket.getOutputStream(),true);
		msgTextfield = new JTextField(30);
        msgTextfield.setBounds(0,330, 500, 40);
		this.add(msgTextfield);
		msgTextfield.addActionListener(this);
		this.setFocusable(true);
		
		input = new Input(this);
    }
	public Dimension getPreferredSize() {
        //Sets the size of the panel
        return new Dimension(1280,780);
		
    }
	public void paintComponent(Graphics g){
        //draw background
		super.paintComponent(g);
		g.setColor(Color.white);
        g.fillRect(0,0,1280,780);
		
		for(int i = 0; i<playerList.size();i++){
			playerList.get(i).drawMe(g);
		}
		
		if(!firstLogIn){
			
			try{
				System.out.println(inObj.available());
				while(inObj.available()>0){
					Player temp = (Player) inObjREAL.readObject(); //make sure client constantly sends player information, vice versa
					//set Player(1) values here::::
					temp.drawMe(g);
					System.out.println("DRAWING");
					//set boolean values later
					
					if(!contains(temp.getIDCode())){
						playerList.add(temp);
					}
					else{
						getPlayer(temp.getIDCode()) = temp;
					}
				}
				
				
				
				outObj.reset();
				outObj.writeObject(p1);
				System.out.println("moving down");
			} catch (ClassNotFoundException e) {
				System.err.println("Class does not exist" + e);
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Couldn't get I/O for the connection to");
				System.exit(1);
			}
			
			
			
		}
		repaint();
	}
	public Player getPlayer(int idCode){
		for(int i = 0; i<playerList.size();i++){
			if(playerList.get(i).getIDCode()==idCode){
				return playerList.get(i);
			}
		}
		return null;
	}
	public boolean contains(int idCode){
		for(int i = 0; i<playerList.size();i++){
			if(playerList.get(i).getIDCode()==idCode){
				return true;
			}
		}
		return false;
	}
	public void setup() throws IOException{
        while(true){
			try {
				Thread.sleep(10);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			if(!firstLogIn){
				if (Input.keyboard[87]) {
					p1.moveUp();
					System.out.println("moving up");
				}
				if (Input.keyboard[83]) {
					p1.moveDown();
					System.out.println("moving down");
				}
				repaint();
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		
		if(firstLogIn){
			username = msgTextfield.getText();
			firstLogIn = false;
			msgTextfield.setText("");
			playerList.add(new Player(800,50,username));
			remove(msgTextfield);
		}
		else{
			/*
			out.println(username + ": " + msgTextfield.getText());
			msgTextfield.setText("");
			*/
		}
		repaint();
    }
	
}
