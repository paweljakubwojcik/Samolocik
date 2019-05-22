import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class MessageTypingIn {

	int opacity = 255;
	static Window win = Samolotoszczalec.win;
	static List<MessageTypingIn> messages = new ArrayList<>();

	String message;
	List<String> shownMessage = new ArrayList<>();

	long time = System.currentTimeMillis();
	// tempo pisania liter i tempo fadeoutu napisu w milisekundach
	long tempoLiter = 100, fadeOutTempo = 5000;
	int linijka = 0, literka = 0;
	boolean isFadeOutBegun=false; // gdy caly napis zostanie napisany i zacznie sie fadeOut to zmieni sie na true 
	@SuppressWarnings("static-access")
	static int x = win.size_x / 4, y = win.size_y / 4;

	/**
	 * 
	 * @param s
	 *            - message that will be written live on screen. Next line is
	 *            defined by '/n'. To insert pause in typed text write '%'
	 *            followed by wanted duration in miliseconds
	 */
	MessageTypingIn(String s) {
		this.message = s;
		messages.add(this);
	}
	
	static void draw(Graphics2D g)
	{
		for(int i=0;i<messages.size();i++)
			messages.get(i).drawMe(g);
	}
	
	static void skip()
	{
		for(int i=0;i<messages.size();i++)
			messages.get(i).skipMe();
	}

	public void drawMe(Graphics2D g) {

		g.setFont(new Font(null, Font.TYPE1_FONT, 20));
		g.setColor(new Color(0, 255, 0, opacity));

		for (int i = 0; i < shownMessage.size(); i++) {
			g.drawString(shownMessage.get(i), x, y + i * g.getFontMetrics().getHeight());
		}

		if (literka < message.length()) {
			animate();

		} else if (opacity > 0) {
			if(!isFadeOutBegun) isFadeOutBegun=true;
			double czasKtoryMinal = (double) (System.currentTimeMillis() - time);
			opacity = (int) (255 - (czasKtoryMinal / fadeOutTempo) * 255); // fadeOut
																			// tym razem liniowy
		} else
			messages.remove(this);

	}

	//zwraca true jesli napootka znak nowj lini
	private boolean isNextLine(int l) {
		if (l < message.length() + 2) {
			if (message.charAt(l) == '/' && message.charAt(l + 1) == 'n') {
				return true;
			}
		}
		return false;
	}

	String buffor = "";
	long pauza = 0;

	private void animate() {
			//time - lokalny czas, czas ostatniego wykonania funkcjii
		if (System.currentTimeMillis() - time > tempoLiter + pauza && message.length() > literka) {

			if (message.charAt(literka) == '%') {
				literka++;
				while ((int) message.charAt(literka) >= 48 && (int) message.charAt(literka) <= 57) {
					pauza += pauza * 10 + ((int) message.charAt(literka) - 48);
					literka++;
				}
			} else {
				pauza = 0;
				buffor += message.charAt(literka);
			}

			if (isNextLine(literka)) {
				literka += 2;
				linijka++;
				pauza += 500;
				buffor = "";
			}

			if (shownMessage.size() > linijka)
				shownMessage.remove(linijka);

			shownMessage.add(buffor);
			literka++;
			time = System.currentTimeMillis();
		}
	}
	
	void skipMe()
	{
		for(int i=shownMessage.size()-1;i>=0;i--)
		shownMessage.remove(i);
		buffor="";
		literka=0;
		linijka=0;
		
		while(literka<message.length());
		{
			if(message.charAt(literka)=='%')
			{
				literka++;
				while ((int) message.charAt(literka) >= 48 && (int) message.charAt(literka) <= 57) {
					literka++;
				}
			}
			
			if(isNextLine(literka))
			{
				shownMessage.add(buffor);
				linijka++;
				literka+=2;
				buffor="";
			}
			
			buffor+=message.charAt(literka);
			literka++;
			
		}
		shownMessage.add(buffor);
		
		
	}
}
