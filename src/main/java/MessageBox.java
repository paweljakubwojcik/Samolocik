import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class MessageBox {

	static List<MessageBox> messages = new ArrayList<>();

	
	/**
	 * 
	 * @param str - wiadomosc
	 * @param czas - w milisekundach, jak dlugo wiadomosc ma byc wyswietlana
	 */
	MessageBox(String str, int czas) {

	}
	
	static void draw(Graphics2D g)
	{
		for (int i = messages.size() - 1; i >= 0; i--) {
			messages.get(i).drawMe(g);
		}
	}
	
	void drawMe(Graphics2D g)
	{
		
	}
	
}
