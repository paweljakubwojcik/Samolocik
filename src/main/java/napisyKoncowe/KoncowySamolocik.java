package napisyKoncowe;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import Bullets.Bullet;
import Bullets.BulletExtraPlayer;
import Bullets.BulletPellet;
import Bullets.BulletPlazma;
import Bullets.Granade;
import Bullets.Pellet;

public class KoncowySamolocik {
	public static ArrayList<KoncowySamolocik> sam = new ArrayList<KoncowySamolocik>();
	public static BufferedImage statek, statekRuchUp;
	public static boolean grafika = false;
	private int x, y;
	private int velocity_x = 0;
	private int velocity_y = -3;
	private int width = 50;

	public static Random rand = new Random();

	private boolean czyStrzelil;

	public KoncowySamolocik(int x, int y) {
		this.x = x;
		this.y = y;
		czyStrzelil = false;

		if (!grafika) {
			try {
				for (int i = 1; i <= 4; i++) {
					statek = ImageIO.read(getClass().getResource("/images/samolot.png"));
					statekRuchUp = ImageIO.read(getClass().getResource("/images/ognieruchup.png"));
				}
				grafika = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		sam.add(this);
	}

	public KoncowySamolocik(int x, int y, boolean czyStrzelil) {
		this(x, y);
		this.czyStrzelil = czyStrzelil;
	}

	public static void draw(Graphics2D g) {
		for (int i = 0; i < sam.size() - 1; i++) {
			if (sam.size() != 0) {
				sam.get(i).drawMe(g);
			}
		}
	}

	public void drawMe(Graphics2D g) {
		// animacja
		myMotion();

		g.drawImage(statek, x, y, null);
		g.drawImage(statekRuchUp, x, y, null);

	}

	public void myMotion() {
		x += velocity_x;
		y += velocity_y;
		if (!czyStrzelil && y > 200 && y < 550) {
			int nr = rand.nextInt(5);
			switch (nr) {
			case 0:
				new Bullet(x + width / 2 - Bullet.size / 2, y - Bullet.size);
				break;
			case 1:
				new BulletExtraPlayer(x + width / 2 - BulletExtraPlayer.size / 2, y - BulletExtraPlayer.size);
				break;
			case 2:
				new Pellet(x + width / 2 - BulletPellet.size / 2, y - BulletPellet.size);
				break;
			case 3:
				new Granade(x + width / 2 - Granade.size / 2, y - Granade.size);
				break;
			case 4:
				new BulletPlazma(x + statek.getWidth() / 2 - BulletPlazma.size / 2, y - BulletPlazma.size);
				break;
			}
			czyStrzelil = !czyStrzelil;
		}
		if (y < -100) {
			try {
				sam.remove(this);
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

}
