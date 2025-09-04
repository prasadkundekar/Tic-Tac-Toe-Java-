import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

public class TicTacToeGUI extends JFrame implements ActionListener {
    JButton[][] buttons = new JButton[3][3];
    boolean player1Turn = true; 
    boolean vsComputer;
    int player1Score = 0, player2Score = 0, computerScore = 0;
    JLabel statusLabel, scoreLabel;
    Random rand = new Random();
    static final String SCORE_FILE = "scores.txt";

    public TicTacToeGUI(boolean vsComputer) {
        this.vsComputer = vsComputer;
        loadScores();

        setTitle("🎮 Tic Tac Toe");
        setSize(420, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel board = new JPanel(new GridLayout(3, 3));
        Font font = new Font("Arial", Font.BOLD, 50);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(font);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].addActionListener(this);
                board.add(buttons[i][j]);
            }
        }

        // Status + Score
        statusLabel = new JLabel("Player 1 (X) Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        scoreLabel = new JLabel(getScoreText(), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        add(statusLabel, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);
        add(scoreLabel, BorderLayout.SOUTH);

        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (!btn.getText().equals("")) return; 

        btn.setText(player1Turn ? "X" : "O");
        btn.setForeground(player1Turn ? Color.BLUE : Color.RED);
        btn.setEnabled(false);

        if (checkWin()) {
            if (player1Turn) {
                JOptionPane.showMessageDialog(this, "🏆 Player 1 Wins!");
                player1Score++;
            } else if (vsComputer) {
                JOptionPane.showMessageDialog(this, "🏆 Computer Wins!");
                computerScore++;
            } else {
                JOptionPane.showMessageDialog(this, "🏆 Player 2 Wins!");
                player2Score++;
            }
            updateScore();
            resetBoard();
            return;
        }

        if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "🤝 It's a Draw!");
            resetBoard();
            return;
        }

        player1Turn = !player1Turn;
        statusLabel.setText(player1Turn ? "Player 1 (X) Turn" :
                (vsComputer ? "Computer (O) Turn" : "Player 2 (O) Turn"));

        if (vsComputer && !player1Turn) {
            SwingUtilities.invokeLater(this::computerMove);
        }
    }

    private void computerMove() {
        int row, col;
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (!buttons[row][col].getText().equals(""));

        buttons[row][col].setText("O");
        buttons[row][col].setForeground(Color.RED);
        buttons[row][col].setEnabled(false);

        if (checkWin()) {
            JOptionPane.showMessageDialog(this, "🏆 Computer Wins!");
            computerScore++;
            updateScore();
            resetBoard();
            return;
        }

        if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "🤝 It's a Draw!");
            resetBoard();
            return;
        }

        player1Turn = true;
        statusLabel.setText("Player 1 (X) Turn");
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().equals("") &&
                buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][1].getText().equals(buttons[i][2].getText())) return true;
        }
        for (int j = 0; j < 3; j++) {
            if (!buttons[0][j].getText().equals("") &&
                buttons[0][j].getText().equals(buttons[1][j].getText()) &&
                buttons[1][j].getText().equals(buttons[2][j].getText())) return true;
        }
        if (!buttons[0][0].getText().equals("") &&
            buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][2].getText())) return true;
        if (!buttons[0][2].getText().equals("") &&
            buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][0].getText())) return true;

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().equals("")) return false;
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        player1Turn = true;
        statusLabel.setText("Player 1 (X) Turn");
    }

    private void updateScore() {
        scoreLabel.setText(getScoreText());
        saveScores();
    }

    private String getScoreText() {
        return "📊 Score - P1: " + player1Score + " | " +
                (vsComputer ? "Computer: " + computerScore : "P2: " + player2Score);
    }

    private void saveScores() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SCORE_FILE))) {
            pw.println("==== Tic Tac Toe Scores ====");
            pw.println("Player 1 Wins : " + player1Score);
            pw.println("Player 2 Wins : " + player2Score);
            pw.println("Computer Wins : " + computerScore);
            pw.println("============================");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Could not save scores.");
        }
    }


    private void loadScores() {
        try (BufferedReader br = new BufferedReader(new FileReader(SCORE_FILE))) {
            br.readLine(); 
            String line1 = br.readLine();
            String line2 = br.readLine();
            String line3 = br.readLine();
            if (line1 != null && line1.contains(":"))
                player1Score = Integer.parseInt(line1.split(":")[1].trim());
            if (line2 != null && line2.contains(":"))
                player2Score = Integer.parseInt(line2.split(":")[1].trim());
            if (line3 != null && line3.contains(":"))
                computerScore = Integer.parseInt(line3.split(":")[1].trim());
        } catch (IOException | NumberFormatException e) {
            player1Score = 0;
            player2Score = 0;
            computerScore = 0;
        }
    }

    public static void main(String[] args) {
        String[] options = {"Player vs Player", "Player vs Computer"};
        int mode = JOptionPane.showOptionDialog(null, "Choose Mode", "Tic Tac Toe",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        new TicTacToeGUI(mode == 1);
    }
}
