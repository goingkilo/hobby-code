
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
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

	static String[] specialCharacters = { ",", "#", ";", "\"", "\'" };
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
			URL u = new URL(url);
			URLConnection uc = u.openConnection();
			InputStream is = uc.getInputStream();
			byte[] b =  new byte[is.available()];
			is.read(b);
			is.close();
			return new String(b);
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	static String[] pullNStore(String nub, String[] urls){
		List<String> names = new ArrayList<String>();
		for(String file : urls){
			String name = new File(file).getName();
			name = nub + name.substring( 0, name.indexOf("."));
			names.add(name);
			
			if( new File(name).exists() && new File(name).isFile() ) {
				print( "file exists :" + name);
				continue;
			}
			
			store( name, Counter.getURL(file));
			print( "downloading :"+file +" as " + name);
		}
		return names.toArray( new String[names.size()]);
	}

	static String takeOutSpecialCharacters(String s) {
		for (String p : specialCharacters) {
			s = s.replaceAll(p, empty);
		}
		return s.toLowerCase().trim();
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
			if( f <= 0 ) 	{
				continue;
			}
			if ( interesting(f) > 0 ) {
				problist.add(f);
			}
		}

		Collections.sort(problist, new Comparator<Float>() {
			@Override
			public int compare(Float f1, Float f2) {
				return  (int)(100*f1 - 100*f2);
			}
		});

		for(Float fi : problist) {
			print( ".) " + fi);
		}


		Float[] probabilities = null;
		if( problist.size() > 15) {
			//probabilities = problist.subList(0, 16).toArray(new Float[15]);
			List<Float> tmpList = problist.subList(0,8);
			tmpList.addAll( problist.subList(problist.size()-7,problist.size()-1) );
			probabilities = tmpList.toArray(new Float[tmpList.size()]);
		}
		else {
			probabilities = problist.toArray(new Float[problist.size()]);
		}

		for(Float fi : probabilities) {
			print( "_ " + fi);
		}

		float product = mul(probabilities, false);
		float oneMinusTerm = mul(probabilities, true);

		print("[/]" + product + "," + oneMinusTerm);

		return (product / (product + oneMinusTerm));
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
		
		
		String[] storedDickens = pullNStore( "dickens", Arrays.copyOfRange(dickens,0,5));
		String[] storedTwain = pullNStore( "twain", Arrays.copyOfRange(twain,0,5));
		
		if( args[0].equals("dickens") ) {
			pullNStore( "dickens", new String[]{ dickens[Integer.parseInt(args[1])] } );
		}
		else {
			pullNStore( "twain", new String[]{ twain[Integer.parseInt(args[1])] } );
		}
		
		for(String s : storedDickens) {
			train( getFile(s), true);
			print( "trained,dickens:"+ s);
		}
		for(String s : storedTwain) {
			train( getFile(s), false);
			print( "trained,twain:"+ s);
		}
		
		float f = classify( getFile("twain1837") );
		print( "is a twain book :" + f);
	}

}
