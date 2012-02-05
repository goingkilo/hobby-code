package soup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;

public class Counter {

    static Map<String, Integer> good = new HashMap<String, Integer>();
	static Map<String, Integer> bad = new HashMap<String, Integer>();

	static String[] specialCharacters = { ",", "#", ";", "\"", "\'", };
	static String empty = "";

	static void print(String s) {
		System.out.println(s);
	}

	static void store(String fname, String data) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(fname));
			fos.write(data.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static String getFile(String path) {
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			byte[] b = new byte[fis.available()];
			fis.read(b);
			fis.close();
			fis = null;
			return new String(b);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static String getURL(String url) {
		try {
			return Jsoup.connect(url).get().text();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	static String takeOutSpecialCharacters(String s) {
		for (String p : specialCharacters) {
			s = s.replaceAll(p, empty);
		}
		return s.toLowerCase();
	}

	static int count(Map<String, Integer> map) {
		int count = 0;
		for (Integer i : map.values()) {
			count += i;
		}
		// System.out.println(count);
		return count;
	}

	static void train(String text, boolean ham) {
		String[] tokens = text.split(" ");

		Map<String, Integer> map = ham ? good : bad;

		for (String token : tokens) {
			token = takeOutSpecialCharacters(token);
			if (map.containsKey(token)) {
				map.put(token, map.get(token) + 1);
			} else {
				map.put(token, 1);
			}
		}
	}

	static float calculateProbability(String s) {
		s = takeOutSpecialCharacters(s);

		float pSpam = 0f;

		float goodRatio = 0;
		if (good.containsKey(s)) {
			goodRatio = (float) (((float) good.get(s)) / (float) count(good));
		}
		float badRatio = 0;
		if (bad.containsKey(s)) {
			badRatio = (float) (((float) bad.get(s)) / (float) count(bad));
		}
		if (goodRatio + badRatio > 0) {
			pSpam = (badRatio / (goodRatio + badRatio));
		}

		if (pSpam > 0.99f)
			pSpam = 0.99f;
		if (pSpam < 0.1f)
			pSpam = 0.1f;

		return pSpam;

	}

	static float interesting(Float f) {
		return Math.abs(0.5f - f);
	}

	static float mul(Float[] f, boolean oneMinus) {
		float a = 1.0f;
		for (float f1 : f) {
			if (oneMinus) {
				a = a * (1 - f1);
			} else {
				a = a * f1;
			}
		}
		return a;
	}

	static float classify(String text) {
		String[] tokens = text.split(" ");

		Set<String> set = new HashSet<String>();

		for (String token : tokens) {
			set.add(takeOutSpecialCharacters(token));
		}

		List<Float> problist = new ArrayList<Float>();

		for (String token : set) {

			float f = calculateProbability(token);

			if (f > 0.5f) {
				// System.out.println( ""+ feature + ":"+ f);
				problist.add(f);
			}
		}

		Collections.sort(problist, new Comparator<Float>() {
			@Override
			public int compare(Float f1, Float f2) {
				return Math.round(interesting(f2) - interesting(f1));
			}
		});

		Float[] probabilities = problist.subList(0, 16).toArray(new Float[15]);

		float product = mul(probabilities, false);
		float oneMinusTerm = mul(probabilities, true);

		print("[]" + product + "," + oneMinusTerm);

		return (product / (product + oneMinusTerm));
	}

	public static void main(String[] args) {
		// store( "godrinktea.txt" ,
		// getURLContent("http://www.godrinktea.com/"));
		// store( "buddhaspace.txt" ,
		// getURLContent("http://buddhaspace.blogspot.com"));

		// train( getFile("godrinktea.txt"), true);
		// train( getFile("buddhaspace.txt"), false);

		// System.out.println(
		// "probability of the word being in buddhaspace is "+calculateProbability("zen")
		// );
		TwainSorter.main(null);
	}

}
