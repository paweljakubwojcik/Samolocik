package Bullets;

import java.awt.Color;
import java.awt.Graphics2D;

import AI.Asteroid;
import Gracz.Player;
import InterFace.MessageBox;

public class EnemyBullet extends Bullet {

	public EnemyBullet(int x, int y) {
		super(x, y);
	}

	void draw(Graphics2D g) {
		g.setColor(Color.green);
		g.fillOval(x, y, size, size);
	}

	@SuppressWarnings("static-access")
	@Override
	void MyMotion() {
		if (y > 0 && y < win.size_y)
			y += velocity;
		else
			try {
				bullets.remove(this);
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}

	}

	@Override
	public void collision(Object o) {

		if (o.getClass() == Player.class) {
			Player player = (Player) o;
			if (!player.shield) {
				player.health -= damage;

				player.obrazenia = true;
				player.klatkiObrazenia = 0;

				new MessageBox(Integer.toString((int) -damage), 1000, x, y, "RED");
			}
			bullets.remove(this);
		} else if (o.getClass() == Asteroid.class)
			bullets.remove(this);

	}
}
