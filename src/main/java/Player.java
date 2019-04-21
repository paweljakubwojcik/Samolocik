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

	int velocity = 5; // predkosc samolotu
	int x, y, width, height;
	long CzasSzczau, CzasAtaku = System.currentTimeMillis();
	long delay = 200;
	int health;
	final int DefaultHealth = 100;
	String nazwa = "PLAYER";

	Player(Window win, int x, int y) {
		this.win = win;
		this.x = x;
		this.y = y;
		this.health = DefaultHealth;

		CzasSzczau = System.currentTimeMillis();
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
		/*g.setColor(Color.red);
		g.drawRect(x + statek.getWidth() / 10, y + statek.getHeight() / 10, width, height);*/

		// pasek ¿ycia
		g.setColor(new Color(255, 0, 0, 200));
		g.drawRect(win.size_x / 80, win.size_y / 10, win.size_x / 3, win.size_y / 20);
		g.fillRect(win.size_x / 80, win.size_y / 10, (win.size_x / 3) * health / DefaultHealth, win.size_y / 20);

		// napis player
		g.setFont(new Font(null, Font.PLAIN, 25));
		g.drawString(nazwa, win.size_x / 80, win.size_y / 12);
	}

	public void strzal() {

		if (System.currentTimeMillis() - CzasSzczau > delay) {
			new Bullet(x + statek.getWidth() * 6 / 10, y - statek.getHeight() / 5);
			CzasSzczau = System.currentTimeMillis();
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

		if (o.getClass() == Bullet.class) {
			Bullet bullet = (Bullet) o;
			this.health -= bullet.damage;

		} else if (o.getClass() == Asteroid.class) {
			Asteroid asteroid = (Asteroid) o;

			if (System.currentTimeMillis() - CzasAtaku > delay) {
				this.health -= 5;
				CzasAtaku = System.currentTimeMillis();
			}

			if (this.x < asteroid.x && this.y < asteroid.y + asteroid.height)
				this.moveLeft();
			else if (this.x > asteroid.x + asteroid.width)
				this.moveRight();
			else if (this.y > asteroid.y)
				this.moveDown();

		}

		if (this.health == 0) {
			System.out.println("umar³em");
			try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
