
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class MessageBox {

	static List<MessageBox> messages = new ArrayList<>();

	String str;
	int x,y;
	double czas, opacity=255;
	long time;
	boolean fadeOut=true;
	static Window win=Samolotoszczalec.win;
	
	
	/**
	 * 
	 * @param str - wiadomosc
	 * @param czas - w milisekundach, jak dlugo wiadomosc ma byc wyswietlana
	 */
	MessageBox(String str, int czas,int x, int y) {
		this.x=x;
		this.y=y;
		this.str=str;
		this.czas=czas;
		this.time=System.currentTimeMillis();
		messages.add(this);
	}
	
	/**
	 * ten wyswietla napis caly czas
	 * @param str - wiadomosc
	 * 
	 */
	@SuppressWarnings("static-access")
	MessageBox(String str) {

		this.str=str;
		this.fadeOut=false;
		this.x=win.size_x/2;
		this.y=win.size_y/2;
		messages.add(this);
	}
	
	static void draw(Graphics2D g)
	{
		for (int i = messages.size() - 1; i >= 0; i--) {
			messages.get(i).drawMe(g);
		}
	}
	
	void drawMe(Graphics2D g)
	{
		g.setColor(new Color(0,255,0,(int)opacity));
		g.setFont(new Font(null, Font.PLAIN, 25));
		g.drawString(str,x,y);
		
		motion();
		
	}
	
	
	void motion()
	{
		if(fadeOut){
			double czasKtoryMinal = (double)(System.currentTimeMillis()-time);
			opacity = 255 - Math.sqrt(czasKtoryMinal/czas)*255;
			y--;
			}
			
			if(opacity<=0) messages.remove(this);
	}
	
}
