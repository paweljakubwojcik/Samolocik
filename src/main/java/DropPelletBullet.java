import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class DropPelletBullet extends Drop {
	static BufferedImage blueammo;

	DropPelletBullet(int x, int y) {
		super(x, y);
		this.width = 50;
		this.height = 50;
		try {
			URL url = getClass().getResource("images//bluesuply.png");
			blueammo = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	void drawMe(Graphics2D g) {
		g.drawImage(blueammo, x, y, null);

	}

	@Override
	public int[][] getPole() {
		int[][] tab = { { x, y, width, height } };
		return tab;
	}

	@Override
	void collision(Object o) {
		if (o.getClass() == Player.class) {
			Player player = (Player) o;
			player.amunition[2] += 15;
			drops.remove(this);
		}

		new MessageBox("Pellet Bullet", 1500, x, y);

	}

}
