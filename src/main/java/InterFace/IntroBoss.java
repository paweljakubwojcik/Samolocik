package InterFace;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Program.Window;

/**
 * Wyswietla ekran przed Bossem
 * 
 * @author adrian
 *
 */
public class IntroBoss {
	public static ArrayList<IntroBoss> list = new ArrayList<>();
	public static boolean end = false;

	private static int opacity = 0;
	private int fadeIn = 10, fadeOut = 10;

	private BufferedImage paszkoKolor, paszkoBlack;
	private BufferedImage[] superAtak = new BufferedImage[3];
	private BufferedImage[] superAtakObraz = new BufferedImage[3];
	private long czas = 2000;
	private static long start = 0;

	private long dlugoscInfoPomin;

	private boolean czyPominWyswietla = false;

//	private Color kolor;
//	private Color bgKolor;

	private float alpha = (float) 0.0;
	private AlphaComposite ac;

	private String tytolPaszko = "PROFESOR PASZKOWSKI";

	private String[] Opisy = { "Laserowe Oczy", "Proste Rownolegle", "Proste Prostopadle" };

	private int[] obrazkiRuchX = new int[3];

	private int przezPomin = 255;

	public static boolean czyMoznaPominac = false;

	/**
	 * Inicjalizacja podstawowych zmiennych
	 */
	public IntroBoss() {
		URL[] url = { getClass().getResource("/images/paszko.png"), getClass().getResource("/images/paszkoBlack2.png"),
				getClass().getResource("/images/superAtak0.png"), getClass().getResource("/images/superAtak1.png"),
				getClass().getResource("/images/superAtak2.png") };
		try {
			paszkoKolor = ImageIO.read(url[0]);
			paszkoBlack = ImageIO.read(url[1]);
			superAtak[0] = ImageIO.read(url[2]);
			superAtak[1] = ImageIO.read(url[3]);
			superAtak[2] = ImageIO.read(url[4]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 3; i++) {
			obrazkiRuchX[i] = Window.size_x + 1;
		}

		zlozObrazki();
		list.add(this);

		czyMoznaPominac = true;
	}

	/**
	 * Inicjalizuje przebieg wyswietlania klatki
	 * 
	 * @param czas    - ile czasu ma być wysietlany
	 * @param fadeIn  - jak szybko na 1 klatke ekran ma sie rozjasniac
	 * @param fadeOut - jak szybko na 1 klatke ekran ma sie sciemniac
	 */
	public IntroBoss(long czas, int fadeIn, int fadeOut) {
		this();
		this.czas = czas;
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
	}

	public static void draw(Graphics2D g) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).drawMe(g);
		}
	}

	public void drawMe(Graphics2D g) {
		if (opacity >= 0) {
			ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (opacity / 255.0));
			g.setComposite(ac);
		}

		if (opacity <= 255 && start == 0) {
			opacity += fadeIn;
		}
		if (opacity >= 255 && start == 0) {
			opacity = 255;
			start = System.currentTimeMillis();
		}

		if (System.currentTimeMillis() - start >= czas && start != 0) {
			opacity -= fadeOut;
		}
		if (opacity <= 0) {
			end = true;
			opacity = 0;
			try {
				alpha = 0;
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, Window.size_x, Window.size_y);

		g.setColor(new Color(0, 0, 0));
		g.setFont(new Font(null, 0, 50));
//		g.fillRect(Window.size_x / 2 - g.getFontMetrics().stringWidth(tytolPaszko) / 2, 80 - 50,
//				g.getFontMetrics().stringWidth(tytolPaszko), 80);

		g.drawImage(paszkoBlack, Window.size_x / 2 - paszkoBlack.getWidth() / 2, 120, null);

		if (System.currentTimeMillis() - start > 1000 && start != 0) {
			alpha += 0.02;
			if (alpha > 1) {
				alpha = 1;
			}

			if (obrabianyObrazek < 3 && alpha == 1)
				motion();

			g.drawImage(superAtakObraz[0], obrazkiRuchX[0], 320, null);
			g.drawImage(superAtakObraz[1], obrazkiRuchX[1], 320, null);
			g.drawImage(superAtakObraz[2], obrazkiRuchX[2], 320, null);

			if (opacity == 255) {
				ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
				g.setComposite(ac);
			}
			g.drawString(tytolPaszko, Window.size_x / 2 - g.getFontMetrics().stringWidth(tytolPaszko) / 2, 80);
			g.drawImage(paszkoKolor, Window.size_x / 2 - paszkoBlack.getWidth() / 2, 120, null);

			g.setFont(new Font(null, 0, 30));
			g.drawString("SUPER ATAKI", Window.size_x / 2 - g.getFontMetrics().stringWidth("SUPER ATAKI") / 2, 300);

		}
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
		g.setComposite(ac);
//		g.drawRect(20, 300, 240, 150);
//		g.drawRect(280, 300, 240, 150);
//		g.drawRect(540, 300, 240, 150);

//		g.drawRect(20, 320, 240, 250);
//		g.drawRect(280, 320, 240, 250);
//		g.drawRect(540, 320, 240, 250);

		if (!czyPominWyswietla) {
			dlugoscInfoPomin = System.currentTimeMillis();
			czyPominWyswietla = !czyPominWyswietla;
		} else if (czyPominWyswietla && System.currentTimeMillis() - dlugoscInfoPomin < 3000) {
			pominDraw(g);
		} else if (czyPominWyswietla && System.currentTimeMillis() - dlugoscInfoPomin < 4000) {
			przezPomin -= 10;
			if (przezPomin < 0)
				przezPomin = 0;
			pominDraw(g);
		}
	}

	private void pominDraw(Graphics2D g) {
		g.setColor(new Color(0, 0, 10, przezPomin));
		g.setFont(new Font(null, 0, 20));
		g.drawString("Naciśnij 'S' aby pominąć", 50, 570);
		g.setColor(new Color(0, 0, 10, opacity));
	}

	private void zlozObrazki() {
		superAtakObraz[0] = new BufferedImage(240, 250, BufferedImage.TYPE_INT_RGB);
		superAtakObraz[1] = new BufferedImage(240, 250, BufferedImage.TYPE_INT_RGB);
		superAtakObraz[2] = new BufferedImage(240, 250, BufferedImage.TYPE_INT_RGB);
		Graphics2D[] obrazki = { (Graphics2D) superAtakObraz[0].getGraphics(),
				(Graphics2D) superAtakObraz[1].getGraphics(), (Graphics2D) superAtakObraz[2].getGraphics() };
		for (int i = 0; i < 3; i++) {
			obrazki[i].setColor(Color.WHITE);
			obrazki[i].fillRect(0, 0, 240, 250);
			obrazki[i].setColor(Color.BLACK);
			obrazki[i].drawRect(0, 0, 240 - 1, 250 - 1);
			obrazki[i].drawImage(superAtak[i], 0, 0, null);

			obrazki[i].setFont(new Font(null, 0, 25));
			obrazki[i].drawString(Opisy[i],
					superAtakObraz[i].getWidth() / 2 - obrazki[i].getFontMetrics().stringWidth(Opisy[i]) / 2, 220);
		}

	}

	private int[] Xy = { 20, 280, 540 };
	private int obrabianyObrazek = 0;
	private long StartObrazka;

	private void motion() {
		if (obrazkiRuchX[obrabianyObrazek] > Xy[obrabianyObrazek]) {
			obrazkiRuchX[obrabianyObrazek] -= 8;
		}

		for (int i = 0; i < 3; i++) {
			if (obrazkiRuchX[i] < Xy[i]) {
				StartObrazka = System.currentTimeMillis();
				obrazkiRuchX[i] = Xy[i];

			}
		}
		if (System.currentTimeMillis() - StartObrazka > 750 && obrazkiRuchX[obrabianyObrazek] <= Xy[obrabianyObrazek]) {
			obrabianyObrazek++;
		}
	}

	public static void wylacz() {
		opacity = 0;
		start = 10;
	}

}
