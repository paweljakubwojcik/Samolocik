import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class HealthPack extends Drop{
	
	static BufferedImage apteczka; 

	HealthPack(int x, int y) {
		super(x, y);
		this.width=50;
		this.height=50;
		System.out.print("hey");
		try {
			URL url = getClass().getResource("apteczka.png");
			apteczka = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	void drawMe(Graphics2D g) {
		g.drawImage(apteczka, x, y, null);
		
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
			player.health+=30;
			if(player.health>player.DefaultHealth) player.health=player.DefaultHealth;
			drops.remove(this);
		}
		
	}

}
