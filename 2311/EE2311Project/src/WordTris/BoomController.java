package WordTris;

import java.util.ArrayList;
import WordTris.Resource.resourceSetting;

public class BoomController {
	private ArrayList<Gem> boomList = new ArrayList<Gem>();
	private final int numberOfBoom = resourceSetting.numberOfBoom;
	private final int displayXBase = resourceSetting.boomDisplayX;
	private final int displayY = resourceSetting.boomDisplayY;
	private final int boomInterval = resourceSetting.TrisSize;

	public BoomController() {
		CrearteMutiBoom(numberOfBoom);
	}

	private void CrearteMutiBoom(int number) {
		for (int counter = 0; counter < number; counter++) {
			CreateNewTris();
		}
	}

	public static Gem CreateTris(String Apl, int x, int y) {
		return new Gem(Apl, x, y);
	}

	private void CreateNewTris() {
		int x = boomList.size() * boomInterval + displayXBase;
		int y = displayY;
		BoomGem temp = new BoomGem(x, y);
		boomList.add(temp);
	}

	public Gem PopBoom() {
		if (boomList.size() != 0) {
			Gem temp = boomList.get(0);
			boomList.remove(0);
			UpdateList();
			return temp;
		} else {
			return null;
		}
	}

	public void Display() {
		for (int counter = 0; counter < boomList.size(); counter++) {
			boomList.get(counter).display();
		}
	}

	private void UpdateList() {
		for (int counter = 0; counter < boomList.size(); counter++) {
			int x = counter * boomInterval + displayXBase;
			int y = displayY;
			Gem temp = boomList.get(counter);
			temp.SetDisplayXY(x, displayY);
		}
		// for (int counter = boomList.size(); counter < numberOfBoom;
		// counter++) {
		// CreateNewTris();
		// }

	}
}
