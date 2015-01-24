/*
http://www.programcreek.com/2011/11/use-jdt-astparser-to-parse-java-file/
jar file required for JDT ASTParser
also 
http://www.programcreek.com/2011/08/code-to-parse-a-java-project/
also
http://eclipse.org/articles/article.php?file=Article-JavaCodeManipulation_AST/index.html
*/

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Main {

	// use ASTParse to parse string
	public static void parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

		compilationUnit.accept(new ASTVisitor() {

			Set<String> names = new HashSet<String>();
			
			public boolean visit(CompilationUnit unit) {
				System.out.println( unit.getClass().getName());
				System.out.println( unit. 
				return false;
			}

			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name = node.getName();
				this.names.add( name.getIdentifier());
			
				System.out.println("Declaration of '" + name + "' at line" + compilationUnit.getLineNumber(name.getStartPosition()));
				return false; // do not continue
			}

			public boolean visit(SimpleName node) {
				if (this.names.contains(node.getIdentifier())) {
					System.out.println("Usage of '" + node + "' at line " + compilationUnit.getLineNumber(node.getStartPosition()));
				}
				return true;
			}
			
		});

	}

	public static String reads(String filePath) throws IOException {
		FileInputStream fis = new FileInputStream( new File(filePath));
		byte[] b = new byte[fis.available()];
		fis.read(b);
		fis.close();

		return new String(b);
	}

	public static void process(String dir) throws IOException {
		File[] files  = new File(dir).getAbsoluteFile().listFiles(
				new FileFilter(){
					@Override
					public boolean accept(File pathname) {
						return pathname.getAbsolutePath().endsWith(".java");
					}
				});

		System.out.println( new File(dir).getAbsolutePath());
		for (File file :files )  {
			if (file.isFile()) {
				String s  = reads( file.getAbsolutePath());
				parse(s);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		process("C:/workspaces/other/AST/src/misc");
	}
}
