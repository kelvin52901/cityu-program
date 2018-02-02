package WordTris;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

import WordTris.Key.movement;
import WordTris.Resource.resourceSetting;
import WordTris.ScoreAndStoreEliminatedTrisHelper.ScoreAndStoreEliminatedTrisHelper;
import WordTris.Timer.Time;
import WordTris.WordHelper.CheckWordHelper;
import WordTris.WordHelper.RandomWordHelper;
import game.GameConsole;

public class TrisController {
	private Gem trisArray[][] = new Gem[resourceSetting.gameRow][resourceSetting.gameColumn];
	private Gem SelectedTris = null;
	private int[] checkY = new int[resourceSetting.gameRow];
	private boolean eliminatingTris = false;
	private ArrayList<ArrayList<Integer>> eiminateList = new ArrayList<ArrayList<Integer>>();
	private Time eliminatingTrisTimer = new Time();
	private Time dropTrisTimer = new Time();
	private int displayingEliminatePNGCounter = 0;
	private ScoreAndStoreEliminatedTrisHelper scoreAndStoreEliminatedTrisController = new ScoreAndStoreEliminatedTrisHelper();
	private NextTrisController nextTrisController = new NextTrisController();
	private BoomController boomController = new BoomController();
	private int autoDropTimeInSec = resourceSetting.autoDropTimeInSec;
	private boolean isPlayedSoundEffect = false;
	private boolean isEndGame = false;

	public TrisController() {
		// Create the Tris
		RandomWordHelper wordHelper = new RandomWordHelper(resourceSetting.startGameRandomTris);
		for (int counter = 0; counter < resourceSetting.startGameRandomTris; counter++) {
			int x = resourceSetting.middleX + counter;
			int y = resourceSetting.middleY;
			String Apl = wordHelper.GetStringAt(counter);
			trisArray[x][y] = NextTrisController.CreateTris(Apl, x, y);
		}
		SetCheckY();
		CreateANewRandomTrisAtTop();
	}

	public void display() {
		for (int counterRow = 0; counterRow < trisArray.length; counterRow++) {
			for (int counterColumn = 0; counterColumn < trisArray[counterRow].length; counterColumn++) {
				if (trisArray[counterRow][counterColumn] != null) {
					trisArray[counterRow][counterColumn].display();
				}
			}
		}
		nextTrisController.Display();
		scoreAndStoreEliminatedTrisController.Display();
		boomController.Display();
	}

	private void CreateANewRandomTrisAtTop() {
		int poistion = new Random().nextInt(resourceSetting.gameRow);
		while (checkY[poistion] < 0) {
			poistion = new Random().nextInt(resourceSetting.gameRow);
		}
		Gem temp = nextTrisController.PopTris();
		temp.setPosX(poistion);
		temp.setPosY(0);
		temp.SetBottomY(checkY[poistion]);
		temp.setSelected(true);
		SelectedTris = temp;
		trisArray[poistion][0] = temp;
	}

	public void KeyEventHandler(movement movement) {
		// resourceSetting.debugPrint("" + movement);
		int updatedX = 0;
		int updatedArrayX = 0;
		boolean isNeedNupdate = false;
		switch (movement) {
		case Left:
			if (SelectedTris.getPosX() != 0) {
				updatedX = SelectedTris.getPosX() - 1;
				updatedArrayX = SelectedTris.getPosX() - 1;
				int currentY = SelectedTris.GetCurrentArrayY();
				// resourceSetting.debugPrint("" + currentY + ", " +
				// checkY[updatedX]);
				if (currentY >= checkY[updatedX]) {
					isNeedNupdate = false;
				} else {
					isNeedNupdate = true;
				}
			}
			break;
		case Right:
			if (SelectedTris.getPosX() != resourceSetting.gameRow - 1) {
				updatedX = SelectedTris.getPosX() + 1;
				updatedArrayX = SelectedTris.getPosX() + 1;
				int currentY = SelectedTris.GetCurrentArrayY();
				resourceSetting.debugPrint("" + currentY + ", " + checkY[updatedX]);
				if (currentY >= checkY[updatedX]) {
					isNeedNupdate = false;
				} else {
					isNeedNupdate = true;
				}
			}
			break;
		case Enter:
			this.SelectedTrisMoveDownard(15, true);
			return;
		case Down:
			this.SelectedTrisMoveDownard(10, false);
			return;
		case Space:
			// ReplaceSelectedTris(boomController.PopBoom());
			// this.SelectedTrisMoveDownard(10, false);
			return;
		case NoKey:
			this.SelectedTrisMoveDownard();
		default:
			break;
		}
		if (isNeedNupdate) {
			if (movement == movement.Left || movement == movement.Right) {
				int originalX = SelectedTris.getPosX();
				SelectedTris.setPosX(updatedX);
				SelectedTris.SetBottomY(checkY[updatedX]);
				trisArray[updatedArrayX][SelectedTris.getPosY()] = SelectedTris;
				trisArray[originalX][SelectedTris.getPosY()] = null;
			}
		}
	}

	private void ReplaceSelectedTris(Gem replaceTo) {
		if (replaceTo != null) {

			SelectedTris = null;
			trisArray[SelectedTris.getPosX()][0] = null;
			CreateANewRandomTrisAtTop();
			display();

			// // int displayX = SelectedTris.getDisplayX();
			// // int displayY = SelectedTris.getDisplayY();
			// int arrayX = SelectedTris.getPosX();
			// int displayX = trisArray[arrayX][0].getDisplayX();
			// int displayY = trisArray[arrayX][0].getDisplayY();
			// resourceSetting.debugPrint("displayX: " + displayX + " displayY:
			// " + displayY);
			// replaceTo.SetDisplayXY(displayX, displayY);
			//
			// displayX = replaceTo.getDisplayX();
			// displayY = replaceTo.getDisplayY();
			// resourceSetting.debugPrint("2222 displayX: " + displayX + "
			// displayY: " + displayY);
			//
			// replaceTo.setSelected(true);
			// SelectedTris = replaceTo;
			// trisArray[arrayX][0] = replaceTo;
		}
	}

	private void SelectedTrisMoveDownard(int speedRatio, boolean isEnter) {
		if (isEnter) {
			resourceSetting.SetSpeed(speedRatio);
			SelectedTris.MoveDownard();
		} else {
			SelectedTris.MoveDownard(speedRatio);
		}
		if (!SelectedTris.isSelected()) {
			trisArray[SelectedTris.getPosX()][checkY[SelectedTris.getPosX()]] = SelectedTris;
			trisArray[SelectedTris.getPosX()][SelectedTris.getPosY()] = null;

			checkAndMinimizeWord();
			if (!IsNeedToEliminatingTris()) {
				if ((System.currentTimeMillis() - dropTrisTimer.GetStartTime()) / 1000 >= autoDropTimeInSec) {
					UpdateRowAndColumnAllDropOne();
					dropTrisTimer = new Time();
				}
				UpdateRowAndColumn();
				SetCheckY();
				checkAndMinimizeWord();
				if (!IsNeedToEliminatingTris()) {
					CreateANewRandomTrisAtTop();
					resourceSetting.ResetSpeed();
				}
			}
		}
	}

	private void SelectedTrisMoveDownard() {
		SelectedTrisMoveDownard(1, false);
	}

	private void SetCheckY() {
		int stopCounter = 0;
		for (int counterRow = 0; counterRow < trisArray.length; counterRow++) {
			checkY[counterRow] = -1;
			for (int counterColumn = 0; counterColumn <= resourceSetting.middleY; counterColumn++) {
				if (trisArray[counterRow][counterColumn] == null) {
					checkY[counterRow] = counterColumn;
				} else {
					break;
				}
			}
			if (checkY[counterRow] == 0)
			{
				stopCounter++;
			}
		}
		if (stopCounter == resourceSetting.gameRow)
		{
			//need to stop
			isEndGame = true;
		}
	}
	

	private void checkAndMinimizeWord() {
		String[] checkStringArayRow = new String[resourceSetting.gameRow];
		String[] checkStringArayColumn = new String[resourceSetting.gameColumn];

		for (int counter = 0; counter < checkStringArayRow.length; counter++) {
			checkStringArayRow[counter] = "";
		}
		for (int counter = 0; counter < checkStringArayColumn.length; counter++) {
			checkStringArayColumn[counter] = "";
		}

		for (int counterRow = 0; counterRow < trisArray.length; counterRow++) {
			for (int counterColumn = 0; counterColumn < trisArray[counterRow].length; counterColumn++) {
				String word;
				if (trisArray[counterRow][counterColumn] != null) {
					word = trisArray[counterRow][counterColumn].GetWord();
				} else {
					word = " ";
				}
				if (resourceSetting.isDebug()) {
					// System.out.print("[" + counterRow + "][" + counterColumn
					// + "]" + word + " ");
				}
				checkStringArayRow[counterRow] += word;
				checkStringArayColumn[counterColumn] += word;
			}
			if (resourceSetting.isDebug()) {
				// System.out.println();
			}
		}
		FindMatchForRow(checkStringArayRow);
		FindMatchForColumn(checkStringArayColumn);
	}

	private void FindMatchForRow(String[] stringArray) {
		for (int counter = 0; counter < stringArray.length; counter++) {
			CheckWordHelper checkWordHelperObject = new CheckWordHelper(stringArray[counter]);
			if (checkWordHelperObject.IsHaveWord()) {// hv word
				int xStar = checkWordHelperObject.GetPositionStart();
				int xEnd = checkWordHelperObject.GetPositionEnd();
				String tempword = checkWordHelperObject.GetResultword();
				for (int counterRemove = xStar; counterRemove < xEnd; counterRemove++) {
					AddDataToEiminateList(counter, counterRemove);
					eliminatingTris = true;
					// trisArray[counter][counterRemove] = null;
				}
				scoreAndStoreEliminatedTrisController.Add(tempword);
				break;
			}
		}
	}

	private void FindMatchForColumn(String[] stringArray) {
		for (int counter = 0; counter < stringArray.length; counter++) {
			CheckWordHelper checkWordHelperObject = new CheckWordHelper(stringArray[counter]);
			// System.out.println(checkStringArayColumn[counter]);
			if (checkWordHelperObject.IsHaveWord()) {// hv word
				int yStar = checkWordHelperObject.GetPositionStart();
				int yEnd = checkWordHelperObject.GetPositionEnd();
				String tempword = checkWordHelperObject.GetResultword();
				for (int counterRemove = yStar; counterRemove < yEnd; counterRemove++) {
					AddDataToEiminateList(counterRemove, counter);
					eliminatingTris = true;
					// trisArray[counterRemove][counter] = null;
				}
				scoreAndStoreEliminatedTrisController.Add(tempword);
				break;
			}
		}
	}

	private void AddDataToEiminateList(int x, int y) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(x);
		temp.add(y);
		eiminateList.add(temp);
	}

	public boolean IsNeedToEliminatingTris() {
		return this.eliminatingTris;
	}

	public void EliminateTris() {
		// System.out.println("StartEliminatingTris");
		if (!isPlayedSoundEffect) {
			resourceSetting.soundEffectController.Play(resourceSetting.soundEffectController.playing.soundEffect);
			isPlayedSoundEffect = true;
		}
		// System.out.println("eliminatingTris");
		if (displayingEliminatePNGCounter >= 3) {
			isPlayedSoundEffect = false;
			eliminatingTris = false;
			displayingEliminatePNGCounter = 0;
			for (int counterEliminat = 0; counterEliminat < eiminateList.size(); counterEliminat++) {
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp = eiminateList.get(counterEliminat);
				int x = temp.get(0);
				int y = temp.get(1);
				trisArray[x][y] = null;
			}
			eiminateList = new ArrayList<ArrayList<Integer>>();
			UpdateRowAndColumn();
			SetCheckY();
			checkAndMinimizeWord();
			if (!IsNeedToEliminatingTris()) {
				CreateANewRandomTrisAtTop();
				resourceSetting.ResetSpeed();
			}
			return;
		} else {
			for (int counterEliminat = 0; counterEliminat < eiminateList.size(); counterEliminat++) {
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp = eiminateList.get(counterEliminat);
				int x = temp.get(0);
				int y = temp.get(1);
				int poistionX = trisArray[x][y].getDisplayX();
				int poistionY = trisArray[x][y].getDisplayY();
				Image eliminatPNG = null;
				resourceSetting.debugPrint("displayingEliminatePNGCounter: " + displayingEliminatePNGCounter);
				eliminatPNG = new ImageIcon(this.getClass()
						.getResource(resourceSetting.eliminatingGIFFullPath[displayingEliminatePNGCounter])).getImage();
				GameConsole.getInstance().drawImage(poistionX, poistionY, eliminatPNG);
			}
			if ((System.currentTimeMillis() - eliminatingTrisTimer.GetStartTime()) >= 75) {
				// System.out.println("----Update-------");
				eliminatingTrisTimer = new Time();
				displayingEliminatePNGCounter++;
			}
		}

	}

	private void UpdateRowAndColumn() {
		for (int counterRow = 0; counterRow < resourceSetting.gameRow; counterRow++) {
			int emptyTrisCounter = 0;
			for (int counterColumnCheckNull = resourceSetting.middleY; counterColumnCheckNull >= 0; counterColumnCheckNull--) {
				if (trisArray[counterRow][counterColumnCheckNull] == null && counterColumnCheckNull != 0) {
					// at the head
					if (trisArray[counterRow][counterColumnCheckNull - 1] != null) {
						resourceSetting.debugPrint("trisArray[counterRow][counterColumnCheckNull - 1] != null");
						for (int counterUpdateColumn = counterColumnCheckNull; counterUpdateColumn > 0; counterUpdateColumn--) {
							if (trisArray[counterRow][counterUpdateColumn - 1] == null) {
								break;
							}
							Gem temp = trisArray[counterRow][counterUpdateColumn - 1];
							temp.setPosY(counterUpdateColumn + emptyTrisCounter);
							trisArray[counterRow][counterUpdateColumn + emptyTrisCounter] = temp;
							trisArray[counterRow][counterUpdateColumn - 1] = null;
						}
					}
					emptyTrisCounter++;
				}
			}

			int emptyTrisCounterDown = 0;
			for (int counterColumnCheckNull = resourceSetting.middleY
					+ 1; counterColumnCheckNull < resourceSetting.gameColumn; counterColumnCheckNull++) {
				if (trisArray[counterRow][counterColumnCheckNull] == null
						&& counterColumnCheckNull != resourceSetting.gameColumn - 1) {
					// at the head
					if (trisArray[counterRow][counterColumnCheckNull + 1] != null) {
						for (int counterUpdateColumn = counterColumnCheckNull; counterUpdateColumn < resourceSetting.gameColumn
								- 1; counterUpdateColumn++) {
							if (trisArray[counterRow][counterUpdateColumn + 1] == null) {
								break;
							}
							Gem temp = trisArray[counterRow][counterUpdateColumn + 1];
							temp.setPosY(counterUpdateColumn - emptyTrisCounterDown);
							trisArray[counterRow][counterUpdateColumn - emptyTrisCounterDown] = temp;
							trisArray[counterRow][counterUpdateColumn + 1] = null;
						}
					}
				}
				emptyTrisCounterDown++;
			}
		}

	}

	public boolean isEndGame() {
		return isEndGame;
	}

	private void UpdateRowAndColumnAllDropOne() {
		resourceSetting.debugPrint("UpdateRowAndColumn");
		for (int counterRow = 0; counterRow < resourceSetting.gameRow; counterRow++) {
			for (int counterColumn = resourceSetting.gameColumn - 1; counterColumn > 0; counterColumn--) {
				if (trisArray[counterRow][resourceSetting.gameColumn - 1] != null) {
					isEndGame = true;
					return;
				} else if (trisArray[counterRow][counterColumn - 1] != null) {
					Gem temp = trisArray[counterRow][counterColumn - 1];
					temp.setPosY(counterColumn);
					trisArray[counterRow][counterColumn] = temp;
					trisArray[counterRow][counterColumn - 1] = null;
				}
			}
		}
	}
}