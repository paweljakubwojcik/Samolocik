import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyBullet extends Bullet {

	EnemyBullet(int x, int y) {
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	@Override
	public void collision(Object o) {

		if (o.getClass() == Player.class) {
			Player player = (Player) o;
			player.health -= damage;
			bullets.remove(this);
		} else if (o.getClass() == Asteroid.class)
			bullets.remove(this);

	}
}
