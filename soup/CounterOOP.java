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

public class CounterOOP {

    Map<String, Integer> category1 ;
	Map<String, Integer> category2 ;

	final static String[] specialCharacters = { ",", "#", ";", "\"", "\'", };
	final static String empty = "";

	final static float PROBABILITY_UPPER_LIMIT = 0.99f;
	final static float PROBABILITY_LOWER_LIMIT = 0.1f;
	
	public CounterOOP(){
		category1 = new HashMap<String, Integer>();
		category2 = new HashMap<String, Integer>();
	}
	
	static void print(String s) {
		System.out.println(s);
	}

	void store(String fname, String data) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(fname));
			fos.write(data.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			}
			catch(Exception e1){}
		}
	}

	static String getFile(String path) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(path));
			byte[] b = new byte[fis.available()];
			fis.read(b);
			fis.close();
			fis = null;
			return new String(b);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				fis.close();
			}
			catch(Exception e1){}
		}
	}

	String downloadURL(String url) {
		try {
			return Jsoup.connect(url).get().text();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	String takeOutSpecialCharacters(String s) {
		for (String p : specialCharacters) {
			s = s.replaceAll(p, empty);
		}
		return s.toLowerCase();
	}

	int count(Map<String, Integer> map) {
		int count = 0;
		for (Integer i : map.values()) {
			count += i;
		}
		return count;
	}

	void train(String text, boolean first) {
		String[] tokens = text.split(" ");

		Map<String, Integer> map = first ? category1 : category2;

		for (String token : tokens) {
			token = takeOutSpecialCharacters(token);
			
			if (map.containsKey(token)) {
				map.put(token, map.get(token) + 1);
			} else {
				map.put(token, 1);
			}
		}
	}

	float calculateProbability(String s) {
		s = takeOutSpecialCharacters(s);

		float pCat1Probability = 0f;

		float cat1Ratio = 0;
		if (category1.containsKey(s)) {
			cat1Ratio = (float) (((float) category1.get(s)) / (float) count(category1));
		}
		float cat2Ratio = 0;
		if (category2.containsKey(s)) {
			cat2Ratio = (float) (((float) category2.get(s)) / (float) count(category2));
		}
		if (cat1Ratio + cat2Ratio > 0) {
			pCat1Probability = (cat2Ratio / (cat1Ratio + cat2Ratio));
		}

		//is there a math function to fix range of a number between upper & lower bounds ? 
		if (pCat1Probability > PROBABILITY_UPPER_LIMIT)
			pCat1Probability = PROBABILITY_UPPER_LIMIT;
		
		if (pCat1Probability < PROBABILITY_LOWER_LIMIT)
			pCat1Probability = PROBABILITY_LOWER_LIMIT;

		return pCat1Probability;

	}

	//we are interested in values greater than 0.5
	boolean isInteresting(Float f) {
		return Math.abs(0.5f - f) > 0;
	}

	float product(Float[] f, boolean oneMinus) {
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

	float classify(String text) {
		String[] words = text.split(" ");

		Set<String> set = new HashSet<String>();

		for (String word : words) {
			set.add(takeOutSpecialCharacters(word));
		}

		List<Float> lProbabilities = new ArrayList<Float>();

		for (String word : set) {

			float f = calculateProbability(word);
			if (isInteresting(f)) {
				lProbabilities.add(f);
			}
		}

		Collections.sort(lProbabilities, new Comparator<Float>() {
			@Override
			public int compare(Float f1, Float f2) {
				return (int)( 100*f1 - 100*f2 );
			}
		});

		Float[] probabilities = lProbabilities.subList(0, 16).toArray(new Float[15]);

		float product = product(probabilities, false);
		float oneMinusTerm = product(probabilities, true);

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

