package Gracz;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import AI.Asteroid;
import Bullets.Bullet;
import Bullets.BulletExtraPlayer;
import Bullets.BulletPellet;
import Bullets.BulletPlazma;
import Bullets.CzerwoneObrazenia;
import Bullets.Granade;
import Bullets.Pellet;
import InterFace.MessageBox;
import Program.Samolotoszczalec;
import Program.Window;
import Rozgrywka.Collisionable;

public class Player extends Collisionable {

	Window win;
	BufferedImage statek, statekLewa, statekPrawa, statekRuch, statekRuchUp, statekRuchDown;
	BufferedImage[] statekRozpad = new BufferedImage[6];
	BufferedImage[] statekObrazenia = new BufferedImage[3];
	BufferedImage[] statekOgien = new BufferedImage[3];

	final int DefaultHealth = 40 * 100;
	int velocity = 5; // predkosc samolotu
	int x, y, width, height, width2, height2;
	long CzasSzczau, CzasAtaku;
	long delay = 200;
	public float health;
	public boolean obrazenia = false;

	public int[] amunition = { 1, 0, 30, 30, 2000 };

	int whichAmunition = 0;

	public boolean shield = false, shrink = false;;
	long timeShield, timeShrink;

	private boolean ruchL = false, ruchP = false, ruchUP = false, ruchDOWN = false;

	String nazwa = "PLAYER";

	public int punkty = 0;

	public int wystrzeloneNaboje = 0;
	public int zlapaneBonusy = 0;
	public int zlapaneTarcze = 0;

	private BufferedImage maloHP = new BufferedImage(Window.size_x, Window.size_y, BufferedImage.TYPE_INT_ARGB);
	private Graphics2D gHP = (Graphics2D) maloHP.getGraphics();
	private AlphaComposite ac;
	private int szerCzerRamki = 20;
	private float alphaRED = (float) 0.4;
	private int fadeIn;
	private boolean fade = true;
	private long fadeTime;

	private BufferedImage im;
	private Graphics2D g2d;

	public int klatkiObrazenia = 0;

	private long czasRozpad;
	private int klatkaRozpadu;

	private long czasOgien;
	private int klatkaOgien;

	public Player(Window win, int x, int y) {
		this.win = win;
		this.x = x;
		this.y = y;
		this.health = DefaultHealth;

		CzasSzczau = CzasAtaku = System.currentTimeMillis();

		// Poszczególne animacje dla (odpowiednio) swobodny lot | lot w lewo |
		// lot w
		// prawo | silniki swobodny lot | silniki lot w górę | silniki lot w
		// dół
		URL[] url = { getClass().getResource("/images/samolot.png"), getClass().getResource("/images/samolot left.png"),
				getClass().getResource("/images/samolot right.png"), getClass().getResource("/images/ognieruch.png"),
				getClass().getResource("/images/ognieruchup.png"),
				getClass().getResource("/images/ognieruchdown.png") };
		try {
			statek = ImageIO.read(url[0]);
			statekLewa = ImageIO.read(url[1]);
			statekPrawa = ImageIO.read(url[2]);
			statekRuch = ImageIO.read(url[3]);
			statekRuchUp = ImageIO.read(url[4]);
			statekRuchDown = ImageIO.read(url[5]);

			for (int i = 1; i <= 6; i++) {
				statekRozpad[i - 1] = ImageIO.read(getClass().getResource("/images/samolotRozpad" + i + ".png"));
			}
			for (int i = 1; i <= 3; i++) {
				statekObrazenia[i - 1] = ImageIO.read(getClass().getResource("/images/samolotObrazenia" + i + ".png"));
				statekOgien[i - 1] = ImageIO.read(getClass().getResource("/images/samolotOgien" + i + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.height = statek.getHeight();
		this.width = statek.getWidth();
		this.height2 = statekRuch.getHeight();
		this.width2 = statekRuch.getWidth();
	}

	public void setDefault() {
		health = DefaultHealth;
		amunition[0] = 1;
		for (int i = 1; i < amunition.length; i++)
			amunition[i] = 0;
		punkty = 0;

		czasRozpad = 0;
		klatkaRozpadu = 0;

		czasOgien = 0;
		klatkaOgien = 0;

		x = 400;
		y = 500;
	}

	/**
	 * 
	 * @param Graphics2D
	 */
	@SuppressWarnings("static-access")
	public void draw(Graphics2D g) {

		if (health <= 0) {
			if (System.currentTimeMillis() - czasRozpad > 100 && klatkaRozpadu <= 6) {
				klatkaRozpadu++;
				czasRozpad = System.currentTimeMillis();
			}
			if (klatkaRozpadu < 6) {
				g.drawImage(statekRozpad[klatkaRozpadu], x - width, y - height, width * 3, height * 3, null);
			}
		} else {
			// actual image
			drawAnimation(g);

			// kwadrat testowy
			//
			// g.setColor(Color.red);
			// g.drawRect(getPole()[0][0],getPole()[0][1],getPole()[0][2],getPole()[0][3]);
			//

			if (shrink && System.currentTimeMillis() - timeShrink > 10000) {
				this.height = statek.getHeight();
				this.width = statek.getWidth();
				this.height2 = statekRuch.getHeight();
				this.width2 = statekRuch.getWidth();
				shrink = false;

			}

			if (shield) {
				long pozostalyCzas = -System.currentTimeMillis() + timeShield + Shield.time;
				if (pozostalyCzas > 1500)
					g.setColor(new Color(50, 50, 255, 100));
				else if (pozostalyCzas < 1500 && pozostalyCzas % 400 > 200)
					g.setColor(new Color(50, 50, 255, 50));
				else
					g.setColor(new Color(50, 50, 255, 100));
				g.fillOval(x, y, width, height);
				if (pozostalyCzas < 0)
					shield = false;
			}
			// pasek �ycia
			g.setColor(new Color(255, 0, 0, 150));
			g.drawRect(win.size_x / 80, win.size_y / 10, win.size_x / 3, win.size_y / 20);
			g.fillRect(win.size_x / 80, win.size_y / 10, (win.size_x / 3) * (int) health / DefaultHealth,
					win.size_y / 20);

			// napis player
			g.setFont(new Font(null, Font.PLAIN, 25));
			g.drawString(nazwa, win.size_x / 80, win.size_y / 12);

			Color[] kolory = { new Color(255, 0, 0, 200), new Color(0, 255, 0, 200), new Color(0, 0, 255, 200),
					new Color(0, 255, 255, 200), new Color(255, 0, 255, 200) };
			g.setFont(new Font(null, Font.PLAIN, 10));

			// rodzaje naboi
			for (int i = 0; i < kolory.length; i++) {
				g.setColor(kolory[i]);
				g.fillOval(10, 100 + 20 * i, 10, 10);
				if (i == 0)
					g.drawString("infinite", 30, 110 + 20 * i);
				else
					g.drawString(Integer.toString(amunition[i]), 30, 110 + 20 * i);
				if (whichAmunition == i)
					g.drawRect(10, 100 + 20 * i, 10, 10);
			}

			// ilosc naboi

			// g.drawString(Integer.toString(amunition[whichAmunition]), win.size_x
			// / 80, win.size_y / 10 + win.size_y / 20 + 30);

			if (health < DefaultHealth / 5) {
				if (System.currentTimeMillis() - fadeTime > 100) {
					if (!fade) {
						alphaRED += 0.02;
						fadeIn--;
						szerCzerRamki--;
					} else if (fade) {
						alphaRED -= 0.02;
						fadeIn++;
						szerCzerRamki++;
					}
					fadeTime = System.currentTimeMillis();

					if (fadeIn == 10 || fadeIn == 0) {
						fade = !fade;
					}
				}
				maloHP = new BufferedImage(Window.size_x, Window.size_y, BufferedImage.TYPE_INT_ARGB);
				gHP = (Graphics2D) maloHP.getGraphics();
				gHP.setColor(Color.RED);
				ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaRED);
				gHP.setComposite(ac);
				gHP.fillRect(szerCzerRamki, 0, Window.size_x - 2 * szerCzerRamki, 40 + szerCzerRamki);
				gHP.fillRect(0, 0, szerCzerRamki, Window.size_y);
				gHP.fillRect(Window.size_x - szerCzerRamki, 0, Window.size_x, Window.size_y);
				gHP.fillRect(szerCzerRamki, Window.size_y - szerCzerRamki, Window.size_x - 2 * szerCzerRamki,
						Window.size_y);

				g.drawImage(maloHP, 0, 0, null);
			}

			if (obrazenia && klatkiObrazenia <= 8) {
				klatkiObrazenia++;
				im = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				g2d = (Graphics2D) im.getGraphics();
				g2d.drawImage(statek, 0, 0, width, height, null);
				CzerwoneObrazenia.drawRed(g, im, x, y);
			} else {
				klatkiObrazenia = 0;
				obrazenia = false;
			}

		}

	}

	public void strzal() {

		switch (whichAmunition) {
		case 0:
			if (System.currentTimeMillis() - CzasSzczau > Bullet.delay && amunition[0] != 0) {
				wystrzeloneNaboje++;
				new Bullet(x + width / 2 - Bullet.size / 2, y - Bullet.size);
				CzasSzczau = System.currentTimeMillis();
			}
			break;
		case 1:
			if (System.currentTimeMillis() - CzasSzczau > BulletExtraPlayer.delay && amunition[whichAmunition] != 0) {
				wystrzeloneNaboje++;
				new BulletExtraPlayer(x + width / 2 - BulletExtraPlayer.size / 2, y - BulletExtraPlayer.size);
				amunition[whichAmunition]--;
				CzasSzczau = System.currentTimeMillis();
			}
			break;
		case 2:
			if (System.currentTimeMillis() - CzasSzczau > BulletPellet.delay && amunition[whichAmunition] != 0) {
				wystrzeloneNaboje++;
				new Pellet(x + width / 2 - BulletPellet.size / 2, y - BulletPellet.size);
				amunition[whichAmunition]--;
				CzasSzczau = System.currentTimeMillis();
			}
			break;
		case 3:
			if (System.currentTimeMillis() - CzasSzczau > Granade.delay && amunition[whichAmunition] != 0) {
				wystrzeloneNaboje++;
				if (!Bullet.check(Granade.class)) {
					new Granade(x + width / 2 - Granade.size / 2, y - Granade.size);
					amunition[whichAmunition]--;
					CzasSzczau = System.currentTimeMillis();

				}
			}
			break;
		case 4:
			if (System.currentTimeMillis() - CzasSzczau > BulletPlazma.delay && amunition[whichAmunition] != 0) {
				wystrzeloneNaboje++;
				new BulletPlazma(x + statek.getWidth() / 2 - BulletPlazma.size / 2, y - BulletPlazma.size);
				amunition[whichAmunition]--;
				CzasSzczau = System.currentTimeMillis();
			}
			break;
		}
		if (amunition[whichAmunition] == 0) {
			whichAmunition = 0;
			new MessageBox("No ammo", 1000, x, y, "RED");
		}
	}

	public void changeAmunition(int n) {
		if (amunition[n] != 0)
			whichAmunition = n;
	}

	@SuppressWarnings("static-access")
	public void moveRight() {
		if (x + statek.getWidth() < win.size_x) {
			x += velocity;
			ruchP = true;
		}
	}

	public void moveLeft() {
		if (x > 0) {
			x -= velocity;
			ruchL = true;
		}
	}

	@SuppressWarnings("static-access")
	public void moveUp() {
		if (y > win.size_y / 2) {
			y -= velocity / 2;
			ruchUP = true;
		}
	}

	// do predkosci odjac lub dodac predkosc planszy gdy bedzie
	@SuppressWarnings("static-access")
	public void moveDown() {
		if (y + statek.getHeight() < win.size_y) {
			y += velocity;
			ruchDOWN = true;
		}
	}

	@Override
	public int[][] getPole() {
		int[][] tab = { { x + statek.getWidth() / 10, y + statek.getHeight() / 10, width * 8 / 10, height * 8 / 10 } };
		return tab;
	}

	@Override
	public void collision(Object o) {

		if (shield && System.currentTimeMillis() - timeShield > Shield.time)
			shield = false;

		if (o.getClass() == Asteroid.class) {
			Asteroid asteroid = (Asteroid) o;

			if (shield)
				;
			else if (System.currentTimeMillis() - CzasAtaku > delay) {

				this.health -= asteroid.damage;
				new MessageBox(Integer.toString((int) -asteroid.damage), 1000, x, y, "RED");
				CzasAtaku = System.currentTimeMillis();
			}

			if (this.x < asteroid.x && this.y < asteroid.y + asteroid.height)
				this.moveLeft();
			else if (this.x > asteroid.x + asteroid.width)
				this.moveRight();
			else if (this.y > asteroid.y)
				this.moveDown();

		}

		if (this.health <= 0) {
			new MessageBox("umarlem bo nie mam zyc");
			win.audio.przegrana();

		}
	}

	public boolean isDead() {
		if (health <= 0)
			return true;
		else
			return false;
	}

	/**
	 * Funkcja rysuje animacje samolocika
	 * 
	 * @param g
	 */
	private void drawAnimation(Graphics2D g) {
		if (!ruchUP && !ruchDOWN) {
			g.drawImage(statekRuch, x, y, width2, height2, null);
		} else if (ruchUP) {
			g.drawImage(statekRuchUp, x, y, width2, height2, null);
		} else if (ruchDOWN) {
			g.drawImage(statekRuchDown, x, y, width2, height2, null);
		}

		if (!ruchL && !ruchP) {
			g.drawImage(statek, x, y, width, height, null);
		} else if (ruchL) {
			g.drawImage(statekLewa, x, y, width, height, null);
		} else if (ruchP) {
			g.drawImage(statekPrawa, x, y, width, height, null);
		}

		if (health <= DefaultHealth * 2 / 5) {
			g.drawImage(statekObrazenia[2], x, y, width, height, null);
			if (System.currentTimeMillis() - czasOgien > 50) {
				if (klatkaOgien < 3)
					klatkaOgien++;
				if (klatkaOgien == 3)
					klatkaOgien = 0;
				czasOgien = System.currentTimeMillis();
			}
			if (health <= DefaultHealth * 1 / 5)
				g.drawImage(statekOgien[klatkaOgien], x, y, width, height, null);
		} else if (health <= DefaultHealth * 3 / 5) {
			g.drawImage(statekObrazenia[1], x, y, width, height, null);
		} else if (health <= DefaultHealth * 4 / 5) {
			g.drawImage(statekObrazenia[0], x, y, width, height, null);
		}

		ruchDOWN = false;
		ruchUP = false;
		ruchL = false;
		ruchP = false;
	}

	public void shrink() {
		if (!shrink) {
			width = width / 2;
			height = height / 2;
			width2 /= 2;
			height2 /= 2;
		}
		timeShrink = System.currentTimeMillis();
		shrink = true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

}
