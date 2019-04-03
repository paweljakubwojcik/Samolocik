import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Window implements KeyListener{

	JFrame okno;
	int size_x=800, size_y=600;
	
	
	Window()
	{
		okno=new JFrame("space invider");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setResizable(false);
		okno.setLocationRelativeTo(null);
		okno.setSize(size_x, size_y);
		okno.setVisible(true);
		okno.addKeyListener(this);
	}
	
	
	
	synchronized void draw()
	{
		
	}

	void strzal()
	{
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
		
		if(arg0.getKeyCode()==KeyEvent.VK_SPACE)
		{
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
