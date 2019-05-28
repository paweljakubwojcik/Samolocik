package Gracz;

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
import Bullets.Granade;
import Bullets.Pellet;
import InterFace.MessageBox;
import Program.Window;
import Rozgrywka.Collisionable;

public class Player extends Collisionable {

	Window win;
	BufferedImage statek, statekLewa, statekPrawa, statekRuch, statekRuchUp, statekRuchDown;

	final int DefaultHealth = 40 * 100;
	int velocity = 5; // predkosc samolotu
	int x, y, width, height;
	long CzasSzczau, CzasAtaku;
	long delay = 200;
	public float health;

	public int[] amunition = { 1, 0, 30, 30, 2000 };

	int whichAmunition = 0;

	public boolean shield = false;
	long timeShield;

	private boolean ruchL = false, ruchP = false, ruchUP = false, ruchDOWN = false;

	String nazwa = "PLAYER";

	public int punkty = 0;

	public int wystrzeloneNaboje = 0;
	public int zlapaneBonusy = 0;
	public int zlapaneTarcze = 0;

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
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.height = statek.getHeight() * 8 / 10;
		this.width = statek.getWidth() * 8 / 10;
	}

	/**
	 * 
	 * @param Graphics2D
	 */
	@SuppressWarnings("static-access")
	public void draw(Graphics2D g) {
		// actual image
		drawAnimation(g);

		// kwadrat testowy
		//
		// g.setColor(Color.red);
		// g.drawRect(getPole()[0][0],getPole()[0][1],getPole()[0][2],getPole()[0][3]);
		//
		if (shield) {
			long pozostalyCzas = -System.currentTimeMillis() + timeShield + Shield.time;
			if (pozostalyCzas > 1500)
				g.setColor(new Color(50, 50, 255, 100));
			else if (pozostalyCzas < 1500 && pozostalyCzas % 400 > 200)
				g.setColor(new Color(50, 50, 255, 50));
			else
				g.setColor(new Color(50, 50, 255, 100));
			g.fillOval(x, y, statek.getWidth(), statek.getHeight());
			if (pozostalyCzas < 0)
				shield = false;
		}
		// pasek �ycia
		g.setColor(new Color(255, 0, 0, 150));
		g.drawRect(win.size_x / 80, win.size_y / 10, win.size_x / 3, win.size_y / 20);
		g.fillRect(win.size_x / 80, win.size_y / 10, (win.size_x / 3) * (int) health / DefaultHealth, win.size_y / 20);

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
	}

	public void strzal() {

		switch (whichAmunition) {
		case 0:
			if (System.currentTimeMillis() - CzasSzczau > Bullet.delay && amunition[0] != 0) {
				wystrzeloneNaboje++;
				new Bullet(x + statek.getWidth() / 2 - Bullet.size / 2, y - Bullet.size);
				CzasSzczau = System.currentTimeMillis();
			}
			break;
		case 1:
			if (System.currentTimeMillis() - CzasSzczau > BulletExtraPlayer.delay && amunition[whichAmunition] != 0) {
				wystrzeloneNaboje++;
				new BulletExtraPlayer(x + statek.getWidth() / 2 - BulletExtraPlayer.size / 2,
						y - BulletExtraPlayer.size);
				amunition[whichAmunition]--;
				CzasSzczau = System.currentTimeMillis();
			}
			break;
		case 2:
			if (System.currentTimeMillis() - CzasSzczau > BulletPellet.delay && amunition[whichAmunition] != 0) {
				wystrzeloneNaboje++;
				new Pellet(x + statek.getWidth() / 2 - BulletPellet.size / 2, y - BulletPellet.size);
				amunition[whichAmunition]--;
				CzasSzczau = System.currentTimeMillis();
			}
			break;
		case 3:
			if (System.currentTimeMillis() - CzasSzczau > Granade.delay && amunition[whichAmunition] != 0) {
				wystrzeloneNaboje++;
				if (!Bullet.check(Granade.class)) {
					new Granade(x + statek.getWidth() / 2 - Granade.size / 2, y - Granade.size);
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
		int[][] tab = { { x + statek.getWidth() / 10, y + statek.getHeight() / 10, width, height } };
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
				int damage = -((asteroid.width + asteroid.height) / 2 / 50) * 100;
				this.health -= ((asteroid.width + asteroid.height) / 2 / 50) * 100;
				new MessageBox(Integer.toString(damage), 1000, x, y, "RED");
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
			g.drawImage(statekRuch, x, y, null);
		} else if (ruchUP) {
			g.drawImage(statekRuchUp, x, y, null);
		} else if (ruchDOWN) {
			g.drawImage(statekRuchDown, x, y, null);
		}

		if (!ruchL && !ruchP) {
			g.drawImage(statek, x, y, null);
		} else if (ruchL) {
			g.drawImage(statekLewa, x, y, null);
		} else if (ruchP) {
			g.drawImage(statekPrawa, x, y, null);
		}

		ruchDOWN = false;
		ruchUP = false;
		ruchL = false;
		ruchP = false;
	}

}
