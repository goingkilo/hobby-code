package edi;

import org.armedbear.lisp.Interpreter;
import org.armedbear.lisp.LispObject;


public class ABCLTrial {

	public static void main(String[] args) {

		Interpreter i = Interpreter.createInstance();
		LispObject l = i.eval("(+ 1 2 )");
		Object o = l.javaInstance();
		System.out.println(o.getClass().getName()+","+o);
	}

}
