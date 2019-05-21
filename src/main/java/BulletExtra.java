import java.awt.Color;
import java.awt.Graphics2D;

public class BulletExtra extends EnemyBullet {

	
	static int size = 20; //kazdy rodzaj kuli musi miec swoj statyczny rozmiar
	
	public BulletExtra(int x, int y) {
		super(x, y);
		damage=10;

	}

	synchronized void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillOval(x, y, size, size);
	}

}
