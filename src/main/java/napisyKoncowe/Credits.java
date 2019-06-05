package napisyKoncowe;

import java.awt.Graphics2D;
import java.util.Random;

import Bullets.Bullet;
import Program.Window;
import menu.MenuGlowne;

public class Credits {
	// static ArrayList<Credits> credits = new ArrayList<Credits>();

	private static boolean active = false;
	private static long czas;
	public static float fade = 0;

	private static final int tyleJest = 100; // ta liczba musi byÄ‡ rĂłwna
												// liczbie wiadomoĹ›ci
	private static String[] wiadomosci = new String[tyleJest];
	private static long[] czasWejscia = new long[tyleJest];
	private static boolean[] czyTytul = new boolean[tyleJest];
	private static boolean[] czyWyswietlono = new boolean[tyleJest];
	private static final int czasPoLinijce = 1000 * 2;
	private static final int czasOdstepu = 3500 * 2;
	private static final int czasPomiedzy = 2000 * 2;

	private static int indexWiadomosci = 0;

	private static boolean bylMax = false;

	public static Random rand = new Random();

	private static boolean powrotDoMenu;

	public Credits() {
		czas = System.currentTimeMillis();
		powrotDoMenu = false;
		active = true;
		bylMax = false;
		fade = 0;
		for (int i = 0; i < tyleJest; i++) {
			czyWyswietlono[i] = false;
		}
		indexWiadomosci = 0;

		wpiszDane("NIEWDZIĘCZNA PRZESTRZEŃ", 1000, true);

		wpiszDane("Zarządcy projektu", czasOdstepu, true);
		wpiszDane("Adrian Burakowski", czasPoLinijce, false);
		wpiszDane("Paweł Wójcik", czasPoLinijce, false);

		wpiszDane("Programiści", czasOdstepu, true);
		wpiszDane("Adrian Burakowski", czasPoLinijce, false);
		wpiszDane("Paweł Wójcik", czasPoLinijce, false);

		wpiszDane("Muzyka", czasOdstepu, true);
		wpiszDane("Paweł Wójcik", czasPoLinijce, false);

		wpiszDane("Grafika", czasOdstepu, true);
		wpiszDane("Asteroidy Alieny", czasPomiedzy, true);
		wpiszDane("Paweł Wójcik", czasPoLinijce, false);
		wpiszDane("Samolocik", czasPomiedzy, true);
		wpiszDane("Adrian Burakowski", czasPoLinijce, false);
		wpiszDane("Intro", czasPomiedzy, true);
		wpiszDane("Paweł Wójcik", czasPoLinijce, false);
		wpiszDane("Cinematics i Ekran Końcowy", czasPomiedzy, true);
		wpiszDane("Adrian Burakowski", czasPoLinijce, false);

		wpiszDane("Sztuczna inteligencja", czasOdstepu, true);
		wpiszDane("Alieny", czasPomiedzy, true);
		wpiszDane("Paweł Wójcik", czasPoLinijce, false);
		wpiszDane("Boss", czasPomiedzy, true);
		wpiszDane("Adrian Burakowski", czasPoLinijce, false);

		wpiszDane("Mechanika rozgrywki", czasOdstepu, true);
		wpiszDane("Interakcja z samolotem", czasPomiedzy, true);
		wpiszDane("Paweł Wójcik", czasPoLinijce, false);
		wpiszDane("Kolizje", czasPomiedzy, true);
		wpiszDane("Opracował‚", czasPoLinijce, true);
		wpiszDane("Adrian Burakowski", czasPoLinijce, false);
		wpiszDane("Zaprogramował‚", czasPoLinijce, true);
		wpiszDane("Paweł Wójcik", czasPoLinijce, false);

		wpiszDane("Testerzy", czasOdstepu, true);
		wpiszDane("Paweł Wójcik", czasPoLinijce, false);
		wpiszDane("Ada Gębicka", czasPoLinijce, false);
		wpiszDane("Adrian Burakowski", czasPoLinijce, false);
	}

	public static void draw(Graphics2D g) {
		drawMe(g);

		for (int i = 0; i < indexWiadomosci; i++) {
			if (System.currentTimeMillis() - czas > czasWejscia[i] && !czyWyswietlono[i]) {
				czyWyswietlono[i] = true;
				new Napisy(wiadomosci[i], czyTytul[i]);
				new KoncoweAlieny(rand.nextInt(Window.size_x - 50), Window.size_y + rand.nextInt(200));
				new KoncoweAlieny(rand.nextInt(Window.size_x - 50), Window.size_y + rand.nextInt(200));
				new KoncoweAlieny(rand.nextInt(Window.size_x - 50), Window.size_y + rand.nextInt(200));
				if (i >= indexWiadomosci - 1) {
					for (int j = 0; j < 100; j++) {
						new KoncoweAlieny(rand.nextInt(Window.size_x - 50), Window.size_y + rand.nextInt(200), true);
						new KoncowySamolocik(rand.nextInt(Window.size_x - 50), 2 * Window.size_y + rand.nextInt(200));
						new KoncowySamolocik(rand.nextInt(Window.size_x - 50), 2 * Window.size_y + rand.nextInt(2500),
								true);
					}
				}
			}
		}
		Napisy.draw(g);
		if (fade < 1 && !bylMax) {
			fade += 0.01;
			if (fade >= 1) {
				fade = 1;
				bylMax = !bylMax;
			}
		}

		else if (Napisy.nap.isEmpty()) {
			fade -= 0.02;
			if (fade < 0.0) {
				fade = 0;
				active = false;
			} else if (fade < 0.5 && !powrotDoMenu) {
				powrotDoMenu = true;
				new MenuGlowne();
			}
		}

	}

	private static void drawMe(Graphics2D g) {
		KoncoweAlieny.draw(g);
		KoncowySamolocik.draw(g);
		Bullet.drawBullets(g);
	}

	public static boolean isActive() {
		return active;
	}

	private void wpiszDane(String wiad, int czasPo, boolean tytulowy) {
		if (indexWiadomosci == 0) {
			wiadomosci[indexWiadomosci] = wiad;
			czasWejscia[indexWiadomosci] = czasPo;
			czyTytul[indexWiadomosci] = tytulowy;
		} else {
			wiadomosci[indexWiadomosci] = wiad;
			czasWejscia[indexWiadomosci] = czasWejscia[indexWiadomosci - 1] + czasPo;
			czyTytul[indexWiadomosci] = tytulowy;
		}
		indexWiadomosci++;
	}

	public static void aktualizujCzas(long time) {
		czas += time;
	}

}
