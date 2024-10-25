import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;


public class GUI extends JPanel implements KeyListener, ActionListener{
    
    JPanel basePanel;
    JPanel inputPanel;
    JPanel wordPanel;
    

    Component paddingLeft = Box.createHorizontalGlue();
    Component paddingRight = Box.createHorizontalGlue();

    JLabel inputText;
    JLabel guessedLettersLbl;
    JLabel guessBank;
    JLabel inputLabel;
    
    JButton enterButton;
    
    char guess;
    ArrayList<Character> guessedLetters = new ArrayList<Character>();
    ArrayList<String> wordBank = new ArrayList<String>();
    char[] alphabet = {'a', 'b','c','d','e','f','g','h','i','j','k','l','m','n',
                        'o','p','q','r','s','t','u','v','w','x','y','z'};
    ArrayList<Character> alphabetList;
    String word;
    int mistakes;
    
    public GUI() {
        addGUI();
    }

    public void addGUI() {
        //convert from array to arraylist cuz im lazy
        alphabetList = new ArrayList<Character>();
        for (int i = 0; i < alphabet.length; i++) {
            alphabetList.add(alphabet[i]);
        }

        wordBank.add("hello");
        wordBank.add("banana");
        wordBank.add("apple");
        wordBank.add("monkey");
        wordBank.add("orange");
        wordBank.add("hi");
        word = wordBank.get((int) (Math.random() * wordBank.size()));
        mistakes = 0;
        
        basePanel = new JPanel();
        inputPanel = new JPanel();
        wordPanel = new JPanel();

        //input panel
        inputPanel.setBackground(Color.LIGHT_GRAY);
        inputPanel.setPreferredSize(new Dimension(400, 350));
        inputPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 350));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        wordPanel.setBackground(Color.LIGHT_GRAY);
        wordPanel.setPreferredSize(new Dimension(350, 50));
        wordPanel.setLayout(new BoxLayout(wordPanel, BoxLayout.X_AXIS));
        //add left side of padding
        wordPanel.add(paddingLeft);
/*
        wordPanel2.setBackground(Color.PINK);
        wordPanel2.setPreferredSize(new Dimension(100, 50));
        wordPanel2.setLayout(new BoxLayout(wordPanel2, BoxLayout.X_AXIS));
*/
        //input panel text
        inputText = new JLabel("Guess a letter"); 
        guessedLettersLbl = new JLabel("Guessed letters:");
        inputLabel = new JLabel("A");
        enterButton = new JButton("ENTER");
        guessBank = new JLabel("[]");
        
        inputText.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputText.setFont(new Font("SANS_SERIF", Font.BOLD, 30));
        guessedLettersLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessedLettersLbl.setFont(new Font("SANS_SERIF", Font.PLAIN, 20));
        guessBank.setFont(new Font("SANS_SERIF", Font.PLAIN, 20));
        guessBank.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessBank.setMaximumSize(new Dimension(300, 70));
        
        inputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 100));

        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.setFocusable(false);
        enterButton.addActionListener(this);

        inputPanel.add(inputText);
        inputPanel.add(inputLabel);
        inputPanel.add(enterButton);
        inputPanel.add(guessedLettersLbl);
        inputPanel.add(guessBank);
        
        inputPanel.addKeyListener(this);
        inputPanel.setFocusable(true);
        inputPanel.requestFocusInWindow();

        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
        basePanel.setPreferredSize(new Dimension(400, 300));
        basePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 300));
        basePanel.setBackground(Color.WHITE);
        
        
        
        displayWord(word);
        //right side of padding
        wordPanel.add(paddingRight);
        
        add(basePanel);
        add(wordPanel);
        add(inputPanel);

        

    }

    public JLabel createPic(String filePath, float alignment) {
        BufferedImage pic;
        JLabel picLabel;
        try {
            pic = ImageIO.read(new File(filePath));
            picLabel = new JLabel(new ImageIcon(pic.getScaledInstance(100, 50, Image.SCALE_SMOOTH)));
            picLabel.setAlignmentX(alignment);
            return picLabel;
        }
        catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    public void drawHangman(int mistakes) {
        Component[] components = basePanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                basePanel.remove(comp); 
            }
        }
        for (int i = 1; i <= mistakes; i++) {
            basePanel.add(createPic("src\\images\\hangman" + i + ".png", Component.CENTER_ALIGNMENT));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            enterGuess();
        }
        else {
            char keyChar = e.getKeyChar();
            inputLabel.setText(String.valueOf(keyChar));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enterButton) {
            enterGuess();
        }
    }

    public void displayWord(String word) {
        //remove exisiting labels/padding
        wordPanel.remove(paddingLeft);
        Component[] components = wordPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                wordPanel.remove(comp); 
            }
        }
        wordPanel.remove(paddingRight);
        wordPanel.add(paddingLeft);
        for (int i = 0; i < word.length(); i++) {
            if (guessedLetters.contains(word.charAt(i))) {
                wordPanel.add(createLabel(Character.toLowerCase(word.charAt(i))));
            }
            else {
                wordPanel.add(createLabel('_'));
                wordPanel.add(createLabel(' '));
            }
        }
        wordPanel.add(paddingRight);
        wordPanel.repaint();
        wordPanel.revalidate();
    }

    public JLabel createLabel(char letter) {
        return new JLabel(String.valueOf(letter));
    }

    public void enterGuess() {
        guess = inputLabel.getText().charAt(0);
        inputLabel.setText(" ");
        
        if (!guessedLetters.contains(guess) && alphabetList.contains(guess)) {
            guessedLetters.add(guess);
            if (checkWin()) {
                displayWord(word);
                endGame("win");
            }
            if (word.indexOf(guess) == -1) {
                mistakes++;
                if (mistakes >= 6) {
                    drawHangman(6);
                    endGame("lose");
                }
                else {
                    drawHangman(mistakes);
                    }
            }
            displayWord(word);
            guessBank.setText("<html><div style='text-align: center;'>" + guessedLetters + "</div></html>");
        }
    }

    public void endGame(String outcome) {
        Object[] options = {"Yes", "No"};
            int result = JOptionPane.showOptionDialog(
            basePanel,
   "You " + outcome + "! The correct word was " + word + "\nPlay again?",
     "Game Over!",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
       null,
            options, // The custom button text
            options[0] // Default button (Yes)
    );

        if (result == JOptionPane.YES_OPTION) {
            resetGame();
        } else if (result == JOptionPane.NO_OPTION) {
            System.out.println("You clicked No.");
            basePanel.setFocusable(false);
            inputPanel.setFocusable(false);
        } else {
            System.out.println("You clicked No.");
            basePanel.setFocusable(false);
            inputPanel.setFocusable(false);
        }

    }

    public void resetGame() {
        basePanel.removeAll();
        mistakes = 0;
        guessedLetters.removeAll(guessedLetters);
        word = wordBank.get((int) (Math.random() * wordBank.size() + 1));
        displayWord(word);
        repaint();
        revalidate();
    }

    public boolean checkWin() {
        for (int i = 0; i < word.length(); i++) {
            if (guessedLetters.indexOf(word.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }
}
