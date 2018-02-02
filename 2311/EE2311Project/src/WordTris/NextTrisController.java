package WordTris;

import java.util.ArrayList;

import WordTris.Resource.resourceSetting;
import WordTris.WordHelper.RandomWordHelper;

public class NextTrisController {
	private ArrayList<Gem> nextTrisList = new ArrayList<Gem>();
	private final int numberOfTirs = 3;
	private final int displayXBase = resourceSetting.nextTrisDisplayX;
	private final int displayY = resourceSetting.nextTrisDisplayY;
	private final int TrisInterval = resourceSetting.TrisSize;

	
	public NextTrisController() {
		CrearteMutiGem(numberOfTirs);
	}

	private void CrearteMutiGem(int number) {
		for (int counter = 0; counter < number; counter++) {
			CreateNewTris();
		}
	}

	public static Gem CreateTris(String Apl, int x, int y) {
		return new Gem(Apl, x, y);
	}

	private void CreateNewTris() {
		Gem temp = new Gem(RandomWordHelper.GetOneAlpString(), 0, 0);
		int x = nextTrisList.size() * TrisInterval + displayXBase;
		int y = displayY;
		temp.SetDisplayXY(x, y);
		nextTrisList.add(temp);
	}

	public Gem PopTris() {
		Gem temp = nextTrisList.get(0);
		nextTrisList.remove(0);
		UpdateList();
		return temp;
	}

	public void Display() {
		for (int counter = 0; counter < nextTrisList.size(); counter++) {
			nextTrisList.get(counter).display();
		}
	}

	private void UpdateList() {
		for (int counter = 0; counter < nextTrisList.size(); counter++) {
			int x = counter * TrisInterval + displayXBase;
			int y = displayY;
			Gem temp = nextTrisList.get(counter);
			temp.SetDisplayXY(x, displayY);
		}
		for (int counter = nextTrisList.size(); counter < numberOfTirs; counter++) {
			CreateNewTris();
		}

	}
}
