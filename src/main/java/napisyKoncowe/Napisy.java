package napisyKoncowe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Program.Window;

public class Napisy {
	static ArrayList<Napisy> nap = new ArrayList<Napisy>();
	public static float tempo = (float) 0.5;

	private String wiadomosc;
	private float y;

	private boolean Tytul;

	public Napisy(String wiadomosc, boolean Tytul) {
		this.y = Window.size_y + 50;
		this.wiadomosc = wiadomosc;
		this.Tytul = Tytul;
		nap.add(this);
	}

	public static void draw(Graphics2D g) {
		for (int i = 0; i < nap.size(); i++) {
			if (nap.size() != 0) {
				nap.get(i).drawMe(g);
			}
		}
	}

	private void drawMe(Graphics2D g) {
		myMotion();
		if (!Tytul)
			g.setColor(Color.WHITE);
		else
			g.setColor(new Color(100, 255, 255));
		if (Tytul)
			g.setFont(new Font(null, 0, 35));
		else
			g.setFont(new Font(null, 0, 25));
		g.drawString(wiadomosc, Window.size_x / 2 - g.getFontMetrics().stringWidth(wiadomosc) / 2, y);
	}

	private void myMotion() {
		y -= tempo;
		if (y < 0) {
			nap.remove(this);
			try {
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

}
