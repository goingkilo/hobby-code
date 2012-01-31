
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;

public class Counter {

    static Map<String,Integer> good = new HashMap<String,Integer>();
    static Map<String,Integer> bad  = new HashMap<String,Integer>();

    static String[] punctuation = {",","#",";","\"","\'",};
    static String empty  = "";

    static void store(String fname,String data){
        try {
            FileOutputStream fos = new FileOutputStream(new File( fname));
            fos.write(data.getBytes());
            fos.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    static String getFileContent(String path){
        try {
            FileInputStream fis = new FileInputStream(new File(path));
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();
            fis  = null;
            return new String(b);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    static String getURLContent(String url){
        try {
            return Jsoup.connect(path).get().text();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String sanitise(String s){
        for(String p : punctuation) {
            s = s.replaceAll(p,empty);
        }
        return s.toLowerCase();
    }

    static int count(Map<String,Integer> map){
        int count = 0;
        for( Integer i : map.values() ){
            count += i;
        }
        System.out.println(count);
        return count;
    }

    static void train(String text, boolean ham) {
        String[] tokens = text.split(" ");

        Map<String,Integer> map = ham ? good : bad;

        for(String token : tokens ){
            token = sanitise(token);
            if(map.containsKey(token)){
                map.put( token, map.get(token) + 1 );
            }
            else {
                map.put( token, 1 );
            }
        }
    }

    static float calculateProbability(String s) {
        s = sanitise(s);

        float rGood = 0;
        if( good.containsKey(s) ) {
            rGood = (float)(((float)good.get(s)) / (float)count(good));
        }
        float rBad  = 0;
        if( bad.containsKey(s) ){
            rBad = (float)(((float)bad.get(s)) / (float)count(bad));
        }
        System.out.println( good.get(s)+"/"+bad.get(s));
        return (rBad == 0 && rGood == 0) ? 0.0f : (rBad / (rGood + rBad));
    }


    public static void main(String[] args){
        //store( "godrinktea.txt" , getURLContent("http://www.godrinktea.com/"));
        //store( "buddhaspace.txt" , getURLContent("http://buddhaspace.blogspot.com"));

        train( getFileContent("godrinktea.txt"), true);
        train( getFileContent("buddhaspace.txt"), false);

        System.out.println(  "probability of the word being in buddhaspace is "+calculateProbability("zen") );
    }

}
