import java.io.Serializable;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.BorderLayout;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Player implements Serializable{
	private int x; 
	private int y;
	private String username;
	private String idCode;
	public Player(int x, int y, String username){
		this.x = x;
		this.y = y;
		this.username = username;
		idCode = "";
	}
	public void drawMe(Graphics g){
		g.setColor(Color.GREEN);
		g.fillRect(x,y,100,100);
		g.setColor(Color.BLACK);
		g.drawString(username,x,y+120);
	}
	public String getIDCode(){
		return idCode;
	}
	public void setIDCode(String id){
		this.idCode = id;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void moveRight(){
		x++;
	}
	public void moveLeft(){
		x--;
	}
	public void moveUp(){
		y--;
	}
	public void moveDown(){
		y++;
	}
}