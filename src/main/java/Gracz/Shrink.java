package Gracz;

import java.awt.Color;
import java.awt.Graphics2D;

import Bullets.Drop;
import InterFace.MessageBox;

public class Shrink extends Drop{
	
	static long time = 5000;

	public Shrink(int x, int y) {
		super(x, y);
		this.width = 50;
		this.height = 50;
	}

	@Override
	protected void drawMe(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);

		
	}

	@Override
	public void collision(Object o) {
		if (o.getClass() == Player.class) {
			new MessageBox("Shrink", 2000, x, y);
			Player player = (Player) o;
			player.zlapaneBonusy++;
			player.shrink();
			
			drops.remove(this);
		}
		
	}

}
