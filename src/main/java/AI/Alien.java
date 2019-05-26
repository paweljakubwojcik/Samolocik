package AI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Bullets.EnemyBullet;
import Program.Window;

public class Alien extends Enemy {

	static final int defaultHealth = 10*100;
	private int zakresRuchuX, zakresRuchuY;
	int velocity;
	private BufferedImage Image[] = new BufferedImage[4];
	private int i = 0;
	long time = System.currentTimeMillis();
	long czasAtak= System.currentTimeMillis();

	/**
	 * 
	 * @param x
	 * @param y
	 * @param win
	 */
	@SuppressWarnings("static-access")
	Alien(int x, int y, Window win) {
		super(x, y);
		this.win = win;
		this.health = defaultHealth;
		width = 40;
		height = 40;

		velocity = 1;
		velocity_y = velocity;
		velocity_x = 0;
		zakresRuchuY = y + generator.nextInt(win.size_y / 2 - y);

		try {
			Image[0] = ImageIO.read(getClass().getResource("/images//Alien2Klatka1.png"));
			Image[1] = ImageIO.read(getClass().getResource("/images//Alien2Klatka2.png"));
			Image[2] = ImageIO.read(getClass().getResource("/images//Alien2Klatka3.png"));
			Image[3] = ImageIO.read(getClass().getResource("/images//Alien2Klatka4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("static-access")
	@Override
	public void drawMe(Graphics2D g) {
		// animacja
		if (System.currentTimeMillis() - time > 1000 / 20) {
			if (i < 3)
				i++;
			else
				i = 0;
			time = System.currentTimeMillis();
		}

		g.drawImage(Image[i], x, y, width, height, null);
		g.setColor(new Color(255, 0, 0, 200));
		/* g.drawRect(x, y, width, height); */
		g.drawRect(x, y + win.size_y / 400, win.size_x / 20, win.size_y / 200);
		g.fillRect(x, y + win.size_y / 400, (win.size_x / 20) * (int)health / defaultHealth, win.size_y / 200);
	}

	@SuppressWarnings("static-access")
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
		
		if(System.currentTimeMillis()-czasAtak>generator.nextInt(3000)+1000){
			new EnemyBullet(x+width/2,y+height);
			czasAtak=System.currentTimeMillis();
		}
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
