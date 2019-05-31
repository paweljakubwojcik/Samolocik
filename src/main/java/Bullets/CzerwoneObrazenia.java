package Bullets;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CzerwoneObrazenia {

	public CzerwoneObrazenia() {

	}

	public static void drawRed(Graphics2D g, BufferedImage zdjecieDoZamalowania, int x, int y) {

		int w = zdjecieDoZamalowania.getWidth();
		int h = zdjecieDoZamalowania.getHeight();
		BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = dyed.createGraphics();
		AlphaComposite przez = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
		g2d.setComposite(przez);
		g2d.drawImage(zdjecieDoZamalowania, 0, 0, null);
		g2d.setComposite(AlphaComposite.SrcAtop);
		g2d.setColor(Color.RED);
		g2d.fillRect(0, 0, w, h);
		g2d.dispose();
		g.drawImage(dyed, x, y, null);

	}
}
