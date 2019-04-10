import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

public class Window implements KeyListener {

	JFrame okno;
	int size_x = 800, size_y = 600;
	int tloY = -size_y;
	BufferedImage klatka;

	Player statek1;
	Thread Game;
	boolean ruch1L = false;
	boolean ruch1P = false;
	boolean ruch1D = false;
	boolean ruch1U = false;
	boolean strzal = false;

	BossPaszko paszkow;

	BufferedImage imc = new BufferedImage(size_x , size_y*2, BufferedImage.TYPE_INT_ARGB);
	BufferedImage im1 = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_ARGB);
	BufferedImage im2 = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_ARGB);

	Window() {
		okno = new JFrame("space invider");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setResizable(false);
		okno.setLocationRelativeTo(null);
		okno.setSize(size_x, size_y);
		okno.setLayout(null);
		okno.setVisible(true);
		okno.addKeyListener(this);
		klatka = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_ARGB);
		statek1 = new Player(this, 400, 500);

		EnemyGenerator generator = new EnemyGenerator(this);

		// Enemy.enemies.add(paszkow = new BossPaszko(this, 400, 20));

		losujtlo(im1);
		losujtlo(im2);
		scaltla(im1, im2);

		Game = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					draw();
					Bullet.motion();
					Enemy.motion();
					generator.generate();

					if (ruch1L)
						statek1.moveLeft();
					if (ruch1P)
						statek1.moveRight();
					if (ruch1U)
						statek1.moveUp();
					if (ruch1D)
						statek1.moveDown();
					if (strzal)
						statek1.strzal();

					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					tloY++;
					// paszkow.AI();
				}

			}
		}, "watek-1");
	}

	synchronized void draw() {
		Graphics2D g = (Graphics2D) klatka.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, size_x, size_y);
		g.drawImage(imc, null, 0, tloY);
		Bullet.drawBullets(g);
		Enemy.draw(g);
		statek1.draw(g);
		g.dispose();
		drawklatka();
	}

	void drawklatka() {
		Graphics2D g2d = (Graphics2D) okno.getGraphics();
		g2d.drawImage(klatka, 0, 0, null);
		g2d.dispose();
	}

	void losujtlo(BufferedImage im) {
		Random los = new Random();
		Graphics2D g2d = (Graphics2D) im.getGraphics();
		g2d.setColor(new Color(0, 0, 0, 255));
		g2d.fillRect(0, 0, 1280, 720);
		g2d.setColor(Color.WHITE);
		int wielkosc = 0, xxx = 0, yyy = 0;
		for (int i = 0; i < 40; i++) { // 20
			wielkosc = los.nextInt(15); // 20
			xxx = los.nextInt(1280);
			yyy = los.nextInt(720);

			g2d.fillOval(xxx, yyy, wielkosc, wielkosc);
		}
		g2d.setColor(Color.WHITE);
	}

	void scaltla(BufferedImage i1, BufferedImage i2) {
		Graphics2D g2d = (Graphics2D) imc.getGraphics();
		g2d.drawImage(i1, 0, 0, null);
		g2d.drawImage(i2, 0, size_y, null);
	}

	@Override
	public void keyPressed(KeyEvent key) {
		int klucz = key.getKeyCode();
		if (klucz == KeyEvent.VK_Z) {
			strzal = true;
		} else if (klucz == KeyEvent.VK_LEFT) {
			ruch1L = true;
		} else if (klucz == KeyEvent.VK_RIGHT) {
			ruch1P = true;
		} else if (klucz == KeyEvent.VK_UP) {
			ruch1U = true;
		} else if (klucz == KeyEvent.VK_DOWN) {
			ruch1D = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent key) {
		int klucz = key.getKeyCode();
		if (klucz == KeyEvent.VK_Z) {
			strzal = false;
		} else if (klucz == KeyEvent.VK_LEFT) {
			ruch1L = false;
		} else if (klucz == KeyEvent.VK_RIGHT) {
			ruch1P = false;
		} else if (klucz == KeyEvent.VK_UP) {
			ruch1U = false;
		} else if (klucz == KeyEvent.VK_DOWN) {
			ruch1D = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {

	}

}
