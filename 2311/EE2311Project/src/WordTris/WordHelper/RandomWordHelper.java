package WordTris.WordHelper;

import java.util.Random;

public class RandomWordHelper {
	private static String resultString;

	public RandomWordHelper(int lenght) {
		StringBuilder tempString = new StringBuilder();
		while (true) {
			// create new random string
			for (int counter = 0; counter < lenght; counter++) {
				tempString.append(RandomWordHelper.GetOneAlpString());
			}

			// check the string is ok
			CheckWordHelper checkWordHelperObject = new CheckWordHelper(tempString.toString());
			if (checkWordHelperObject.IsHaveWord()) {
				tempString = new StringBuilder();
				continue;
			} else {
				resultString = tempString.toString();
				break;
			}
		}
	}

	public String GetStringAt(int index) {
		return "" + resultString.charAt(index);
	}

	public static String GetOneAlpString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		int index = (int) (rnd.nextFloat() * SALTCHARS.length());
		return "" + SALTCHARS.charAt(index);
	}
}
