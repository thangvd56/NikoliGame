package gameSlitherlink;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Cell extends Canvas{
	
	private Beside canh1;
	private Beside canh2;
	private Beside canh3;
	private Beside canh4;
	private int index;
	private int size = 39;
	private boolean hide1 = false;
	private boolean hide2 = false;
	private boolean hide3 = false;
	private boolean hide4 = false;
	public Cell(){
		setBackground(Color.WHITE);
		canh1 = new Beside(0,0,size,0);
		canh2 = new Beside(size,0,size,size);
		canh3 = new Beside(size,size,0,size);
		canh4 = new Beside(0,size,0,0);
	}
	public void setIndex(int index){
		this.index = index;
	}
	public void setHide(boolean hide1, boolean hide2, boolean hide3, boolean hide4){
		this.hide1 = hide1;
		this.hide2 = hide2;
		this.hide3 = hide3;
		this.hide4 = hide4;
	}
	public void paint(Graphics g){
		//g.drawRect(0, 0, 20, 20);
		//g.setColor(Color.GREEN);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		if(hide1 == true)g.drawLine(canh1.x1, canh1.x2, canh1.x3, canh1.x4);
		if(hide2 == true)g.drawLine(canh2.x1, canh2.x2, canh2.x3, canh2.x4);
		if(hide3 == true)g.drawLine(canh3.x1, canh3.x2, canh3.x3, canh3.x4);
		if(hide4 == true)g.drawLine(canh4.x1, canh4.x2, canh4.x3, canh4.x4);
		g.drawRect(canh1.x1, canh1.x2, 1, 1);
		g.drawRect(canh2.x1, canh2.x2, 1, 1);
		g.drawRect(canh3.x1, canh3.x2, 1, 1);
		g.drawRect(canh4.x1, canh4.x2, 1, 1);
		if(index<5) g.drawString(String.valueOf(index), size /2-3, size/2+10);
	}
}
