package Bullets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import Gracz.Player;
import InterFace.MessageBox;

public class DropExtraBullet extends Drop {

	static BufferedImage greenammo;

	public DropExtraBullet(int x, int y) {
		super(x, y);
		this.width = 50;
		this.height = 50;
		try {
			URL url = getClass().getResource("/images//greensuply.png");
			greenammo = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void drawMe(Graphics2D g) {
		g.drawImage(greenammo, x, y, null);

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
			player.amunition[1] += 30;
			drops.remove(this);
		}

		new MessageBox("Extra Bullet", 1500, x, y);

	}

}
