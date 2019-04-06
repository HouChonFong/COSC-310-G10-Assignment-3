
public class test {
	public static void main(String[] args) {
		String[] arr = {  "pants", "jeans", "jacket", "gloves", "shirt", "socks",
				"sneakers", "boots", "cap", "sweater", "coat","wallet", "earring", "necklace", "bracelet", "belt", "ring", "keychain",
				"glasses" };
		for (String s : arr) {
			Stemmer.spellCheck(s);
		}
		
		Stemmer.spellCheck("purchas");
	}
}
