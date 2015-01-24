(import '(javax.swing JFrame JPanel JButton JOptionPane JTextPane JScrollPane BoxLayout))
(import '(java.awt BorderLayout Color Dimension))
(import '(java.awt.event ActionListener  KeyEvent KeyListener))
(import '(java.io PushbackReader BufferedReader))
(import javax.swing.event.CaretListener)
(import '(javax.swing.text BadLocationException DefaultHighlighter Highlighter))

(def frame  (JFrame. "Clojure"))
(def panel (JPanel. ))
(def text (JTextPane.))
(def scroll (JScrollPane. text))
(def button (JButton. "evaluate"))


(.setLayout panel (BoxLayout. panel BoxLayout/Y_AXIS))

(def caret-listener (proxy [CaretListener] []
 (caretUpdate [caretEvent]
  (println "you have a caret update ! good for eyes ! " (.getDot caretEvent))) )   )

(def act (proxy [ActionListener] []
           (actionPerformed [event]
                            (println "button->" (eval (read-string (.getText text)))))))

(def key-listen
    (proxy [KeyListener] []
        (keyPressed [event])
         (keyReleased [event] )
         (keyTyped [event] )))


(. text setCaretColor Color/RED)
(. text addCaretListener caret-listener)
(.. text getCaret  (setBlinkRate 600))
(. text setText  "start hacking")

(.add panel button)
(.add panel (new JScrollPane text))

(.add frame panel)

(doto frame
   (.setSize  350 500)
 (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
 (.setLocationRelativeTo nil )
   (.setVisible true))

 

(.addActionListener button act)
(.addKeyListener text key-listen)



(.setLayout panel (BoxLayout. panel BoxLayout/Y_AXIS))

(def caret-listener (proxy [CaretListener] []
 (caretUpdate [caretEvent]
  (println "you have a caret update ! good for eyes ! " (.getDot caretEvent))) )   )

(def act (proxy [ActionListener] []
           (actionPerformed [event]
                            (let [a (or (.getSelectedText text) ";;")]
                                        (println  " you clicked button")))))

(def key-listen
    (proxy [KeyListener] []
        (keyPressed [event])
         (keyReleased [event] )
         (keyTyped [event] )))


(. text setCaretColor Color/RED)
(. text addCaretListener caret-listener)
(.. text getCaret  (setBlinkRate 600))
(. text setText  "start hacking")

(.add panel (new JScrollPane text))

(.add frame panel)


(doto frame
   (.setSize  350 500)
 (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
 (.setLocationRelativeTo nil )
   (.setVisible true))


(.addActionListener button act)
(.addKeyListener text key-listen)
