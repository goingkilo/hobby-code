package cascade1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tweet {

	String user, date, text;
	Date d;
	boolean retweet;
	String original;

	public Tweet(String user, String date, String text) {
		this.user = user;
		this.date = date;
		this.text = text;

		try {
			d = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss ZZZZZ").parse(date);
		} //                          Sat, 21 Jul 2012 11:51:36 +0000
		catch (ParseException e) {
			System.out.println( "  >>" + e.getMessage() + ","+date);
		}
	}

	public Tweet(){}


	public float convertDate() {
		//System.out.println(user);
		//Date date = tweet.d;
		//return date.getYear()*365 + date.getMonth()* + date.getDay() + date.getHours() ;
		return Date.parse( d.toGMTString());
	}

	public static void main(String[] args) throws Exception {
		Date a = new SimpleDateFormat
				("EEE, dd MMM yyyy hh:mm:ss ZZZZZ")
		.parse("Sat, 21 Jul 2012 11:51:36 +0000");
		System.out.println( Date.parse( a.toGMTString()) );
	}


	/*
    Sat, 21 Jul 2012 11:51:36 +0000
    Ben Simpson
    Wakey wakey! Eggs and bakey, let's hit the river and all get nakey! @tale_lover12 @mail_lover21 @thedwalk @jmgreer88 @T_Card85 @ecannon88
    Sat, 21 Jul 2012 11:51:36 +0000
    Maria Purcell
    RT @MarkNobbyNoble: Kinder eggs are illegal in the US due to a choking hazard with the toy but you can buy a gun &amp; nobody asks any questions! Mad place.
    Sat, 21 Jul 2012 11:51:35 +0000
    Charlotte Hayers
    Never put all your eggs into one basket because you end up losing the lot!
    Sat, 21 Jul 2012 11:51:27 +0000
    Steviee!
    RT @SarahSoltan1D: None of the boys notice/follow you and yet your whole twitter is dedicated to them, but they follow eggs? Me too,  let's go cry together
    Sat, 21 Jul 2012 11:51:27 +0000
    Rachelle Holder
    Pancakes and eggs for breakfast ?_?
    Sat, 21 Jul 2012 11:51:22 +0000
    Rachael Bea
    @MTN_ProjectFame that pure water guy sha...he shud b stoned with eggs.. lmao!!!!
    Sat, 21 Jul 2012 11:51:16 +0000
    Jolie DJJolie
    @JedBarry1 I've ran out of eggs. Fancy doing a swap of eggs for CDs? X
    Sat, 21 Jul 2012 11:51:16 +0000
    Satan
    Forever wary of the tops and ends of bananas since I found out that's where tarantulas lay their eggs
    Sat, 21 Jul 2012 11:51:16 +0000
    Morgan McGibson
    @MChevious hate eggs
    Sat, 21 Jul 2012 11:51:15 +0000
    keoratile?...Paeder
    @ManCharrr awww change da scroll thing till it turns red :)...den u will get ur nicely boiled eggs ... Yummy :D
    Sat, 21 Jul 2012 11:51:11 +0000
    Paul Lawrence
    @WestWingReport-Causal connection btwn NRA&amp;Dementia-see Charlton"I can hide my own Easter Eggs"Heston(Too mean&amp;personal?)
    Sat, 21 Jul 2012 11:51:06 +0000
    Nik Sneed
    Wakey wakey eggs and Bacon nigguh!
    Sat, 21 Jul 2012 11:51:04 +0000
    ?????
    Someone in my house finished the eggs...world war is about to commence.
    Sat, 21 Jul 2012 11:50:58 +0000
    ???? ????????
    ??? ??... ? ?????, ???? ????????????? ?? ?????? ? ?????, ??????????????? ?????????????))
	 */

}
