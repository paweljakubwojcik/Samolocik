import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Alien extends Enemy {

	static final int defaultHealth = 4;
	int zakresRuchuX, zakresRuchuY;
	int velocity;
	BufferedImage Image[] = new BufferedImage[4];
	int i = 0;
	long time = System.currentTimeMillis();

	/**
	 * 
	 * @param x
	 * @param y
	 * @param win
	 */
	Alien(int x, int y, Window win) {
		super(x, y);
		this.win = win;
		this.health = defaultHealth;
		width = 50;
		height = 50;

		velocity = 1;
		velocity_y = velocity;
		velocity_x = 0;
		zakresRuchuY = y + generator.nextInt(win.size_y / 2 - y);

		URL url1 = getClass().getResource("images\\Alien2Klatka1.png");
		URL url2 = getClass().getResource("images\\Alien2Klatka2.png");
		URL url3 = getClass().getResource("images\\Alien2Klatka3.png");
		URL url4 = getClass().getResource("images\\Alien2Klatka4.png");
		try {
			Image[0] = ImageIO.read(url1);
			Image[1] = ImageIO.read(url2);
			Image[2] = ImageIO.read(url3);
			Image[3] = ImageIO.read(url4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void drawMe(Graphics2D g) {
		if (System.currentTimeMillis() - time > 1000 / 20) {
			if (i < 3)
				i++;
			else
				i = 0;
			time = System.currentTimeMillis();
		}

		g.drawImage(Image[i], x, y, null);
		g.setColor(Color.red);
		g.drawRect(x, y + win.size_y / 400, win.size_x / 20, win.size_y / 300);
		g.fillRect(x, y + win.size_y / 400, (win.size_x / 20) * health / defaultHealth, win.size_y / 300);
	}

	@Override
	public void myMotion() {

		if ((x >= zakresRuchuX && velocity_x > 0) || (x <= zakresRuchuX && velocity_x < 0)
				|| (y >= zakresRuchuY && velocity_y > 0) || (y <= zakresRuchuY && velocity_y < 0)) {
			losujKierunek();
			if (velocity_y > 0)
				zakresRuchuY = y + generator.nextInt(Math.abs(win.size_y / 2 - y) + 1);
			if (velocity_x > 0)
				zakresRuchuX = x + generator.nextInt(Math.abs(win.size_x - x) + 1);
			if (velocity_y < 0)
				zakresRuchuY = generator.nextInt(Math.abs(y) + 1);
			if (velocity_x < 0)
				zakresRuchuX = generator.nextInt(Math.abs(x) + 1);
		}
		x += velocity_x;
		y += velocity_y;
	}

	private void losujKierunek() {
		if (generator.nextBoolean()) // true - vertical, false - horizontal
		{
			if (velocity_y == 0) {
				if (generator.nextBoolean())// true -up, false - down
				{
					velocity_y = -velocity_x;
				} else {
					velocity_y = velocity_x;
				}
				velocity_x = 0;
			} else {
				velocity_y = -velocity_y;
			}
		} else {
			if (velocity_x == 0) {
				if (generator.nextBoolean())// true left, false right
				{
					velocity_x = -velocity_y;
				} else {
					velocity_x = velocity_y;
				}
				velocity_y = 0;
			} else {
				velocity_x = -velocity_x;
			}
		}
	}
}
