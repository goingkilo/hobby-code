
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
				return  (int)(100*interesting(f2) - 100*interesting(f1)); //descending order
			}
		});

		Float[] probabilities = problist.subList(0, 16).toArray(new Float[15]);

		float product = mul(probabilities, false);
		float oneMinusTerm = mul(probabilities, true);

		print("[]" + product + "," + oneMinusTerm);

		return (product / (product + oneMinusTerm));
	}
	
	static String[] pullNStore(String nub, String[] files){
		List<String> names = new ArrayList<String>();
		for(String file : files){
			String name = new File(file).getName();
			name = name.substring( 0, name.indexOf("."));
			//print(name);
			names.add(name);
			Counter.store( nub + name, Counter.getURL(file));
		}
		return names.toArray( new String[names.size()]);
	}

	public static void main(String[] args) {

		String[] dickens = {"http://www.gutenberg.org/ebooks/1400.txt.utf8",
				"http://www.gutenberg.org/ebooks/98.txt.utf8",
				"http://www.gutenberg.org/ebooks/730.txt.utf8",
				"http://www.gutenberg.org/ebooks/766.txt.utf8",
				"http://www.gutenberg.org/ebooks/580.txt.utf8",
				"http://www.gutenberg.org/ebooks/1023.txt.utf8",
				"http://www.gutenberg.org/ebooks/786.txt.utf8",
				"http://www.gutenberg.org/files/564/564-0.txt",
				"http://www.gutenberg.org/ebooks/967.txt.utf8",
				"http://www.gutenberg.org/ebooks/963.txt.utf8",
				"http://www.gutenberg.org/ebooks/883.txt.utf8",
				"http://www.gutenberg.org/ebooks/700.txt.utf8",
				"http://www.gutenberg.org/ebooks/821.txt.utf8"};
		
		String[] twain = {   "http://www.gutenberg.org/ebooks/76.txt.utf8",
				"http://www.gutenberg.org/ebooks/74.txt.utf8",
				"http://www.gutenberg.org/ebooks/10947.txt.utf8",
				"http://www.gutenberg.org/ebooks/19640.txt.utf8",
				"http://www.gutenberg.org/ebooks/30165.txt.utf8",
				"http://www.gutenberg.org/ebooks/86.txt.utf8",
				"http://www.gutenberg.org/ebooks/1837.txt.utf8",
				"http://www.gutenberg.org/ebooks/3176.txt.utf8",
				"http://www.gutenberg.org/ebooks/10135.txt.utf8",
				"http://www.gutenberg.org/ebooks/245.txt.utf8",
				"http://www.gutenberg.org/ebooks/119.txt.utf8",
				"http://www.gutenberg.org/ebooks/3177.txt.utf8",
				"http://www.gutenberg.org/ebooks/8525.txt.utf8",
				"http://www.gutenberg.org/ebooks/70.txt.utf8",
				"http://www.gutenberg.org/ebooks/3186.txt.utf8",
				"http://www.gutenberg.org/ebooks/2895.txt.utf8",
				"http://www.gutenberg.org/ebooks/142.txt.utf8",
				"http://www.gutenberg.org/ebooks/3200.txt.utf8",
				"http://www.gutenberg.org/ebooks/3178.txt.utf8",
				"http://www.gutenberg.org/ebooks/3250.txt.utf8",
				"http://www.gutenberg.org/ebooks/102.txt.utf8",
				"http://www.gutenberg.org/ebooks/3190.txt.utf8",
				"http://www.gutenberg.org/ebooks/26203.txt.utf8",
				"http://www.gutenberg.org/ebooks/18381.txt.utf8",
				"http://www.gutenberg.org/ebooks/3187.txt.utf8"};
		
		int[] dickens = new int[]{1400,98,730,   766,    580};
		int[] twains  = new int[]{76, 74, 10947, 19640,  30165 };
		
		for(int i = 0 ; i < 5 ; i++ ) {
			Counter.train( Counter.getFile(dickens[i]), true);
			System.out.println( "trained,dickens:"+ i);
		}
		
		for(int i : twains ){
			Counter.train( Counter.getFile(twain[i]), false);
			System.out.println( "trained,twain:"+ i);
		}
		float f = Counter.classify( Counter.getFile("twain1837") );
		System.out.println( "is a twain book :" + f);
	}

}
