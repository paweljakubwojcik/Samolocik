import java.awt.Color;
import java.awt.Graphics2D;

public class Shield extends Drop {

	static long time = 30000;

	public Shield(int x, int y) {
		super(x, y);
	this.width=50;
	this.height=50;
	}

	@Override
	void drawMe(Graphics2D g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, width, height);

	}

	@Override
	void collision(Object o) {
		if (o.getClass() == Player.class) {
			new MessageBox("Shield Activated",2000,x,y);
			Player player = (Player) o;
			player.shield = true;
			player.timeShield = System.currentTimeMillis();
			drops.remove(this);
		}

	}
}