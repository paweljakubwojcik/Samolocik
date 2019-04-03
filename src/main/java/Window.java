import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
<<<<<<< HEAD
=======
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
>>>>>>> branch 'master' of https://github.com/7Adrian/Samolocik.git

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Window implements KeyListener {

	JFrame okno;
	int size_x = 800, size_y = 600;
	BufferedImage klatka = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_RGB);
	BufferedImage statek;
	Player statek1 = new Player();

	Window() {
		okno = new JFrame("space invider");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setResizable(false);
		okno.setLocationRelativeTo(null);
		okno.setSize(size_x, size_y);
		okno.setLayout(null);
		okno.setVisible(true);
		okno.addKeyListener(this);

<<<<<<< HEAD
		Thread watek = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

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
=======
		URL url = getClass().getResource("samolot.png");
		try {
			statek = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		draw();

>>>>>>> branch 'master' of https://github.com/7Adrian/Samolocik.git
	}

	synchronized void draw() {
		while (true) {
			Graphics2D g = (Graphics2D) klatka.getGraphics();
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, size_x, size_y);
			g.drawImage(statek, statek1.x, statek1.y, null);
			g.dispose();
			drawklatka();
		}
	}

	void drawklatka() {
		Graphics2D g2d = (Graphics2D) okno.getGraphics();
		g2d.drawImage(klatka, 0, 0, null);
		g2d.dispose();

	}

	void strzal() {

	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			System.out.println("strzal");

		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
