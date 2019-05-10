import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Player extends Collisionable {

	Window win;
	BufferedImage statek;

	final int DefaultHealth = 40;
	int velocity = 5; // predkosc samolotu
	int x, y, width, height;
	long CzasSzczau, CzasAtaku, czasDodatkowejAmunicji;
	long delay = 200;
	float health;
	int[] amunition = { 1, 0, 0, 0, 0 };
	int whichAmunition = 0;

	String nazwa = "PLAYER";

	Player(Window win, int x, int y) {
		this.win = win;
		this.x = x;
		this.y = y;
		this.health = DefaultHealth;

		CzasSzczau = CzasAtaku = System.currentTimeMillis();
		URL url = getClass().getResource("samolot.png");
		try {
			statek = ImageIO.read(url);
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
		g.drawImage(statek, x, y, null);

		// kwadrat testowy
		
		  g.setColor(Color.red); g.drawRect(x + statek.getWidth() / 10, y +
		  statek.getHeight() / 10, width, height);
		 

		// pasek ¿ycia
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
				new Bullet(x + statek.getWidth() * 6 / 10, y - statek.getHeight() / 5);
				CzasSzczau = czasDodatkowejAmunicji = System.currentTimeMillis();
			}
			break;
		case 1:
			if (System.currentTimeMillis() - CzasSzczau > BulletExtraPlayer.delay && amunition[whichAmunition] != 0) {
				new BulletExtraPlayer(x + statek.getWidth() * 6 / 10, y - statek.getHeight() / 2);
				amunition[whichAmunition]--;
				CzasSzczau = czasDodatkowejAmunicji = System.currentTimeMillis();
			}
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		}
	}

	@SuppressWarnings("static-access")
	public void moveRight() {
		if (x + statek.getWidth() < win.size_x) {
			x += velocity;
		}
	}

	public void moveLeft() {
		if (x > 0) {
			x -= velocity;
		}
	}

	@SuppressWarnings("static-access")
	public void moveUp() {
		if (y > win.size_y / 2) {
			y -= velocity / 2;
		}
	}

	// do predkosci odjac lub dodac predkosc planszy gdy bedzie
	@SuppressWarnings("static-access")
	public void moveDown() {
		if (y + statek.getHeight() < win.size_y) {
			y += velocity;
		}
	}

	@Override
	public int[][] getPole() {
		int[][] tab = { { x + statek.getWidth() / 10, y + statek.getHeight() / 10, width, height } };
		return tab;
	}

	@Override
	public void collision(Object o) {

		if (o.getClass() == Asteroid.class) {
			Asteroid asteroid = (Asteroid) o;

			if (System.currentTimeMillis() - CzasAtaku > delay) {
				this.health -= ((asteroid.width + asteroid.height) / 2 / 50);
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
			new MessageBox("umarlem");

		}
	}

	public boolean isDead() {
		if (health <= 0)
			return true;
		else
			return false;
	}

}
