package napisyKoncowe;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class KoncoweAlieny {
	public static ArrayList<KoncoweAlieny> ali = new ArrayList<KoncoweAlieny>();

	private static BufferedImage Image[] = new BufferedImage[4];
	private static BufferedImage ImageRozpad[] = new BufferedImage[4];
	private int i = 0;
	long time = System.currentTimeMillis();

	private static boolean grafika = false;

	private int klatkaRozpadu;

	private int width, height;

	public boolean zacznijSmierc = false;
	private int x, y;
	private int velocity_x = 0;
	private int velocity_y = -2;

	private boolean mamDead = false;
	public static Random rand = new Random();

	KoncoweAlieny(int x, int y) {
		this.x = x;
		this.y = y;
		width = 40;
		height = 40;

		if (!grafika) {
			try {
				for (int i = 1; i <= 4; i++) {
					Image[i - 1] = ImageIO.read(getClass().getResource("/images//Alien2Klatka" + i + ".png"));
					ImageRozpad[i - 1] = ImageIO
							.read(getClass().getResource("/images//Alien2KlatkaRozpad" + i + ".png"));
				}
				grafika = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ali.add(this);
	}

	KoncoweAlieny(int x, int y, boolean mamDead) {
		this(x, y);
		this.mamDead = mamDead;
	}

	public static void draw(Graphics2D g) {
		for (int i = 0; i < ali.size() - 1; i++) {
			if (ali.size() != 0) {
				ali.get(i).drawMe(g);
			}
		}
	}

	public void drawMe(Graphics2D g) {
		// animacja
		myMotion();

		if (System.currentTimeMillis() - time > 1000 / 20) {
			if (i < 3)
				i++;
			else
				i = 0;
			if (zacznijSmierc) {
				if (klatkaRozpadu <= 4) {
					klatkaRozpadu++;
					if (klatkaRozpadu == 4) {
						try {
							ali.remove(this);
							this.finalize();
						} catch (Throwable e) {
							e.printStackTrace();
						}
					}
				} else {
					klatkaRozpadu = 0;
				}
			}
			time = System.currentTimeMillis();
		}
		if (!zacznijSmierc) {
			g.drawImage(Image[i], x, y, width, height, null);
		} else if (klatkaRozpadu != 4) {
			g.drawImage(ImageRozpad[klatkaRozpadu], x - width, y - height, width * 3, height * 3, null);
		}

	}

	public void myMotion() {
		x += velocity_x;
		y += velocity_y;
		if (y <= 100 + rand.nextInt(100) - 50 && mamDead) {
			zacznijSmierc = true;
		} else if (y < -100) {
			zacznijSmierc = true;
		}
	}

}
