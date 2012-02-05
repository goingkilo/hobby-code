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
/**
* simple naive bayes classifier to find out
* if a given text was written mark twain or not
*/
public class CounterOOP {

	private Map<String, Integer> good ;
	private Map<String, Integer> bad ;

	final static String[] specialCharacters = { ",", "#", ";", "\"", "\'", };
	final static String empty = "";

	final static float PROBABILITY_UPPER_LIMIT = 0.99f;
	final static float PROBABILITY_LOWER_LIMIT = 0.1f;
	
	public CounterOOP(){
		good = new HashMap<String, Integer>();
		bad = new HashMap<String, Integer>();
	}
	
	/*
	 * utility method, creates a file denoted by fname, 
         * and writes the data to it
	 * @param String
  	 * @param String
	 */
	private void store(String fname, String data) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(fname));
			fos.write(data.getBytes());
			fos.close();
		} catch (Exception e) {
			throw e;
			
		} finally {
			try {
				fos.close();
			}
			catch(Exception e1){}
		}
	}

	/*
	 * utility method, reads and returns the contents of a given file path
	 * @param String
         * @return String
	 */
	private String getFile(String path) {
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

	/*
	 * downloads and returns the contents of a given url
	 * @param String
         * @param String 
	 */
	String downloadURL(String url) {
		try {
			return Jsoup.connect(url).get().text();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * removes special characters from the string
         * and converts it to lower case
	 */
	String simplifyToken(String s) {
		for (String p : specialCharacters) {
			s = s.replaceAll(p, empty);
		}
		return s.toLowerCase();
	}

	/*
	 * sum up the values of the given map
	 */
	int count(Map<String, Integer> map) {
		int count = 0;
		for (Integer i : map.values()) {
			count += i;
		}
		return count;
	}

	/*
	 * count and store word counts of given text
	 */
	void train(String text, boolean first) {
		String[] tokens = text.split(" ");

		Map<String, Integer> map = first ? good : bad;

		for (String token : tokens) {
			token = simplifyToken(token);
			
			if (map.containsKey(token)) {
				map.put(token, map.get(token) + 1);
			} else {
				map.put(token, 1);
			}
		}
	}

	/*
	 * calculate probability for a single word 
	 * given pre-calculated(trained) probabilities
	 */
	float calculateProbability(String s) {
		s = simplifyToken(s);

		float pCat1Probability = 0f;

		float cat1Ratio = 0;
		if (good.containsKey(s)) {
			cat1Ratio = (float) (((float) good.get(s)) / (float) count(good));
		}
		float cat2Ratio = 0;
		if (bad.containsKey(s)) {
			cat2Ratio = (float) (((float) bad.get(s)) / (float) count(bad));
		}
		if (cat1Ratio + cat2Ratio > 0) {
			pCat1Probability = (cat2Ratio / (cat1Ratio + cat2Ratio));
		}

		//is there a math function to fix range of a number between upper & lower bounds ?
		System.out.println( "debug:" + pCat1Probability);
		
		if (pCat1Probability > PROBABILITY_UPPER_LIMIT) {
			pCat1Probability = PROBABILITY_UPPER_LIMIT;
		}
		
		if (pCat1Probability < PROBABILITY_LOWER_LIMIT) {
			pCat1Probability = PROBABILITY_LOWER_LIMIT;
		}

		return pCat1Probability;

	}

	/*
	 * we only want to look at words which have a probabiity
	 * greater than 0.5
	 */
	boolean isInteresting(Float f) {
		return Math.abs(0.5f - f) > 0;
	}

	/**
	 * depending of the boolean, either returns product
	 * of the elements of the array, or returns
	 * product of 1 minus each element of the array
	 * @param Float[] f
	 * @param boolean oneMinus
	 * @return float 
	 */
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

	/**
	 * calculate probability of the 
	 * top 15 
	 * @param text
	 * @return float
	 */
	float classify(String text) {
		String[] words = text.split(" ");

		Set<String> set = new HashSet<String>();
		for (String word : words) {
			set.add(simplifyToken(word));
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
				return (int)( 100*f2 - 100*f1 );	//descending
			}
		});

		//get the first fifteen
		Float[] probabilities = lProbabilities.subList(0, 16).toArray(new Float[15]);

		float product = product(probabilities, false);
		float oneMinusTerm = product(probabilities, true);

		System.out.println("[]" + product + "," + oneMinusTerm);

		return (product / (product + oneMinusTerm));
//		return product;
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

