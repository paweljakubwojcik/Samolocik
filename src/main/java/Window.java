import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Window implements KeyListener {

	JFrame okno;
	int size_x = 800, size_y = 600;
	BufferedImage klatka = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_ARGB);
	BufferedImage statek;
	Player statek1;
	boolean ruch1L = false;
	boolean ruch1P = false;

	Window() {
		okno = new JFrame("space invider");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setResizable(false);
		okno.setLocationRelativeTo(null);
		okno.setSize(size_x, size_y);
		okno.setLayout(null);
		okno.setVisible(true);
		okno.addKeyListener(this);
		
		statek1 = new Player(400, 500);

		URL url = getClass().getResource("samolot.png");
		try {
			statek = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread watek = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					draw();
					
					if (ruch1L)
						statek1.moveLeft();
					if (ruch1P)
						statek1.moveRight();

					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		watek.start();
	}

	synchronized void draw() {
		Graphics2D g = (Graphics2D) klatka.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, size_x, size_y);
		statek1.drawBullets();
		g.drawImage(statek, statek1.x, statek1.y, null);
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

		if (key.getKeyCode() == KeyEvent.VK_SPACE) {
			statek1.strzal();
		} else if (key.getKeyCode() == KeyEvent.VK_LEFT) {
			ruch1L = true;
		} else if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
			ruch1P = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent key) {

		if (key.getKeyCode() == KeyEvent.VK_LEFT) {
			ruch1L = false;
		} else if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
			ruch1P = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {

	}

}
