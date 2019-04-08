import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window implements KeyListener {

	JFrame okno;
	int size_x = 800, size_y = 600;
	BufferedImage klatka;

	Player statek1;
	Thread Game;
	boolean ruch1L = false;
	boolean ruch1P = false;
	boolean ruch1D = false;
	boolean ruch1U = false;
	boolean strzal = false;

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
		Enemy.enemies.add(new Asteroid(this, 400, 20));

		Game = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					draw();
					Bullet.motion();
					Enemy.motion();

					if (ruch1L)
						statek1.moveLeft();
					if (ruch1P)
						statek1.moveRight();
					if (ruch1U)
						statek1.moveUp();
					if (ruch1D)
						statek1.moveDown();
					if(strzal)
						statek1.strzal();

					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}, "watek-1");
	}

	synchronized void draw() {
		Graphics2D g = (Graphics2D) klatka.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, size_x, size_y);
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

	@Override
	public void keyPressed(KeyEvent key) {

		if (key.getKeyCode() == KeyEvent.VK_Z) {
			strzal = true;
		} else if (key.getKeyCode() == KeyEvent.VK_LEFT) {
			ruch1L = true;
		} else if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
			ruch1P = true;
		} else if (key.getKeyCode() == KeyEvent.VK_UP) {
			ruch1U = true;
		} else if (key.getKeyCode() == KeyEvent.VK_DOWN) {
			ruch1D = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_Z) {
			strzal = false;
		} else if (key.getKeyCode() == KeyEvent.VK_LEFT) {
			ruch1L = false;
		} else if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
			ruch1P = false;
		} else if (key.getKeyCode() == KeyEvent.VK_UP) {
			ruch1U = false;
		} else if (key.getKeyCode() == KeyEvent.VK_DOWN) {
			ruch1D = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {

	}

}
