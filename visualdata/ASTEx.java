
/**
* from http://www.programcreek.com/2011/01/a-complete-standalone-example-of-astparser/
*
*/

import java.util.HashSet;

import java.util.Set;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import java.util.Scanner;
import java.io.File;
import org.apache.commons.lang.StringUtils;


public class ASTEx {
	public static void main(String args[]) {

		ASTParser parser = ASTParser.newParser(AST.JLS3);
		if( args.length == 0 ) {
			parser.setSource("public class A { int i = 9;  \n int j; \n ArrayList<Integer> al = new ArrayList<Integer>();j=1000; }".toCharArray());
		}
		else {
			String s = getFileAsString(args[0]);
			System.out.println( s);
			parser.setSource( s.toCharArray());
		}
		//parser.setSource("/*abc*/".toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		//ASTNode node = parser.createAST(null);


		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {

			Set names = new HashSet();

			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name = node.getName();
				this.names.add(name.getIdentifier());
				System.out.println("Declaration of '"+name+"' at line"+cu.getLineNumber(name.getStartPosition()));
				return false; // do not continue to avoid usage info
			}

			public boolean visit(SimpleName node) {
				if (this.names.contains(node.getIdentifier())) {
					System.out.println("Usage of '" + node + "' at line " +	cu.getLineNumber(node.getStartPosition()));
				}
				return true;
			}

		});
	}

	static String getFileAsString(String filename) {
		try {
		return new Scanner(new File(filename)).useDelimiter("\\Z").next();
		}
		catch(Exception e ) {
			e.printStackTrace();
			return null;
		}

	}
}
