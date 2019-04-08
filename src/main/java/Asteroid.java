
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Asteroid extends Enemy implements IEnemy {

	static final int defaultHealth = 4;
	BufferedImage Image;
	static String obrazy[][] = {
			{ "images//Asteroida1.png", "images//Asteroida1Damage1.png", "images//Asteroida1Damage2.png",
					"images//Asteroida1Damage3.png" },
			{ "images//Asteroida2.png", "images//Asteroida2Damage2.png", "images//Asteroida2Damage2.png",
					"images//Asteroida2Damage3.png" },
			{ "images//Asteroida3.png" } };
	int index = generator.nextInt(3);

	/**
	 * @param window
	 * 
	 */
	Asteroid(Window win) {
		super(generator.nextInt(win.size_x), -40);
		this.win = win;
		this.health = defaultHealth;
		width = generator.nextInt(70) + 30;
		height = generator.nextInt(70) + 30;
		velocity_x = generator.nextInt(2) + 1;
		velocity_y = generator.nextInt(2) + 1;

		loadGraphic();

	}

	@Override
	public void drawMe(Graphics2D g) {
		g.drawImage(Image, x, y, null);

	}

	@Override
	public void myMotion() {

		y += velocity_y;
		if (y > win.size_y)
			Enemy.enemies.remove(this);
	}

	void loadGraphic() {
		URL url = getClass().getResource(obrazy[index][defaultHealth - health]);
		try {
			Image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
