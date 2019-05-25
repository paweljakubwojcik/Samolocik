package InterFace;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Program.Samolotoszczalec;
import Program.Window;

public class Sterowanie {
	static int startopacity = 20;
	static int opacity = startopacity;
	private static final int Maxopacity = 100;
	private static boolean opacityForPause = false;
	private static boolean opacityForPomin = false;
	static Window win = Samolotoszczalec.win;
	static long start = 0;
	private static final int czas = 2000;
	private static boolean fadeOut = false;
	private static short instrukcji = 0;
	private static int rK = 255, gK = 255, bK = 100;

	private static int liczbainstrukcji = 4; // Trzeba ustawić jeśli dodaje się nowe instrukcje

	public static void draw(Graphics2D g) {
		logika();
		przezroczystosc();
		g.setColor(new Color(rK, gK, bK, opacity));
		if (!win.isIntro()) {
			PominDraw(g);
		}
		switch (instrukcji) {
		case 0:
			MoveDraw(g);
			break;
		case 1:
			ShotDraw(g);
			PauseDraw(g);
			break;
		case 2:
			SupplyDraw(g);
			PauseDraw(g);
			break;
		case 3:
			AmmoDraw(g);
			PauseDraw(g);
			break;

		}

	}

	private static void logika() {
		if (instrukcji == liczbainstrukcji) {
			win.setInstrukcja(false);
			win.setSterowanie(false);
		}
	}

	private static void AmmoDraw(Graphics2D g) {
		int y = 300;
		for (int x = 0; x < 5; x++) {
			g.drawRect(x * 50 + 50, y, 45, 45);
			g.drawRect(x * 50 + 50 + 2, y + 2, 41, 41);
			String tekst = String.valueOf(x + 1);
			g.setFont(new Font(null, 0, 40));
			g.drawString(tekst, x * 50 + 57, y + 35);
		}
		g.setFont(new Font(null, 0, 15));
		g.drawString("Użyj klawiszy numerycznych, aby przełączać", 57, y + 75);
		g.drawString("się między typami amunicji", 57, y + 95);
	}

	private static void MoveDraw(Graphics2D g) {
		g.setFont(new Font(null, 0, 25));
		g.drawString("Użyj strzałek, aby się poruszać", 150, 400);
		int x = 560, y = 350;
		g.setFont(new Font(null, 0, 40));
		g.drawRect(x + 50, y, 45, 45);
		g.drawRect(x + 50 + 2, y + 2, 41, 41);
		g.drawString("\u2191", x + 50 + 7, y + 35);
		g.drawRect(x, y + 50, 45, 45);
		g.drawRect(x + 2, y + 52, 41, 41);
		g.drawString("\u2190", x + 7, y + 52 + 35);
		g.drawRect(x + 50, y + 50, 45, 45);
		g.drawRect(x + 50 + 2, y + 52, 41, 41);
		g.drawString("\u2193", x + 50 + 7, y + 52 + 35);
		g.drawRect(x + 100, y + 50, 45, 45);
		g.drawRect(x + 100 + 2, y + 52, 41, 41);
		g.drawString("\u2192", x + 100 + 7, y + 52 + 35);
	}

	private static void ShotDraw(Graphics2D g) {
		g.setFont(new Font(null, 0, 25));
		g.drawString("Naciśnij", 150, 400);

		int x = 260, y = 360;
		g.setFont(new Font(null, 0, 40));
		g.drawRect(x, y, 45, 45);
		g.drawRect(x + 2, y + 2, 41, 41);
		g.drawString("Z", x + 7, y + 35);

		g.setFont(new Font(null, 0, 25));
		g.drawString("aby strzelać", x + 60, 400);
	}

	private static void SupplyDraw(Graphics2D g) {
		g.setFont(new Font(null, 0, 25));
		g.drawString("Zbieraj dropy aby uzyskać bonusy w grze", 150, 400);
	}

	private static void przezroczystosc() {
		if (start == 0) {
			start = System.currentTimeMillis();
		}
		if (System.currentTimeMillis() - start > czas) {
			fadeOut = true;
		}
		if (opacity < Maxopacity && opacity >= 0 && !fadeOut) {
			opacity++;
		} else if (fadeOut && opacity > 0) {
			opacity--;
			if (opacity == 0) {
				instrukcji++;
				start = 0;
				opacity = startopacity;
				fadeOut = false;
			}
		}
	}

	public static void PominDraw(Graphics2D g) {
		g.setColor(new Color(rK, gK, bK, Maxopacity));
		if (instrukcji + 1 == liczbainstrukcji && opacity == Maxopacity) {
			opacityForPomin = true;
		}
		if (opacityForPomin == true) {
			g.setColor(new Color(rK, gK, bK, opacity));
		}
		g.setFont(new Font(null, 0, 20));
		g.drawString("Naciśnij 'S' aby pominąć", 50, 570);
		g.setColor(new Color(rK, gK, bK, opacity));
	}

	private static void PauseDraw(Graphics2D g) {
		if (instrukcji == 1 && !opacityForPause) {
			g.setColor(new Color(rK, gK, bK, opacity));
			if (opacity == Maxopacity) {
				opacityForPause = true;
			}
		} else if (instrukcji + 1 == liczbainstrukcji && opacity == Maxopacity) {
			opacityForPause = false;
		} else if (instrukcji != liczbainstrukcji && opacityForPause) {
			g.setColor(new Color(rK, gK, bK, Maxopacity));
		} else {
			g.setColor(new Color(rK, gK, bK, opacity));
		}
		g.setFont(new Font(null, 0, 20));
		g.drawString("Naciśnij 'P' aby zapauzować", 470, 570);
		g.setColor(new Color(rK, gK, bK, opacity));
	}

}
