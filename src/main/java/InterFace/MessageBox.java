package InterFace;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import Program.Samolotoszczalec;
import Program.Window;

public class MessageBox {

	static List<MessageBox> messages = new ArrayList<>();

	String str, color = "green";
	int x, y;
	double czas, opacity = 255;
	int fontSize = 25;
	long time;
	boolean fadeOut = true;
	static Window win = Samolotoszczalec.win;
	boolean wysrodkuj = false;

	/**
	 * 
	 * @param str  - wiadomosc
	 * @param czas - w milisekundach, jak dlugo wiadomosc ma byc wyswietlana
	 */
	public MessageBox(String str, int czas, int x, int y) {
		this.x = x;
		this.y = y;
		this.str = str;
		this.czas = czas;
		this.time = System.currentTimeMillis();
		messages.add(this);
	}

	/**
	 * 
	 * @param str  - wiadomosc
	 * @param czas - w milisekundach, jak dlugo wiadomosc ma byc wyswietlana
	 */
	public MessageBox(String str, int czas, int x, int y, String color) {
		this(str, czas, x, y);
		this.color = color;
	}

	/**
	 * ten wyswietla napis caly czas
	 * 
	 * @param str - wiadomosc
	 * 
	 */
	@SuppressWarnings("static-access")
	public MessageBox(String str) {

		this.str = str;
		this.fadeOut = false;
		this.y = win.size_y / 2 - fontSize / 2;
		wysrodkuj = true;
		messages.add(this);
	}

	/**
	 * 
	 * @param str
	 * @param fontSize
	 */
	@SuppressWarnings("static-access")
	public MessageBox(String str, int fontSize) {

		this.fontSize = fontSize;
		this.str = str;
		this.fadeOut = false;
		this.x = win.size_x / 2 - str.length() * fontSize / 2;
		this.y = win.size_y / 2 - fontSize / 2;
		messages.add(this);

	}

	/**
	 * 
	 * @param str
	 * @param fontSize
	 * @param Color
	 */
	MessageBox(String str, int fontSize, String Color) {

		this(str, fontSize);
		this.color = Color;

	}

	public static void draw(Graphics2D g) {
		for (int i = messages.size() - 1; i >= 0; i--) {
			messages.get(i).drawMe(g);
		}
	}

	@SuppressWarnings("static-access")
	void drawMe(Graphics2D g) {
		if (color == "green" || color == "GREEN")
			g.setColor(new Color(0, 255, 0, (int) opacity));
		if (color == "RED" || color == "red")
			g.setColor(new Color(255, 0, 0, (int) opacity));
		if (color == "blue" || color == "BLUE")
			g.setColor(new Color(0, 0, 255, (int) opacity));

		g.setFont(new Font(null, Font.PLAIN, fontSize));
		if (wysrodkuj) {
			this.x = win.size_x / 2 - g.getFontMetrics().stringWidth(str) / 2;
		}
		g.drawString(str, x, y);

		motion();

	}

	void motion() {
		if (fadeOut) {
			double czasKtoryMinal = (double) (System.currentTimeMillis() - time);
			opacity = 255 - Math.sqrt(czasKtoryMinal / czas) * 255; // fadeOut nieliniowy
			y--;
		}

		if (opacity <= 0)
			messages.remove(this);
	}
	
	public static void restart()
	{
		messages.clear();
	}
	
	void aktualizujCzas(long t)
	{
		time+=t;
	}
	
	public static void aktualizujeCzasy(long t)
	{
		for(int i=0;i<messages.size();i++)
			messages.get(i).aktualizujCzas(t);
	}
}
