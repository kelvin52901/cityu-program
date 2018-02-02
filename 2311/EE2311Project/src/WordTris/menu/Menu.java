package WordTris.menu;

import java.awt.Color;
import java.awt.Font;
import game.GameConsole;

public class Menu implements Setting {
	GameConsole gc = game.GameConsole.getInstance();

	public void render() {

		// Image startbutton=new
		// ImageIcon(this.getClass().getResource("/constant/start-game.png")).getImage();

		gc.show();
		gc.drawRectangle(gamew / 2, 250, 100, 50, Color.yellow);
		// gc.drawImage(gamew / 2 + 4, ((300 + 250) / 2), startbutton);
		gc.drawText(gamew / 2 + 5, ((300 + 250) / 2), "Play", new Font("Helvetica", Font.BOLD, 40), Color.BLUE);
		gc.update();
	}

}
