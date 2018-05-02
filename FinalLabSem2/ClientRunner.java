import java.util.*;
import java.net.*;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import java.io.*;
public class ClientRunner {

    public static void main(String args[]) throws IOException{

        JFrame frame = new JFrame("Client");

        ClientScreen sc = new ClientScreen();
        frame.add(sc);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
		
		sc.setup(); //change this later
    }
}
