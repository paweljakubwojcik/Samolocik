package AI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import Bullets.BulletExtra;
import Bullets.BulletEyes;
import Bullets.BulletPaszkoProstopadly;
import Bullets.BulletPaszkoRownolegly;
import Bullets.CzerwoneObrazenia;
import Bullets.EnemyBullet;
import InterFace.AudioMeneger;
import InterFace.IntroBoss;
import Program.Window;

public class BossPaszko extends Enemy implements IEnemyBoss {

	public BufferedImage paszko, paszko2;
	boolean majestyWalk = true;
	private long czasAtak, czasRuchu = System.currentTimeMillis();
	private final int defaultHealth = 150 * 100;
	String nazwa = "PROF. PASZKOWSKI";

	public static int superCiosy = 0;

	private Graphics2D g2d;
	private BufferedImage im;

	AudioMeneger audio = new AudioMeneger();

	private long dlugoscInfoPomin;
	private boolean czyPominWyswietla = false;
	private int przezPomin = 0;

	public static boolean czyMoznaPominac = false;

	private static boolean czasPominac = false;

	/**
	 * 
	 * @param win
	 * @param x
	 * @param y
	 */
	BossPaszko(Window win, int x, int y) {
		super(x, y);
		this.win = win;
		this.x = x;
		this.y = y;
		this.velocity_x = -4;
		this.velocity_y = 2;
		health = defaultHealth;
		czasAtak = System.currentTimeMillis();
		EnemyGenerator.setAsteroids(false);

		// alternatywne obrazki paszko01.png paszko02.png
		URL[] url = { getClass().getResource("/images/paszko.png"), getClass().getResource("/images/paszko2.png") };
		try {
			paszko = ImageIO.read(url[0]);
			paszko2 = ImageIO.read(url[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.height = paszko.getHeight();
		this.width = paszko.getWidth();

		Enemy.enemies.add(this);
		this.AI();

		dlugoscInfoPomin = 0;
		czyMoznaPominac = true;
	}

	@SuppressWarnings("static-access")
	@Override
	public void drawMe(Graphics2D g) {
		if (velocity_x < 0) {
			g.drawImage(paszko, x, y, null);
		} else {
			g.drawImage(paszko2, x, y, null);
		}

		g.setColor(new Color(0, 255, 0, 200));
		g.drawRect(win.size_x * 79 / 80 - win.size_x / 3, win.size_y / 10, win.size_x / 3, win.size_y / 20);
		g.fillRect(win.size_x * 79 / 80 - win.size_x / 3 * (int) health / defaultHealth, win.size_y / 10,
				(win.size_x / 3) * (int) health / defaultHealth, win.size_y / 20);

		if (!majestyWalk) {
			g.setFont(new Font(null, Font.PLAIN, 25));
			g.drawString(nazwa, win.size_x * 50 / 80, win.size_y / 12);
		}

		if (obrazenia && klatkiObrazenia <= 8) {
			klatkiObrazenia++;
			im = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			g2d = (Graphics2D) im.getGraphics();
			if (velocity_x < 0)
				g2d.drawImage(paszko, 0, 0, width, height, null);
			else
				g2d.drawImage(paszko2, 0, 0, width, height, null);
			CzerwoneObrazenia.drawRed(g, im, x, y);
		} else {
			klatkiObrazenia = 0;
			obrazenia = false;
		}

		if (!czyPominWyswietla) {
			dlugoscInfoPomin = System.currentTimeMillis();
			czyPominWyswietla = !czyPominWyswietla;
		} else if (czyPominWyswietla && System.currentTimeMillis() - dlugoscInfoPomin < 3000
				&& EnemyGenerator.stworzonePaszki == 1) {
			przezPomin++;
			if (przezPomin > 100)
				przezPomin = 100;
			pominDraw(g);
		} else if (czyPominWyswietla && System.currentTimeMillis() - dlugoscInfoPomin < 4500
				&& EnemyGenerator.stworzonePaszki == 1) {
			przezPomin -= 2;
			if (przezPomin < 0)
				przezPomin = 0;
			pominDraw(g);
		}
	}

	private void pominDraw(Graphics2D g) {
		g.setColor(new Color(255, 255, 100, przezPomin));
		g.setFont(new Font(null, 0, 20));
		g.drawString("Naciśnij 'S' aby pominąć", 50, 570);
		g.setColor(new Color(255, 255, 100, 255));
	}

	@SuppressWarnings("static-access")
	@Override
	public void myMotion() {
		if (czasPominac && majestyWalk) {
			wylacz2();
			czasPominac = !czasPominac;
		}

		if (majestyWalk) {
			if (y < 120) {
				if (System.currentTimeMillis() - czasRuchu > 50) {
					y++;
					czasRuchu = System.currentTimeMillis();
				}
			} else if (IntroBoss.list.size() == 0) {
				new IntroBoss(10000, 5, 5);
			} else {
				if (IntroBoss.end) {
					majestyWalk = false;
					EnemyGenerator.setAsteroids(true);
				}
			}

		} else {

			x += velocity_x;
			if (generator.nextBoolean())
				y += velocity_y;
			else
				y -= velocity_y;

			if (y < 20) {
				y = 20;
				velocity_y = -velocity_y;
			} else if (y > 200) {
				y = 200;
				velocity_y = -velocity_y;
			}

			if (x < 0) {
				x = 0;
				velocity_x = -velocity_x;
			} else if (x + paszko.getWidth() > win.size_x) {
				velocity_x = -velocity_x;
			}

			this.AI();
		}
	}

	@Override
	public void strzal(String rodzaj) {

		if (rodzaj == "Bullet") {
			new EnemyBullet(x, y + height / 2);
		} else if (rodzaj == "BulletExtra") {
			new BulletExtra(x, y + height / 2);
		}
		if (BulletPaszkoRownolegly.liczbaKresek == 0 && BulletPaszkoProstopadly.liczbaKresek == 0) {

			if (rodzaj == "BulletPaszkoRownolegly") {
				superCiosy++;
				audio.playNoRepeat(2);
				int losX, losY;

				for (int i = 0; i < 7; i++) {
					losX = generator.nextInt(Window.size_x);
					losY = generator.nextInt(Window.size_y);
					new BulletPaszkoRownolegly(losX, losY + height / 2);
				}

			} else if (rodzaj == "BulletPaszkoProstopadly") {
				superCiosy++;
				audio.playNoRepeat(1);
				int losX, losY;
				for (int i = 0; i < 4; i++) {
					losX = generator.nextInt(Window.size_x);
					losY = generator.nextInt(Window.size_y / 2) + Window.size_y / 2;
					new BulletPaszkoProstopadly(losX, losY);
				}

			} else if (rodzaj == "BulletEyes") {
				superCiosy++;
				new BulletEyes(x, y + height / 2, this);
			}
		}

	}

	@Override
	public void AI() {
		int rszczalu = generator.nextInt(5);
		if (System.currentTimeMillis() - czasAtak > generator.nextInt(1000) + 500) { // 1000
																						// 500
			if (rszczalu == 0)
				strzal("Bullet");
			else if (rszczalu == 1)
				strzal("BulletEyes");
			else if (rszczalu == 2)
				strzal("BulletExtra");
			else if (rszczalu == 3)
				strzal("BulletPaszkoRownolegly");
			else if (rszczalu == 4)
				strzal("BulletPaszkoProstopadly");
			czasAtak = System.currentTimeMillis();
		}

	}

	@Override
	public int[][] getPole() {

		int[][] tab = { { x + paszko.getWidth() / 2, y + paszko.getWidth() / 2, paszko.getWidth() / 2 } };
		int[][] tab1 = { { 12345, 12345, 0 } };

		if (!majestyWalk)
			return tab;
		else
			return tab1;
	}

	public static void wylacz() {
		czasPominac = true;
	}

	private void wylacz2() {
		czyMoznaPominac = false;
		y = 120;
		dlugoscInfoPomin = 0;
		new IntroBoss(10000, 5, 5);
	}

}
