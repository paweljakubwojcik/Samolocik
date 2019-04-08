import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class BossPaszko extends Enemy implements IEnemyBoss {

	BufferedImage paszko;

	BossPaszko(Window win, int x, int y) {
		super(x, y);
		this.win = win;
		this.x = x;
		this.y = y;
		this.velocity_x = 4;
		this.velocity_y = 2;

		URL url = getClass().getResource("paszko.png");
		try {
			paszko = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void drawMe(Graphics2D g) {
		g.drawImage(paszko, x, y, null);
	}

	@Override
	public void myMotion() {
		x += velocity_x;
		if (generator.nextBoolean())
			y += velocity_y;
		else
			y -= velocity_y;
		
		if(y<20) {
			y=20;
			velocity_y=-velocity_y;
		} else if(y>400) {
			y=400;
			velocity_y=-velocity_y;
		}
		
		if(x<0) {
			x=0;
			velocity_x=-velocity_x;
		} else if(x+paszko.getWidth()>win.size_x) {
			velocity_x=-velocity_x;
		}
		
	}

	@Override
	public void strzal(String rodzaj) {
		
		
	}

}
