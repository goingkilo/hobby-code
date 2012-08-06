

/*

I've always wanted a simple scheme editor
this one is a swing implementation
needs SISC-Scheme jars in the classpath
namely - sisc.jar, sisc-lib.jar, sisc-opt.jar
also the sisc heap that comes in the sisc zip - sisc.shp
*/
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
 
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
 
import sisc.data.SchemeString;
import sisc.interpreter.AppContext;
import sisc.interpreter.Context;
import sisc.interpreter.Interpreter;
import sisc.ser.BufferedRandomAccessInputStream;
import sisc.ser.SeekableDataInputStream;
 
public class Scythe {
 
                Interpreter interpreter;
 
                Highlighter.HighlightPainter highlighter = new MyHighlightPainter(Color.cyan);
 
                Object tag1,tag2 ;
 
                boolean quote = false;
 
                JFrame frame;
                JPanel panel;
                JButton button;
                JFileChooser fc;
                JTextPane text;
 
 
                ActionListener actionListener = new ActionListener( ){
 
                                @Override
                                public void actionPerformed(ActionEvent arg0) {
                                                String selectedText = text.getSelectedText() ;
                                                String allText = text.getText();
                                                String textToEval = "";
                                                String result = "";
 
                                                if ( selectedText == null || selectedText.equals("") || selectedText.length() == 0 ) {
                                                                textToEval = allText;
                                                                if ( textToEval == null || textToEval.equals("") || textToEval.length() == 0 ) {
                                                                                return;
                                                                }
                                                }
                                                else {
                                                                textToEval = selectedText;
                                                }
 
                                                try {
                                                                result = eval( textToEval);
                                                                if( result.equals("#!void")) {
                                                                                result =  ";OK\n";
                                                                }
                                                                text.setText( allText +  "\n" + result);
                                                }
                                                catch (Exception e) {
                                                                text.setText( allText +  "\n;; error : " + e.getMessage());
                                                }
                                }
                };
 
 
                CaretListener caretListener = new CaretListener(){
 
                                @Override
                                public void caretUpdate(CaretEvent e) {
                                               
                                                String t = text.getText();
                                                t = t.replaceAll(System.getProperty("line.separator"), "\n");
 
                                                if( t.equals(""))
                                                                return;
 
                                                int pos = e.getDot() ;
                                                pos = (pos > 0) ? pos -1 : 0;
                                               
                                                System.out.println( e.getDot() + ", " + t.length() );
 
//                                            System.out.println( t + "["+pos+"]{"+t.charAt(pos)+"}");
                                               
                                                if( t.charAt( pos) == ')'  ) {
 
                                                                text.getHighlighter().removeAllHighlights();
 
                                                                //get matching paren
                                                                int i  = Scythe.match( t, pos);
 
                                                                //highlight both
                                                                try {
                                                                                tag1 = text.getHighlighter().addHighlight( i, i+1, highlighter);
                                                                                tag2 = text.getHighlighter().addHighlight( pos, pos +1, highlighter);
                                                                }
                                                                catch (BadLocationException e1) {
                                                                                e1.printStackTrace();
                                                                }
                                                }
                                                else {
                                                                if ( tag1 != null) {
                                                                                text.getHighlighter().removeHighlight(tag1);
                                                                }
                                                                if ( tag2 != null) {
                                                                                text.getHighlighter().removeHighlight(tag2);
                                                                }//what if ))
                                                }
 
                                }
                };
 
                public Scythe()throws Exception{
 
                                //loading scheme
                                AppContext context = new AppContext();
                                interpreter = Context.enter(context);
                                context.loadEnv(
                                                                new SeekableDataInputStream(
                                                                                                new BufferedRandomAccessInputStream(
                                                                                                                                "C:/Users/karthik.raghunathan/Downloads/sisc-1.16.6/sisc.shp", "r")));
 
                                frame = new JFrame();
                                panel = new JPanel();
                                button = new JButton("evaluate");
 
                                button.addActionListener( actionListener);
 
                                text = new JTextPane();
 
                                text.setCaretColor(Color.RED);
                                text.getCaret().setBlinkRate(600);
                               
                                text.addCaretListener( caretListener);
                                text.setText( "" );
 
                                panel.setLayout( new BoxLayout(panel,BoxLayout.Y_AXIS));
                                panel.add(button);
                                panel.add( new JScrollPane(text));
 
                                frame.add( panel);
 
                                menus();
 
                                frame.setSize( new Dimension(350,500));
                                frame.setLocationRelativeTo(null);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame.setVisible(true);
 
                }
 
                String eval(String s) throws Exception{
                                //                            System.out.println( s +"," + interpreter);
                                return interpreter.eval(s).toString();
                }
 
                String eval_into_string(String s) throws Exception{
                                return ((SchemeString)interpreter.eval(s)).asString();
                }
 
                public void menus()
                {
                                JMenuBar menubar  = new JMenuBar();
 
                                JPopupMenu.setDefaultLightWeightPopupEnabled (false);
                                JMenu file = new JMenu("File");
                                file.setMnemonic(KeyEvent.VK_F);
 
                                addMenuItem( file, "New", KeyEvent.VK_N, "New  ",  new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent arg0)
                                                {
                                                                //                                                            System.out.println("new I");
                                                }
                                });
 
                                addMenuItem(file, "Open", KeyEvent.VK_L, "Open", new ActionListener() {
 
                                                @Override
                                                public void actionPerformed(ActionEvent arg0)
                                                {
                                                                fc = new JFileChooser();
                                                                int returnVal = fc.showOpenDialog(null);
                                                                if (returnVal == JFileChooser.APPROVE_OPTION)
                                                                {
                                                                                File f1 = fc.getSelectedFile(); //This grabs the File you typed
                                                                                //                                                                            System.out.println( "-o-" + f1.getAbsolutePath() );
                                                                                try
                                                                                {             
                                                                                                FileInputStream fis = new FileInputStream(f1);
                                                                                                byte[] b= new byte[fis.available()];
                                                                                                fis.read(b);
                                                                                                fis.close();
                                                                                                //                                                                                            System.out.println( "setting : " + new String(b));
                                                                                                text.setText( new String(b));
                                                                                }             
                                                                                catch(Exception e)
                                                                                {
                                                                                                System.out.println("unable to load file:"+f1.getAbsolutePath() + " , Error :"+e.getMessage());
                                                                                }             
                                                                }
                                                }
                                }
                                                                );
 
                                addMenuItem(file, "Save", KeyEvent.VK_S, "Save",
                                                                new ActionListener()
                                {
                                                @Override
                                                public void actionPerformed(ActionEvent arg0)
                                                {
                                                                fc = new JFileChooser();
                                                                int returnVal = fc.showSaveDialog(null);
                                                                if (returnVal == JFileChooser.APPROVE_OPTION)
                                                                {
                                                                                File file = fc.getSelectedFile(); //This grabs the File you typed
                                                                                try
                                                                                {
                                                                                                FileOutputStream fos = new FileOutputStream( file );
                                                                                                fos.write(text.getText().getBytes());
                                                                                                fos.close();
                                                                                }             
                                                                                catch(Exception e)
                                                                                {
                                                                                                System.out.println("unable to save to file:"+file.getAbsolutePath() + " , Error :"+e.getMessage());
                                                                                }             
                                                                }
                                                }
                                }
                                                                );
 
                                addMenuItem(file, "Quit", KeyEvent.VK_X, "Exit",
                                                                new ActionListener()
                                {
                                                @Override
                                                public void actionPerformed(ActionEvent arg0)
                                                {
                                                                frame.dispose();
                                                                System.exit(0);
                                                }
                                }
                                                                );
 
 
                                JMenu help = new JMenu("Help");
                                help.setMnemonic(KeyEvent.VK_H);
 
                                addMenuItem(help, "Credits", KeyEvent.VK_C, "Credits",  new ActionListener(){
 
                                                @Override
                                                public void actionPerformed(ActionEvent arg0) {
                                                                JOptionPane.showMessageDialog(frame,"dev : raghunathan.karthik@gmail.com");
                                                }
                                });
 
                                menubar.add(file);
                                menubar.add(help);
 
                                frame.setJMenuBar(menubar);
                }
 
                private void addMenuItem(JMenu menu, String name, int mnemonic, String toolTipText, ActionListener a)
                {
                                JMenuItem item = new JMenuItem(name, null);
                                item.setMnemonic(mnemonic);
                                item.setToolTipText(toolTipText);
                                item.addActionListener(a);
                                menu.add(item);
 
                }
 
                static int match(String text, int pos) {
                                int k = 0;
                                for( int i = pos ; i >= 0 ; i-- ) {
 
                                                char c = text.charAt(i);
                                                if ( c== ')' ) {
                                                                k ++;
                                                }
                                                else if ( c == '(' ) {
                                                                k --;
                                                                if( k == 0 ) return i;
                                                }
//                                            System.out.println("["+c+"]");
                                }
                                return -1;
                }
 
                class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
                                public MyHighlightPainter(Color color) {
                                                super(color);
                                }
                }
 
                public static void main(String[] args) throws Exception {
                                new Scythe();
                                System.out.println( "abc\n".length() );
                                System.out.println( "abc".length() );
                }
 
}
