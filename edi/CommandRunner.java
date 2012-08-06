
package edi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;


public class CommandRunner {

	BufferedOutputStream bos ;
	BufferedInputStream bis ;
	BufferedInputStream ers ;
	
	Runnable outStream = new Runnable(){
		public void run() {
			int i;
			try {
				while( (i = bis.read()) != -1 ){
					System.out.print((char)i);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	
	Runnable errorStream = new Runnable(){
		public void run() {
			int i;
			try {
				while( (i = ers.read()) != -1 ){
					System.out.print((char)i);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	public void go(String command) throws IOException, InterruptedException {
		Runtime r = Runtime.getRuntime();
		Process p = r.exec(command);

		bos = new BufferedOutputStream(p.getOutputStream());
		bis = new BufferedInputStream( p.getInputStream());
		ers = new BufferedInputStream(p.getErrorStream());

		Thread t1 = new Thread(outStream);
		t1.start();
		Thread t2 = new Thread(errorStream);
		t2.start();
		
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		new CommandRunner().go("kill -9 1164");
	}
	
}
