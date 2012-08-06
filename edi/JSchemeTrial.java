package edi;

import jscheme.JScheme;

public class JSchemeTrial {

	public static void main(String[] args){
		
		JScheme scheme = new JScheme();
		Object s = scheme.eval("(define (hello a ) (+ a 2))");
		s = scheme.eval("(hello 2)");
		System.out.println(s);
	}
}
