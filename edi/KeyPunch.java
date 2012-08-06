package edi;

import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class KeyPunch {
	static synchronized void playSound() {
		new Thread(new Runnable() { 
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream( 
							new FileInputStream( 
									new File(("/home/kilo/typekey.wav"))));
					clip.open(inputStream);
					clip.start();
					System.out.println("played");
				} 
				catch (Exception e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static void main(String[] args){
		playSound();
	}
}
