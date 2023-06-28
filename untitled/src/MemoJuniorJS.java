/*
@Author Salome D'Angelo
@Author Javier Ladino Prada
*/

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MemoJuniorJS extends JFrame implements ActionListener {
    private JButton[] buttons;
    private String[] cardSymbols;
    private boolean[] flipped;
    private int numFlipped;
    private int firstIndex;
    private int secondIndex;

    public static void main(String[] args) {
        MemoJuniorJS game = new MemoJuniorJS();
    }

    public MemoJuniorJS() {
        cardSymbols = new String[]{"A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F", "G", "G", "H", "H"};
        buttons = new JButton[16];
        flipped = new boolean[16];
        numFlipped = 0;
        firstIndex = -1;
        secondIndex = -1;

        JPanel gridPanel = new JPanel(new GridLayout(4, 4));
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].addActionListener(this);
            gridPanel.add(buttons[i]);
        }

        add(gridPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MemoJuniorJS");
        setSize(400, 400);
        setResizable(false);
        setVisible(true);
    }

    private void startNewGame() {
        shuffleCards();
        resetCards();
    }

    private void shuffleCards() {
        for (int i = cardSymbols.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            String temp = cardSymbols[i];
            cardSymbols[i] = cardSymbols[j];
            cardSymbols[j] = temp;
        }
    }

    private void resetCards() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            flipped[i] = false;
        }
    }

    private void checkCards() {
        if (cardSymbols[firstIndex].equals(cardSymbols[secondIndex])) {
            buttons[firstIndex].setEnabled(false);
            buttons[secondIndex].setEnabled(false);
            numFlipped += 2;

            if (numFlipped == buttons.length) {
                JOptionPane.showMessageDialog(this, "¡Felicidades! Has ganado el juego.", "Fin del juego",
                        JOptionPane.INFORMATION_MESSAGE);
                startNewGame();
            }
        } else {
            buttons[firstIndex].setText("");
            buttons[secondIndex].setText("");
        }

        firstIndex = -1;
        secondIndex = -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i] && !flipped[i]) {
                buttons[i].setText(cardSymbols[i]);
                flipped[i] = true;

                if (firstIndex == -1) {
                    firstIndex = i;
                } else {
                    secondIndex = i;
                    buttons[firstIndex].setEnabled(false);
                    buttons[secondIndex].setEnabled(false);
                    break;
                }
            }
        }

        if (secondIndex != -1) {
            checkCards();
        }
    }
}
