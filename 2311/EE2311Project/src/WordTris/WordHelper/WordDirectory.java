package WordTris.WordHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import WordTris.Resource.resourceSetting;

public class WordDirectory {
	private static BufferedReader bufferedReader;
	private static HashMap<String, Integer> mapWordDirectory = new HashMap<String, Integer>();
	
	static {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(resourceSetting.GetWordDirectoryPath());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		bufferedReader = new BufferedReader(fileReader);
		try {
			while (bufferedReader.ready()) {
				mapWordDirectory.put(bufferedReader.readLine().toUpperCase(), 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean IsAWord(String word) {
           
		return mapWordDirectory.containsKey(word);
	}
}
