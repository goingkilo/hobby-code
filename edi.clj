
(ns pipo)

(import '(javax.swing JFrame JPanel JButton JOptionPane JTextPane JScrollPane BoxLayout))
(import '(java.awt BorderLayout))
(import '(java.awt.event ActionListener  KeyEvent KeyListener))
(import '(java.io PushbackReader BufferedReader))

(def frame (doto (JFrame. "Hello Frame")))
(def panel (JPanel. ))
(def text (JTextPane.))
(def scroll (JScrollPane. text))
(def button (JButton. "Click Me!"))


(.setLayout panel (BoxLayout. panel BoxLayout/Y_AXIS))

(.add panel scroll BorderLayout/CENTER)
(.add panel button BorderLayout/SOUTH)

(.add (.getContentPane frame) panel) 

;;(.. frame (getContentPane) (add panel))


(doto frame
  (.setSize 500 600)
	(.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
	(.setLocationRelativeTo nil )
  (.setVisible true))

(def act (proxy [ActionListener] []
           (actionPerformed [event] 
                            (let [a (or (.getSelectedText text) ";;")]
                                        (println (eval (read-string a)))))))

(def key-listen 
    (proxy [KeyListener] []
	       (keyPressed [event])
         (keyReleased [event] )
         (keyTyped [event] )))


(.addActionListener button act)
(.addKeyListener text key-listen)
