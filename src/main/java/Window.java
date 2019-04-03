import javax.swing.JFrame;

public class Window {

	JFrame okno;
	int size_x=800, size_y=600;
	
	Window()
	{
		okno=new JFrame("spaceinvider");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setResizable(false);
		okno.setLocationRelativeTo(null);
		okno.setSize(size_x, size_y);
		okno.setVisible(true);
	}
	
}
