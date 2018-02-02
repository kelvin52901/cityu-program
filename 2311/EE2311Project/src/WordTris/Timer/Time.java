package WordTris.Timer;

import WordTris.Resource.resourceSetting;
import game.GameConsole;

public class Time {
	private enum state {
		running, stop
	}

	private long millis;
	private long hours;
	private long minutes;
	private long startTime;
	private long stopTime;
	private long seconds;
	private state currentState = state.running;
	private int displayX = resourceSetting.timerDisplayX;
	private int displayY = resourceSetting.timerDisplayY;

	public Time() {
		startTime = System.currentTimeMillis();
	}

	private String gettime() {
		switch (currentState) {
		case running:
			millis = System.currentTimeMillis() - startTime;
			break;
		case stop:
			millis = stopTime;
			break;
		}
		hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		seconds = millis / 1000;
		millis -= seconds * (1000);
		// return minutes + ":" + seconds + ":" + millis;
		return String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + ":"
				+ String.format("%03d", millis);
	}

	public void display() {
		GameConsole.getInstance().drawText(displayX, displayY, "Time: " + this.gettime());
	}

	public void Stop() {
		currentState = state.stop;
		stopTime = System.currentTimeMillis() - startTime;
	}

	public void Resume() {
		currentState = state.running;
		startTime = System.currentTimeMillis() - stopTime;
	}

	public long GetStartTime() {
		return this.startTime;
	}
}
