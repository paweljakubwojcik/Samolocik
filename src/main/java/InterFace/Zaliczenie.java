package InterFace;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import Bullets.Granade;
import Program.Window;
import napisyKoncowe.Credits;

/**
 * Klasa realizująca wyświetlenie ekranu zaliczenia
 * 
 * @author adrian
 *
 */
public class Zaliczenie {
	static ArrayList<Zaliczenie> list = new ArrayList<>();

	private long delay = 2000, begin = System.currentTimeMillis();

	private int opacity = 0;
	private int fadeIn = 10, fadeOut = 10;

	private BufferedImage wynik;
	private long czas = 2000;
	private int punkty;
	private String ocena;
	private static long start;

	private Color kolor;
	private Color bgKolor;
	private int szerPKT;
	private int opacityPKT = 0;
	private int opacityOcena = 0;

	private int xPunkty = 580, yPunkty = 445;
	private int xOcena = 580, yOcena = 545;

	private float alpha = (float) 0.0;
	private AlphaComposite ac;

	private boolean fajerwerki = false;
	private long startFajerwerki;
	private long jakDlugoFajerwerki = 8000;
	private boolean win = false;

	public static int restartx = 400, restarty = 200, restartsizex = 400, restartsizey = 100;
	public static boolean hover = false;

	private boolean bylRes = false;

	private boolean graczUmarl = false;;

	/**
	 * Inicjalizuje standardowe informacje
	 * 
	 */
	public Zaliczenie() {
		URL url = getClass().getResource("/images/Zaliczenie.png");
		try {
			wynik = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		punkty = 0;
		start = 0;
		list.add(this);
	}

	/**
	 * Inicjalizuje standarodwe informacje przez czas czasu
	 * 
	 * @param czas - ile ma być wyświetlana informacja
	 */
	public Zaliczenie(long czas) {
		this();
		this.czas = czas;
	}

	/**
	 * Inicjalizuje standardowe informacje
	 * 
	 * @param czas    - ile ma być wyświetlany ekran końcowy
	 * @param fadeIn  - jak szybko na 1 klatkę ekran ma się rozjaśniać
	 * @param fadeOut - jak szybko na 1 klatkę ekran ma się ściemniać
	 */
	public Zaliczenie(long czas, int fadeIn, int fadeOut) {
		this(czas);
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
	}

	/**
	 * Inicjalizuje ekran końcowy wystawiając ocenę na podstawie uzyskanych punktów
	 * 
	 * @param czas    - ile czasu ma być wyświetlany ekran końcowy
	 * @param fadeIn  - jak szybko na 1 klatkę ekran ma się rozjaśniać
	 * @param fadeOut - jak szybko na 1 klatkę ekran ma się ściemniać
	 * @param punkty  - ile punktów uzyskano
	 */
	public Zaliczenie(long czas, int fadeIn, int fadeOut, int punkty) {
		this(czas, fadeIn, fadeOut);
		this.punkty = punkty;
		try {
			ocena();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("\nPaszko hack your life");
		}
	}

	/**
	 * Inicjalizuje ekran końcowy wystawiając ocenę na podstawie uzyskanych punktów
	 * 
	 * @param czas       - ile czasu ma by� wy�wietlany ekran koncowy
	 * @param fadeIn     - jak szybko na 1 klatkę ekran ma się rozjaśniać
	 * @param fadeOut    - jak szybko na 1 klatkę ekran ma się ściemniać
	 * @param punkty     - ile punktów uzyskano
	 * @param graczUmarl - Czy gracz zyje?
	 */
	public Zaliczenie(long czas, int fadeIn, int fadeOut, int punkty, boolean win, boolean graczUmarl) {
		this(czas, fadeIn, fadeOut);
		this.punkty = punkty;
		this.win = win;
		this.fajerwerki = win;
		this.graczUmarl = graczUmarl;
		try {
			ocena();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("\nPaszko hack your life");
		}
	}

	public static void draw(Graphics2D g) {
		for (int i = 0; i < list.size(); i++)
			list.get(i).drawMe(g);
	}

	/**
	 * Rysuje Ekran Zaliczenia
	 * 
	 * @param g - Grafika do rysowania
	 */
	public void drawMe(Graphics2D g) {

		if (!fajerwerki) {
			if (System.currentTimeMillis() - begin > delay)
				alfa();

			ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			g.setComposite(ac);
			g.drawImage(wynik, 0, 0, null);

			// przycisk restart
			/*
			 * if (!hover) { g.setColor(Color.black); g.fillRect(restartx, restarty,
			 * restartsizex, restartsizey); g.setColor(Color.white); g.drawString("restart",
			 * restartx + restartsizex / 2, restarty + restartsizey / 2); } else {
			 * g.setColor(Color.white); g.fillRect(restartx, restarty, restartsizex,
			 * restartsizey); g.setColor(Color.black); g.drawString("restart", restartx +
			 * restartsizex / 2, restarty + restartsizey / 2); }
			 */

			if (System.currentTimeMillis() - start > 1000 && start != 0) {
				g.setFont(new Font(null, 0, 80));
				opacityOceny();

				if (opacityPKT > 0) {
					if (punkty <= 50) {
						kolor = new Color(0, 0, 0, opacityPKT);
						bgKolor = new Color(220, 10, 10, opacityPKT * 80 / 255);
					} else {
						kolor = new Color(0, 0, 0, opacityPKT);
						bgKolor = new Color(10, 220, 10, opacityPKT * 80 / 255);
					}
					g.setColor(bgKolor);
					g.fillRect(xPunkty - 5, yPunkty - 80, szerPKT, 90);
					g.setColor(kolor);
					g.drawString(String.valueOf(punkty), xPunkty, yPunkty);
				}

				if (opacityOcena > 0) {
					if (punkty <= 50) {
						kolor = new Color(0, 0, 0, opacityOcena);
						bgKolor = new Color(220, 10, 10, opacityOcena * 80 / 255);
					} else {
						kolor = new Color(0, 0, 0, opacityOcena);
						bgKolor = new Color(10, 220, 10, opacityOcena * 80 / 255);
					}
					g.setColor(bgKolor);
					g.fillRect(xOcena - 5, yOcena - 80, 140, 90);
					g.setColor(kolor);
					g.drawString(ocena, xOcena, yOcena);
				}

				g.setColor(new Color(0, 0, 0, opacityPKT));
				g.drawRect(xPunkty - 5, yPunkty - 80, szerPKT, 90);
				g.setColor(new Color(0, 0, 0, opacityOcena));
				g.drawRect(xOcena - 5, yOcena - 80, 140, 90);
			}

			ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
			g.setComposite(ac);
		} else if (startFajerwerki == 0) {
			startFajerwerki = System.currentTimeMillis();
		}

		if (fajerwerki && win) {
			fajerwerki(g);
		}

		if (System.currentTimeMillis() - startFajerwerki > jakDlugoFajerwerki) {
			fajerwerki = false;
		}

	}

	private void alfa() {
		if (opacity < 255 && start == 0) {
			opacity += fadeIn;
			if (opacity > 255)
				opacity = 255;
		} else if (opacity >= 255 && start == 0) {
			opacity = 255;
			start = System.currentTimeMillis();
		} else if (System.currentTimeMillis() - start > czas && opacity > 0) {
			if (!bylRes && graczUmarl) {
				new Restart("Odmalować?");
				bylRes = !bylRes;
			} else if (!bylRes && !graczUmarl) {
				new Credits();
				bylRes = !bylRes;
			}
			opacity -= fadeOut;
			if (opacity < 0)
				opacity = 0;
		} else if (opacity == 0) {
			try {
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		alpha = (float) ((float) (opacity) / 255.0);
	}

	private void ocena() throws Exception {
		if (punkty >= 0) {
			if (punkty <= 50) {
				ocena = "2.0";
			} else if (punkty <= 60) {
				ocena = "3.0";
			} else if (punkty <= 70) {
				ocena = "3.5";
			} else if (punkty <= 80) {
				ocena = "4.0";
			} else if (punkty <= 90) {
				ocena = "4.5";
			} else if (punkty <= 100) {
				ocena = "5.0";
			} else {
				ocena = "1.0";
				throw new Exception("Hack LIFE");
			}
		} else {
			ocena = "1.0";
			throw new Exception("Hack LIFE");
		}
		if (punkty < 10) {
			szerPKT = 60;
		} else if (punkty < 100) {
			szerPKT = 110;
		} else if (punkty == 100) {
			szerPKT = 160;
		}
	}

	private void opacityOceny() {
		if (opacityPKT < 255) {
			opacityPKT += 3;
		} else if (opacityPKT >= 255) {
			if (opacityOcena < 255) {
				opacityOcena += 3;
			}
		}
		if (opacityPKT > 255) {
			opacityPKT = 255;
		}
		if (opacityOcena > 255) {
			opacityOcena = 255;
		}
	}

	private void fajerwerki(Graphics2D g) {
		Random rand = new Random();
		if (rand.nextInt(100) > 90)
			new Granade(rand.nextInt(Window.size_x), rand.nextInt(100) + 500, true);

	}

	public static void wylancz() {
		list.clear();
	}

	public static boolean isEmpty() {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getClass() == Zaliczenie.class)
				return false;
		}
		return true;
	}

	public static void aktualizujCzas(long czasPauzy) {
		start += czasPauzy;
	}

}
