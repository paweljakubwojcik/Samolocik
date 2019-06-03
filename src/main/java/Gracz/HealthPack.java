package Gracz;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import Bullets.Drop;
import InterFace.MessageBox;

public class HealthPack extends Drop {

	static BufferedImage apteczka;

	private static final int DomyslnaIlosc = 1000;
	private static int ilosc = DomyslnaIlosc;

	public HealthPack(int x, int y) {
		super(x, y);
		this.width = 50;
		this.height = 50;
		try {
			URL url = getClass().getResource("/images/apteczka.png");
			apteczka = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void drawMe(Graphics2D g) {
		g.drawImage(apteczka, x, y, null);
	}

	@Override
	public void collision(Object o) {
		if (o.getClass() == Player.class) {
			new MessageBox("Health +" + ilosc, 1500, x, y);
			Player player = (Player) o;
			player.zlapaneBonusy++;
			player.health += ilosc;
			if (player.health > player.DefaultHealth)
				player.health = player.DefaultHealth;
			drops.remove(this);
		}
	}

	public static void setEasy() {
		ilosc = 3 * DomyslnaIlosc / 2;
	}

	public static void setMedium() {
		ilosc = DomyslnaIlosc;
	}

	public static void setHard() {
		ilosc = DomyslnaIlosc;
	}

}
