package WordTris.Timer;

import WordTris.Timer.Time;
import WordTris.Resource.resourceSetting;
import WordTris.TrisController;
import WordTris.WordTris;
import WordTris.Key.Key;
import WordTris.Key.movement;
import game.GameConsole;

/**
 *
 * @author user
 */
public class Timmer implements Runnable {
	Time gameTime = new Time();
	TrisController trisController = new TrisController();
	Key pressedKey = new Key();
	GameConsole console = GameConsole.getInstance();
	private static boolean running = true;
	private static Thread gameloop;

	public void setgameloop(Thread thread) {
		gameloop = thread;
	}

	public void run() {
		while (true) {
			while (running) {
				if (trisController.isEndGame()) {
					//console.setBackground(resourceSetting.GetNormalBackgroundPath());
					console.setBackground(resourceSetting.GetGameOverBackground());
					//console.setBackground(resourceSetting.GetNormalBackgroundPath());
					
					resourceSetting.debugPrint("resourceSettingresourceSettingresourceSettingresourceSetting");
					resourceSetting.soundEffectController.Play(resourceSetting.soundEffectController.playing.gameOver);
					gameTime.Stop();
					new WordTris().stop();
				} else {
					movement keyEvent = pressedKey.getkey();
					control();
					if (keyEvent == movement.ESC) {
						console.setBackground(resourceSetting.GetNormalBackgroundPath());
						gameTime.Stop();
						new WordTris().stop();
					} else {
						display(keyEvent);
						console.update();
					}
				}
			}
			while (!running) {
				control();
				new WordTris().stop();
				if (pressedKey.getkey() == movement.ESC && !trisController.isEndGame()) {
					console.setBackground(resourceSetting.GetPlayingBackgroundPath());
					gameTime.Resume();
					Timmer.resume();
				}
			}
		}
	}

	private void display(movement keyEvent) {
		console.clear();
		gameTime.display();
		trisController.display();
		if (trisController.IsNeedToEliminatingTris()) {
			trisController.EliminateTris();
		} else {
			// resourceSetting.debugPrint(message);
			trisController.KeyEventHandler(keyEvent);
		}

	}

	private void control() {
		try {
			gameloop.sleep(40);
		} catch (InterruptedException e) {

		}
	}

	public static void stop() {

		running = false;
	}

	public void start() {
		gameloop.start();
	}

	public static void resume() {
		running = true;
		//System.out.println("a");

		// gameloop = new Thread();
		// gameloop.start();
	}

}
