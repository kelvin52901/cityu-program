/*
 * Project Name: EE2311 Project - WordTris
 * Student Name:
 * Student ID:
 * 
 */
package WordTris;

import WordTris.Resource.resourceSetting;
import WordTris.Timer.Timmer;
import WordTris.Timer.Time;
import WordTris.menu.Menu;
import WordTris.menu.Stop;
import game.GameConsole;

/**
 *
 * @author Your Name
 */
public class WordTris {
	private static enum STATE {
		MENU, GAME, STOP
	}

	private STATE state = STATE.GAME;

	Time a = new Time();
	Menu p = new Menu();
	private GameConsole console = GameConsole.getInstance();

	private void start() {
		// console.clear();
		if (state == STATE.GAME) {
			console.setBackground(resourceSetting.GetPlayingBackgroundPath());
			console.show();
			Timmer t1 = new Timmer();
			Thread gameThread = new Thread(t1);
			t1.setgameloop(gameThread);
			t1.start();
			resourceSetting.soundEffectController.Play(resourceSetting.soundEffectController.playing.BGM);
			console.update();
		} else if (state == STATE.MENU) {
			p.render();
		} else if (state == STATE.STOP) {
			new Stop().render();
		}
	}

	public void stop() {
		state = STATE.STOP;
		new Stop().render();
	}

	public static void main(String[] args) {
		// TODO code application logic here
		WordTris WordTrisMain = new WordTris();
		WordTrisMain.start();

	}
}
