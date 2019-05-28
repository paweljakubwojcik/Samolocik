package achievement;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Gracz.Player;
import Program.Window;

public class Achievement {

	private static BufferedImage[] image = new BufferedImage[15];
	private int x = Window.size_x, y = 50;
	private long time;
	private boolean powrot = false;

	private float alpha = (float) 0.6;
	private AlphaComposite ac;

	private static List<Achievement> osiagniecia = new ArrayList<>();
	private int slot;

	private int osiagniecie;

	private static String[][] nazwyOsiagniec = new String[15][2];
	private static int[] punkty = new int[15];

	/**
	 * Narzędzie do wyświetlania Achievementow. Funkcja drawMe dba o zachowanie
	 * animacji, oraz rysuje wszystkie osiąagniecia na ekranie.
	 */
	public Achievement() {
		nazwyOsiagniec[0][0] = "Zabójca";
		nazwyOsiagniec[0][1] = "Zabij Aliena";
		punkty[0] = 1;

		nazwyOsiagniec[1][0] = "Survivalowiec";
		nazwyOsiagniec[1][1] = "Przetrwaj 1 falę";
		punkty[1] = 2;

		nazwyOsiagniec[2][0] = "Gorąca lufa";
		nazwyOsiagniec[2][1] = "Wystrzel 100 naboi";
		punkty[2] = 2;

		nazwyOsiagniec[3][0] = "Niszczyciel";
		nazwyOsiagniec[3][1] = "Zniszcz 10 asteroid";
		punkty[3] = 3;

		nazwyOsiagniec[4][0] = "Farciarz";
		nazwyOsiagniec[4][1] = "Złap 5 bonusów";
		punkty[4] = 3;

		nazwyOsiagniec[5][0] = "Morderca";
		nazwyOsiagniec[5][1] = "Zabij 25 alienów";
		punkty[5] = 5;

		nazwyOsiagniec[6][0] = "Zabity";
		nazwyOsiagniec[6][1] = "Ale czy na pewno?";
		punkty[6] = 10;

		nazwyOsiagniec[7][0] = "To koniec";
		nazwyOsiagniec[7][1] = "Pokonaj Paszkowskiego";
		punkty[7] = 25;

		nazwyOsiagniec[8][0] = "Niezniszczalny";
		nazwyOsiagniec[8][1] = "Złap 20 tarcz";
		punkty[8] = 6;

		nazwyOsiagniec[9][0] = "Kosmita";
		nazwyOsiagniec[9][1] = "Zniszcz 100 asteroid";
		punkty[9] = 4;

		nazwyOsiagniec[10][0] = "Tak jest łatwiej";
		nazwyOsiagniec[10][1] = "Złap 100 bonusów";
		punkty[10] = 5;

		nazwyOsiagniec[11][0] = "Bóg";
		nazwyOsiagniec[11][1] = "Przeżyj 100 super ciosów";
		punkty[11] = 10;

		nazwyOsiagniec[12][0] = "Dewastator";
		nazwyOsiagniec[12][1] = "Zniszcz 200 asteroid";
		punkty[12] = 10;

		nazwyOsiagniec[13][0] = "Rzeźnia";
		nazwyOsiagniec[13][1] = "Wystrzel 2500 naboi";
		punkty[13] = 4;

		nazwyOsiagniec[14][0] = "Kosmiczny Terror";
		nazwyOsiagniec[14][1] = "Wystrzel 7000 naboi";
		punkty[14] = 10;

		generujObrazki();

	}

	/**
	 * -0 Zabójca „Zabij aliena” -1 Survivalowiec „Przetrwaj 1 falę” -2 Gorąca lufa
	 * „Wystrzel 100 naboi” -3 Niszczyciel „Zniszcz 10 asteroid” -4 Farciarz „Złap 5
	 * bonusów” -5 Morderca „Zabij 25 alienów” -6 Zabity „Ale czy na pewno?” (tyczy
	 * 1st śmierci Paszka) -7 To koniec „Pokonaj Paszka” -8 Niezniszczalny „Złap 20
	 * tarcz” -9 Kosmita „Zniszcz 100 asteroid” -10 Tak jest łatwiej „Złap 100
	 * bonusów” -11 Bóg „Przeżyj 100 super ciosów” -12 Dewastator „Zniszcz 200
	 * asteroid” -13 Rzeźnia „Wystrzel 2500 naboi” -14 Kosmiczny terror „Wystrzel
	 * 7000 naboi”
	 * 
	 * @param nrOsiagniecia - od 0 do 14 opisane w opisie Konstruktora
	 * @param player        - gracz któremu ma zostać przypisane osiągnięcie
	 */
	public Achievement(int nrOsiagniecia, Player player) {
		ustawSlot();
		y += 55 * slot;
		dodajPunkty(nrOsiagniecia, player);
		osiagniecie = nrOsiagniecia;
		osiagniecia.add(this);
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < osiagniecia.size(); i++) {
			osiagniecia.get(i).drawMe(g);
		}
	}

	private void drawMe(Graphics2D g) {
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g.setComposite(ac);
		g.drawImage(image[osiagniecie], x, y, null);
		motion();
	}

	private void motion() {
		if (x + image[0].getWidth() > Window.size_x && !powrot) {
			x -= 3;
		} else if (!powrot) {
			time = System.currentTimeMillis();
			powrot = true;
		}

		if (System.currentTimeMillis() - time > 3000 && powrot) {
			x += 5;
			if (x > Window.size_x) {
				try {
					this.finalize();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void ustawSlot() {
		boolean nieZnaleziono = true;
		int wolnySlot = 0;
		while (nieZnaleziono) {
			boolean wolny = true;
			for (int i = 0; i < osiagniecia.size(); i++) {
				if (osiagniecia.get(i).slot == wolnySlot) {
					wolny = false;
				}
			}
			if (wolny) {
				slot = wolnySlot;
				nieZnaleziono = false;
			} else {
				wolnySlot++;
			}
		}
	}

	private void dodajPunkty(int nr, Player player) {
		player.punkty += punkty[nr];
	}

	private void generujObrazki() {
		BufferedImage pucharson = null;
		try {
			pucharson = ImageIO.read(getClass().getResource("Puchar.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 15; i++) {
			image[i] = new BufferedImage(250, 50, BufferedImage.TYPE_INT_ARGB);
			Graphics2D im = (Graphics2D) image[i].getGraphics();
			im.drawImage(pucharson, 0, 0, null);
			im.setColor(Color.YELLOW);
			im.setFont(new Font(null, 0, 20));
			im.drawString(nazwyOsiagniec[i][0], 60, 20);
			im.setFont(new Font(null, 0, 15));
			im.drawString(nazwyOsiagniec[i][1], 60, 45);

		}

	}

}
