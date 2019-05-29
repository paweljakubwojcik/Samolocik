package Bullets;

import java.awt.Color;
import java.awt.Graphics2D;

public class GranadeExplosion extends Bullet {

	int size2;
	int opacity = 255;
	int expantionV = 6;
	int[][] tab = new int[1][3];

	public GranadeExplosion(int x, int y) {
		super(x, y);
		this.size2 = size;
		damage = generator.nextInt(60)+100;
		System.out.println("detonate");
	}

	synchronized void draw(Graphics2D g) {

		if (opacity > 0)
			for (int i = 0; i < 10; i++) {
				g.setColor(new Color(255, 255 - 25 * i, 0, opacity));
				g.drawOval(x + 2 * i, y + 2 * i, size2 - 2 * i, size2 - 2 * i);
			}

	}

	public void MyMotion() {
		tab[0][0] = x + size2 / 2;
		tab[0][1] = y + size2 / 2;
		tab[0][2] = size2 / 2;
		x -= expantionV / 2;
		y -= expantionV / 2;
		size2 += expantionV;
		opacity -= 8;

		if (opacity <= 0)
			Bullet.bullets.remove(this);
	}

	@Override
	public int[][] getPole() {

		return tab;
	}


}
