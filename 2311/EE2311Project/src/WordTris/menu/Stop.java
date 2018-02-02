package WordTris.menu;

import WordTris.Key.Key;
import WordTris.Resource.resourceSetting;
import WordTris.Timer.Timmer;
import game.GameConsole;

import java.awt.Color;
import java.awt.Font;

public class Stop {
	Key pressedKey = new Key();

	public void render() {
		GameConsole gc=GameConsole.getInstance();
		Timmer.stop();
		gc.clear();
		//gc.setBackground(resourceSetting.GetNormalBackgroundPath());
		gc.update();
	}
}