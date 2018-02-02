/*
 * Project Name: EE2311 Project - WordTris
 * Student Name:
 * Student ID:
 * 
 */

package WordTris;

import game.GameConsole;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;

import WordTris.Resource.resourceSetting;

public class Gem {
	// the upper-left corner of the board, reference origin point
	public static final int orgX = resourceSetting.gameBoardX;
	public static final int orgY = resourceSetting.gameBoardY;
	// the size of the gem
	public static final int w = resourceSetting.TrisSize;
	public static final int h = resourceSetting.TrisSize;
	// default position in 8x8 grid
	private int posX = 0;
	private int posY = 0;
	private int displayX = 0;
	private int displayY = 0;
	private boolean selected = false;
	private int bottomY = 0;
	private String word;

	private Image pic;
	private Image focus;

	Gem(String alphbet, int x, int y) {
		if (alphbet != null) {
			this.pic = new ImageIcon(this.getClass().getResource(resourceSetting.GetAlphbetPath(alphbet))).getImage();
			this.word = alphbet;
		}else{
			this.word = " ";
		}
		this.focus = new ImageIcon(this.getClass().getResource(resourceSetting.GetFocusPath())).getImage();
		this.posX = x;
		this.posY = y;
		this.displayX = (int) (posX * w + orgX);
		this.displayY = (int) (posY * h + orgY);
	}

	public void display() {
		GameConsole.getInstance().drawImage(this.displayX, this.displayY, pic);
		if (selected)
			GameConsole.getInstance().drawImage(this.displayX, this.displayY, focus);
	}

	public boolean isAt(Point point) {
		if (point != null)
			return (point.x > (posX * w + orgX) && point.x <= ((posX + 1) * w + orgX) && point.y > (posY * h + orgY)
					&& point.y <= ((posY + 1) * h + orgY));
		else
			return false;
	}

	public Image getPic() {
		return pic;
	}

	public void setPic(Image input) {
		this.pic = input;
	}
	
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		displayX = (int) (posX * w + orgX);
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		displayY = (int) (posY * h + orgY);
		this.posY = posY;
	}

	public int getDisplayX() {
		return displayX;
	}

	public int getDisplayY() {
		return displayY;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void toggleFocus() {
		selected = !selected;
	}

	public void MoveDownard() {
		MoveDownard(1);
	}

	public void MoveDownard(int speedRatio) {
		int speed = resourceSetting.GetSpeed() * speedRatio;
		if (displayY + speed >= bottomY) {
			displayY = bottomY;
			this.setSelected(false);
			//System.out.println("handle");
		} else {
			displayY += speed;
		}
	}

	public void SetBottomY(int y) {
		this.bottomY = (int) (y * h + orgY);
		resourceSetting.debugPrint("bottomY: " + bottomY);
	}

	public String GetWord() {
		return this.word;
	}

	public int GetCurrentArrayY() {
		return (displayY - orgY) / h;
	}

	public void SetDisplayXY(int x, int y) {
		displayX = x;
		displayY = y;
	}
}
