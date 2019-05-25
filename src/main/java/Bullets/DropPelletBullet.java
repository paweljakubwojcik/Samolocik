package Bullets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import Gracz.Player;
import InterFace.MessageBox;

public class DropPelletBullet extends Drop {
	static BufferedImage blueammo;

	public DropPelletBullet(int x, int y) {
		super(x, y);
		this.width = 50;
		this.height = 50;
		try {
			URL url = getClass().getResource("/images//bluesuply.png");
			blueammo = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void drawMe(Graphics2D g) {
		g.drawImage(blueammo, x, y, null);

	}

	@Override
	public int[][] getPole() {
		int[][] tab = { { x, y, width, height } };
		return tab;
	}

	@Override
	public void collision(Object o) {
		if (o.getClass() == Player.class) {
			Player player = (Player) o;
			player.amunition[2] += 15;
			drops.remove(this);
		}

		new MessageBox("Pellet Bullet", 1500, x, y);

	}

}
