package WordTris.Resource;

import java.awt.Font;

public class resourceSetting {

	private enum mode {
		Eclipse, NetBean
	}

	private static boolean debug = false;
	private static mode CurrentMode = mode.Eclipse;
	private static String constantParentPath;
	private static String alphabetPath = "/Alphabet";
	private static String playingBackgroundPath = "/BackGround/wood-background-2.jpg";
	private static String normualBackground = "/BackGround/giphy.gif";
	private static String gameOverBackground = "/BackGround/gameover.gif";
	private static String wordDirectoryPath = "/Word/words";
	private static String soundEffectPath = "/match.wav";
	private static String BGMPath = "/1019145.wav";
	private static String gameOverMusicPath = "/smb_mariodie.wav";
	private static String boomPath = "/explosionSmall.gif";
	public final static int startGameRandomTris = 5;
	public final static int gameRow = 10;
	public final static int gameColumn = 20;
	public final static int middleX = gameRow / 2 - startGameRandomTris / 2;
	public final static int middleY = gameColumn / 2 - 1;
	private static int speed = 2;
	private static int defaultSpeed = speed;
	public final static String eliminatingGIFFullPath[] = new String[3];
	public final static int timerDisplayX = 250;
	public final static int timerDisplayY = 150;
	public final static int scoreDisplayX = 250;
	public final static int scoreDisplayY = 300;
	public final static int storeEliminatedTrisX = 250;
	public final static int storeEliminatedTrisY = 150;
	public final static int storeEliminatedTrisInterval = 20;
	public final static int nextTrisDisplayX = 250;
	public final static int nextTrisDisplayY = 40;
	public final static int boomDisplayX = 250;
	public final static int boomDisplayY = 70;
	public final static int numberOfBoom = 3;
	public final static int TrisSize = 28;
	public final static int gameBoardX = 440;
	public final static int gameBoardY = 20;
	public final static int autoDropTimeInSec = 10;
	public final static Font font = new Font("Verdana", Font.BOLD, 20);

	static {
		switch (CurrentMode) {
		case Eclipse:
			constantParentPath = "/constant";
			wordDirectoryPath = "src" + constantParentPath + wordDirectoryPath;
			soundEffectPath = "src" + constantParentPath + soundEffectPath;
			BGMPath = "src" + constantParentPath + BGMPath;
			gameOverMusicPath = "src" + constantParentPath + gameOverMusicPath;
			for (int counter = 0; counter < eliminatingGIFFullPath.length; counter++) {
				eliminatingGIFFullPath[counter] = constantParentPath + "/starSmall-" + counter + ".png";
			}

			break;
		case NetBean:
			constantParentPath = "/assets";
			for (int counter = 0; counter < eliminatingGIFFullPath.length; counter++) {
				eliminatingGIFFullPath[counter] = constantParentPath + "/starSmall-" + counter + ".png";
				;
			}
			break;
		default:
			System.exit(999);
			break;
		}
	}

	public static String GetSoundEffectPath() {
		return soundEffectPath;
	}

	public static String GetBGMPathh() {
		return BGMPath;
	}

	public static String GetGameOverMusicPath() {
		return gameOverMusicPath;
	}

	public static String GetAlphbetPath(String alphabet) {
		return constantParentPath + alphabetPath + "/" + alphabet.toUpperCase() + ".jpg";
	}

	public static String GetBoomPath() {
		return constantParentPath + boomPath;
	}

	public static String GetPlayingBackgroundPath() {
		return constantParentPath + playingBackgroundPath;
	}

	public static String GetNormalBackgroundPath() {
		return constantParentPath + normualBackground;
	}

	public static String GetGameOverBackground() {
		return constantParentPath + gameOverBackground;
	}

	public static String GetFocusPath() {
		return constantParentPath + "/focus.png";
	}

	public static String GetWordDirectoryPath() {
		return wordDirectoryPath;
	}

	public static boolean isDebug() {
		return debug;
	}

	public static void debugPrint(String message) {
		if (isDebug()) {
			System.out.println(message);
		}
	}

	public static void ResetSpeed() {
		speed = defaultSpeed;
	}

	public static int GetSpeed() {
		return speed;
	}

	public static void SetSpeed(int speed) {
		if (resourceSetting.speed == defaultSpeed) {
			resourceSetting.speed *= speed;
		}
	}

	public static class soundEffectController {
		public static enum playing {
			soundEffect, BGM, gameOver;
		}

		private static Music soundEffect;
		private static Music BGM;
		private static Music gameOver;
		static {
			soundEffect = new Music(resourceSetting.GetSoundEffectPath(), false);
			BGM = new Music(resourceSetting.GetBGMPathh(), true);
			gameOver = new Music(resourceSetting.GetGameOverMusicPath(), true);
		}

		public static void Play(playing state) {
			switch (state) {
			case soundEffect:
				// SoundThread soundThread = new SoundThread();
				// Thread gameThread = new Thread(soundThread);
				// soundThread.setPlayer(soundEffect);
				soundEffect.play();
				break;
			case BGM:
				BGM.play();
				break;
			case gameOver:
				BGM.stop();
				gameOver.play();
				break;
			default:
				break;

			}
		}
	}

}
