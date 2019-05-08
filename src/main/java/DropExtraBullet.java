import java.awt.Color;
import java.awt.Graphics2D;

public class DropExtraBullet extends Drop {

	DropExtraBullet(int x, int y) {
		super(x, y);
		this.width=50;
		this.height=50;
		
	}

	@Override
	void drawMe(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
		
	}

	@Override
	public int[][] getPole() {
		int[][] tab = {{x,y,width,height}};
		return tab;
	}

	@Override
	void collision(Object o) {
		if(o.getClass()==Player.class)
		{
			Player player = (Player) o;
			player.amunition[1]+=30;
			if(player.health>player.DefaultHealth) player.health=player.DefaultHealth;
			drops.remove(this);
		}
		
	}

}
