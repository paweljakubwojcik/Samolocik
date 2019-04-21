
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Asteroid extends Enemy {

	static final int defaultHealth = 4;
	private BufferedImage Image;
	static String obrazy[][] = {
			{ "images//Asteroida1.png", "images//Asteroida1Damage1.png", "images//Asteroida1Damage2.png",
					"images//Asteroida1Damage3.png" },
			{ "images//Asteroida2.png", "images//Asteroida2Damage1.png", "images//Asteroida2Damage2.png",
					"images//Asteroida2Damage3.png" },
			{ "images//Asteroida3.png", "images//Asteroida3Damage1.png", "images//Asteroida3Damage2.png",
					"images//Asteroida3Damage3.png" },
			{ "images//Asteroida4.png", "images//Asteroida4Damage1.png", "images//Asteroida4Damage2.png",
					"images//Asteroida4Damage3.png" } };
	private int index = generator.nextInt(4);

	/**
	 * @param window
	 * 
	 */
	@SuppressWarnings("static-access")
	Asteroid(Window win) {

		super(generator.nextInt(win.size_x - 50), -100);
		this.win = win;
		this.health = defaultHealth;
		width = generator.nextInt(70) + 60;
		height = generator.nextInt(70) + 60;
		velocity_x = generator.nextInt(2) + 1;
		velocity_y = generator.nextInt(2) + 1;

		loadGraphic();

	}

	@Override
	public void drawMe(Graphics2D g) {
		g.drawImage(Image, x-width/10, y-height/10,width*10/8,height*10/8, null);
		/*g.setColor(Color.BLUE);
		g.drawOval(x, y, (width+height)/2, (width+height)/2);*/

	}

	@SuppressWarnings("static-access")
	@Override
	public void myMotion() {

		y += velocity_y;
		if (y > win.size_y)
			Enemy.enemies.remove(this);
	}

	private void loadGraphic() {
		URL url = getClass().getResource(obrazy[index][(defaultHealth - health)]);
		try {
			Image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void collision(Object o) {
		super.collision(o);
		if (health > 0)
			loadGraphic();
	}
	
	@Override
	public int[][] getPole()
	{
		int[][] tab = { { x+width/2, y+height/2, (width+height)/4 } };
		return tab;
	}
}
