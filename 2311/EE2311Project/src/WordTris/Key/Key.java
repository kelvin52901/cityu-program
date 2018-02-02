package WordTris.Key;

import com.sun.glass.events.KeyEvent;

import game.GameConsole;

public class Key {
	GameConsole gc = game.GameConsole.getInstance();

	public movement getkey() {
		int key = gc.getPressedKey();
		switch (key) {
		case KeyEvent.VK_LEFT:
			//Tris.SetSelectedPosition(ContriollSetting.movement.Left);
			return movement.Left;
		case KeyEvent.VK_RIGHT:
			//Tris.SetSelectedPosition(ContriollSetting.movement.Right);
			return movement.Right;
		case KeyEvent.VK_DOWN:
			//Tris.SetSelectedPosition(ContriollSetting.movement.Down);
			return movement.Down;
		case KeyEvent.VK_ENTER:
			return movement.Enter;
			//Tris.SelectedTrisMoveDownard(12, true);
		case KeyEvent.VK_SPACE:
			return movement.Space;
		case KeyEvent.VK_ESCAPE:
			return movement.ESC;
		}
		return movement.NoKey;
	}
}
