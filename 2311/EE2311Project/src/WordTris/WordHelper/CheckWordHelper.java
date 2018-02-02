package WordTris.WordHelper;

import WordTris.Resource.resourceSetting;

public class CheckWordHelper {
	// private Map<String, Integer> positionXY= new HashMap<String, Integer>();
	private int positionStart;
	private int positionEnd;
	private String word;
	private boolean haveWord = false;

	public CheckWordHelper(String sentence) {
		if (!(sentence.trim().isEmpty() || sentence == null)) {
			StringBuilder tempSentence = new StringBuilder(sentence.toUpperCase());
			for (int counterStart = 0; counterStart < sentence.length(); counterStart++) {
				if (tempSentence.charAt(counterStart) == ' ') {
					continue;
				}
				for (int counterEnd = counterStart + 2; counterEnd <= sentence.length(); counterEnd++) {
					if (tempSentence.charAt(counterEnd - 1) == ' ') {
						break;
					}
					String checkWord = tempSentence.substring(counterStart, counterEnd);
					if (WordDirectory.IsAWord(checkWord)) {
						resourceSetting.debugPrint("word - " + checkWord);
						positionStart = counterStart;
						positionEnd = counterEnd;
						word = checkWord;
						haveWord = true;
						break;
					}
				}
			}
		}
	}

	public boolean IsHaveWord() {
		return this.haveWord;
	}

	public int GetPositionStart() {
		return this.positionStart;
	}

	public int GetPositionEnd() {
		return this.positionEnd;
	}

	public String GetResultword() {
		return this.word;
	}
}