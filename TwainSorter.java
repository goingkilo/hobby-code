
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwainSorter {

	static String[] dickens = {"http://www.gutenberg.org/ebooks/1400.txt.utf8",
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
	
	static String[] twain = {   "http://www.gutenberg.org/ebooks/76.txt.utf8",
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
	
	static void print(String s) {System.out.println(s);}
 
	static String[] pullNStore(String nub, String[] files){
		List<String> names = new ArrayList<String>();
		for(String file : files){
			String name = file.split("/")[4];
			name = name.substring( 0, name.indexOf("."));
			print(name);
			names.add(name);
			Counter.store( nub + name, Counter.getURL(file));
		}
		return names.toArray( new String[names.size()]);
	}
	
	public static void main(String[] args) {
		String[] storedDickens = pullNStore( "dickens", Arrays.copyOfRange(dickens,0,5));
		String[] storedTwain = pullNStore( "twain", Arrays.copyOfRange(twain,0,5));
	}
}
