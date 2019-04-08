import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class BossPaszko extends Enemy implements IEnemy {

	BufferedImage paszko;
	
	BossPaszko(Window win, int x, int y) {
		super(x, y);
		this.win = win;
		this.x = x;
		this.y = y;

		URL url = getClass().getResource("samolot.png");
		try {
			paszko = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void drawMe(Graphics2D g) {
		
	}

	@Override
	public void myMotion() {
		
	}

}
