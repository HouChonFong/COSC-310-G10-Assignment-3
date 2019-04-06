import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import edu.mit.jwi.Dictionary;

public class findstate {
	public static short findState(String input) {
		// To state = 20
		for (int i = 0; i < main.keywordLen(11); i++) {
			if (input.contains(main.getKeyword(11, i)))
				return 20;
		}
		// To state = 0
		for (int i = 0; i < main.keywordLen(10); i++) {
			if (input.contains(main.getKeyword(10, i)))
				return 0;
		}
		// Generic correct (for state = 4, 7, 9, 15)
		for (int i = 0; i < main.keywordLen(8); i++) {
			if (input.contains(main.getKeyword(8, i)))
				if (main.getState() == 4 ||main.getState() == 7 ||main.getState() == 9 ||main.getState() == 15)
					return 18;
		}
		// Generic wrong (for state = 4, 7, 9, 15)
		for (int i = 0; i < main.keywordLen(9); i++) {
			if (input.contains(main.getKeyword(9, i))) {
				if (main.getState() == 4)
					return 21;
				if (main.getState() == 7)
					return 22;
				if (main.getState() == 9)
					return 23;
				if (main.getState() == 15)
					return 0;
			}
		}
		// From 0 to 1
		for (int i = 0; i < main.keywordLen(0); i++) {
			if (main.getState() == 0 && input.contains(main.getKeyword(0, i)))
				return 1;
		}
		// From 1 to 2(has item), 8(not in stock), 10(no item)
		for (String s : main.clothingItems) {
			if (main.getState() == 1 && (input.contains(s) || input.equals(s))) {
				if (Math.random() > 0.5)
					return 2;
				else
					return 8;
			}
		}
		for (String s : main.accessoriesItems) {
			if (main.getState() == 1 && (input.contains(s) || input.equals(s))) {
				if (Math.random() > 0.5)
					return 24;
				else
					return 8;
			}
		}
		if (main.getState() == 1)
			return 10;
		// From 2 to 3
		for (int i = 0; i < main.keywordLen(1); i++) {
			if ((main.getState() == 2 || main.getState() == 24) && input.contains(main.getKeyword(1, i)))
				return 3;
		}
		// From 3, 21 to 4(valid), 5(invalid)
		if (main.getState() == 3 || main.getState() == 21) {
			for (char c : input.toCharArray()) {
				if (input.length() != 16 || !Character.isDigit(c)) {
					return 5;
				}
				return 4;
			}
		}
		// From 2 to 6
		for (int i = 0; i < main.keywordLen(2); i++) {
			if ((main.getState() == 2 || main.getState() == 24) && input.contains(main.getKeyword(2, i)))
				return 6;
		}
		// From 6 to 7
		if (main.getState() == 6)
			return 7;
		// From 8, 14 to 9
		if (main.getState() == 8 || main.getState() == 14)
			return 9;
		// From 0 to 11
		for (int i = 0; i < main.keywordLen(3); i++) {
			if (main.getState() == 0 && input.contains(main.getKeyword(3, i)))
				return 11;
		}
		// From 0 to 12
		for (int i = 0; i < main.keywordLen(4); i++) {
			if (main.getState() == 0 && input.contains(main.getKeyword(4, i)))
				return 12;
		}
		// From 12 to 13(has order), 16(no order)
		if (main.getState() == 12) {
			if (Math.random() > 0.5)
				return 13;
			else
				return 16;
		}
		// From 13 to 14
		for (int i = 0; i < main.keywordLen(5); i++) {
			if (main.getState() == 13 && input.contains(main.getKeyword(5, i)))
				return 14;
		}
		// From 13 to 15
		for (int i = 0; i < main.keywordLen(6); i++) {
			if (main.getState() == 13 && input.contains(main.getKeyword(6, i)))
				return 15;
		}
		// From 0 to 17
		for (int i = 0; i < main.keywordLen(7); i++) {
			if (main.getState() == 0 && input.contains(main.getKeyword(7, i)))
				return 17;
		}
		
		// From 17 to 18
		if (main.getState() == 17)
			return 18;
		// From 22 to 7
		if (main.getState() == 22)
			return 7;
		// From 23 to 9
		if (main.getState() == 23)
			return 9;
		String[] words = input.split(" ");
		for(int i = 0; i < words.length; i++) {
			words[i] = words[i].replaceAll("[^a-zA-Z]", "");
		}
		return findState(words, 0);
	}
	public static short findState(String[] input, int type) {
		String[] words = new String[0];
		if(type == 0) {
			try {
				ArrayList<String> temp = new ArrayList<>();
				String wnhome = System.getenv ("WNHOME");
				String path = wnhome + File.separator + "dict";
				URL url = new URL("file", null , path);
				IDictionary dict = new Dictionary(url);
				dict.open();
				for(String e : input) {
					IIndexWord idxWord = dict.getIndexWord (e, POS.NOUN);
					IWordID wordID = idxWord.getWordIDs ().get(0) ; // 1st meaning
					IWord word = dict.getWord(wordID);
					ISynset synset = word.getSynset();
					for(IWord w : synset.getWords())
						temp.add(w.getLemma());
				}
				words = new String[temp.size()];
				for(int n = 0; n < temp.size(); n++) {
					words[n] = temp.get(n);
				}
			}
			catch(Exception e) {
				
			}
		}
		else{
			words = new String[input.length];
			for(int i = 0; i < words.length; i++) {
				words[i] = Stemmer.spellCheck(input[i]);
			}
		}
		for(int x = 0; x < words.length; x++) {
		// To state = 20
		for (int i = 0; i < main.keywordLen(11); i++) {
			if (words[x].contains(main.getKeyword(11, i)))
				return 20;
		}
		// To state = 0
		for (int i = 0; i < main.keywordLen(10); i++) {
			if (words[x].contains(main.getKeyword(10, i)))
				return 0;
		}
		// Generic correct (for state = 4, 7, 9, 15)
		for (int i = 0; i < main.keywordLen(8); i++) {
			if (words[x].contains(main.getKeyword(8, i)))
				if (main.getState() == 4 ||main.getState() == 7 ||main.getState() == 9 ||main.getState() == 15)
					return 18;
		}
		// Generic wrong (for state = 4, 7, 9, 15)
		for (int i = 0; i < main.keywordLen(9); i++) {
			if (words[x].contains(main.getKeyword(9, i))) {
				if (main.getState() == 4)
					return 21;
				if (main.getState() == 7)
					return 22;
				if (main.getState() == 9)
					return 23;
				if (main.getState() == 15)
					return 0;
			}
		}
		// From 0 to 1
		for (int i = 0; i < main.keywordLen(0); i++) {
			if (main.getState() == 0 && words[x].contains(main.getKeyword(0, i)))
				return 1;
		}
		// From 1 to 2(has item), 8(not in stock), 10(no item)
		for (String s : main.clothingItems) {
			if (main.getState() == 1 && (words[x].contains(s))) {
				if (Math.random() > 0.5)
					return 2;
				else
					return 8;
			}
		}
		for (String s : main.accessoriesItems) {
			if (main.getState() == 1 && (words[x].contains(s))) {
				if (Math.random() > 0.5)
					return 24;
				else
					return 8;
			}
		}
		if (main.getState() == 1)
			return 10;
		// From 2 to 3
		for (int i = 0; i < main.keywordLen(1); i++) {
			if ((main.getState() == 2 || main.getState() == 24) && words[x].contains(main.getKeyword(1, i)))
				return 3;
		}
		// From 3, 21 to 4(valid), 5(invalid)
		if (main.getState() == 3 || main.getState() == 21) {
			for (char c : words[x].toCharArray()) {
				if (words[x].length() != 16 || !Character.isDigit(c)) {
					return 5;
				}
				return 4;
			}
		}
		// From 2 to 6
		for (int i = 0; i < main.keywordLen(2); i++) {
			if ((main.getState() == 2 || main.getState() == 24) && words[x].contains(main.getKeyword(2, i)))
				return 6;
		}
		// From 6 to 7
		if (main.getState() == 6)
			return 7;
		// From 8, 14 to 9
		if (main.getState() == 8 || main.getState() == 14)
			return 9;
		// From 0 to 11
		for (int i = 0; i < main.keywordLen(3); i++) {
			if (main.getState() == 0 && words[x].contains(main.getKeyword(3, i)))
				return 11;
		}
		// From 0 to 12
		for (int i = 0; i < main.keywordLen(4); i++) {
			if (main.getState() == 0 && words[x].contains(main.getKeyword(4, i)))
				return 12;
		}
		// From 12 to 13(has order), 16(no order)
		if (main.getState() == 12) {
			if (Math.random() > 0.5)
				return 13;
			else
				return 16;
		}
		// From 13 to 14
		for (int i = 0; i < main.keywordLen(5); i++) {
			if (main.getState() == 13 && words[x].contains(main.getKeyword(5, i)))
				return 14;
		}
		// From 13 to 15
		for (int i = 0; i < main.keywordLen(6); i++) {
			if (main.getState() == 13 && words[x].contains(main.getKeyword(6, i)))
				return 15;
		}
		// From 0 to 17
		for (int i = 0; i < main.keywordLen(7); i++) {
			if (main.getState() == 0 && words[x].contains(main.getKeyword(7, i)))
				return 17;
		}
		
		// From 17 to 18
		if (main.getState() == 17)
			return 18;
		// From 22 to 7
		if (main.getState() == 22)
			return 7;
		// From 23 to 9
		if (main.getState() == 23)
			return 9;
		}
		if(type == 0) return findState(input, 1);
		else {
			main.setbkState(main.getState());
			return 19;
		}
	}
}
