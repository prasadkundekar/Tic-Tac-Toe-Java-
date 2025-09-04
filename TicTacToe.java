import java.util.*;
import java.io.*;

public class TicTacToe {

    static Scanner in = new Scanner(System.in);
    static Random rand = new Random();
    static int player1Score = 0, player2Score = 0, computerScore = 0;
    static final String SCORE_FILE = "scores.txt";

  
    public static void loadScores() {
        try (BufferedReader br = new BufferedReader(new FileReader(SCORE_FILE))) {
            br.readLine(); 

            String line1 = br.readLine();
            String line2 = br.readLine();
            String line3 = br.readLine();
            br.readLine(); 

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


    public static void saveScores() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SCORE_FILE))) {
            pw.println("==== Tic Tac Toe Scores ====");
            pw.println("Player 1 Wins : " + player1Score);
            pw.println("Player 2 Wins : " + player2Score);
            pw.println("Computer Wins : " + computerScore);
            pw.println("============================");
        } catch (IOException e) {
            System.out.println("⚠️ Could not save scores.");
        }
    }


    public static int[] parseMove(String move) {
        move = move.toUpperCase().trim();
        if (move.length() != 2) return null;
        char rowChar = move.charAt(0);
        char colChar = move.charAt(1);

        int row = rowChar - 'A';
        int col = colChar - '1';

        if (row < 0 || row > 2 || col < 0 || col > 2) return null;
        return new int[]{row, col};
    }


    public static int[] getValidMove(int[][] A) {
        while (true) {
            System.out.print("Enter your move (A1, B2, C3): ");
            String move = in.nextLine();
            int[] rc = parseMove(move);
            if (rc == null) {
                System.out.println("❌ Invalid format! Use A1, B2, etc.");
                continue;
            }
            if (!isFree(A, rc[0], rc[1])) {
                System.out.println("⚠️ Cell already taken! Try again.");
                continue;
            }
            return rc;
        }
    }

    public static boolean checkRows(int[][] A) {
        for (int i = 0; i < A.length; i++) {
            if ((A[i][0] == A[i][1]) && (A[i][1] == A[i][2]) && A[i][0] != 0)
                return true;
        }
        return false;
    }

    public static boolean checkCols(int[][] A) {
        for (int i = 0; i < A[0].length; i++) {
            if ((A[0][i] == A[1][i]) && (A[1][i] == A[2][i]) && A[0][i] != 0)
                return true;
        }
        return false;
    }

    public static boolean checkDiags(int[][] A) {
        if ((A[0][0] == A[1][1]) && (A[1][1] == A[2][2]) && A[0][0] != 0)
            return true;
        else if ((A[0][2] == A[1][1]) && (A[1][1] == A[2][0]) && A[1][1] != 0)
            return true;
        else
            return false;
    }

    public static boolean checkHit(int[][] A) {
        return (checkRows(A) || checkCols(A) || checkDiags(A));
    }

    public static boolean isFree(int[][] A, int row, int col) {
        return A[row][col] == 0;
    }

    public static boolean getPlayerMove(String turnPrompt, int[][] A, int playerNumber) {
        System.out.println(turnPrompt);
        int[] rc = getValidMove(A);
        A[rc[0]][rc[1]] = playerNumber;
        return checkHit(A);
    }

    public static boolean getComputerMove(int[][] A) {
        int row, col;
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (!isFree(A, row, col));

        System.out.printf("🤖 Computer chooses %c%d\n", (char)('A' + row), col + 1);
        A[row][col] = 2; 
        return checkHit(A);
    }

    public static void printBoard(int[][] A) {
        System.out.println("    1   2   3");
        System.out.println("  -------------");
        for (int i = 0; i < 3; i++) {
            System.out.print((char)('A' + i) + " | ");
            for (int j = 0; j < 3; j++) {
                char symbol;
                if (A[i][j] == 1) symbol = 'X';
                else if (A[i][j] == 2) symbol = 'O';
                else symbol = ' ';
                System.out.print(symbol + " | ");
            }
            System.out.println();
            System.out.println("  -------------");
        }
    }

    public static void playGame(boolean vsComputer) {
        int[][] grid = new int[3][3];
        int foundWinner = 0;

        printBoard(grid);

        for (int i = 0; i < 9; i++) {
            if (i % 2 == 0) { 
                if (getPlayerMove("🎮 Player 1 (X) turn", grid, 1)) {
                    foundWinner = 1;
                    System.out.println("🏆 Player 1 WINS!");
                    player1Score++;
                    break;
                }
            } else {
                if (vsComputer) {
                    if (getComputerMove(grid)) {
                        foundWinner = 1;
                        System.out.println("🏆 Computer WINS!");
                        computerScore++;
                        break;
                    }
                } else {
                    if (getPlayerMove("🎮 Player 2 (O) turn", grid, 2)) {
                        foundWinner = 1;
                        System.out.println("🏆 Player 2 WINS!");
                        player2Score++;
                        break;
                    }
                }
            }
            printBoard(grid);
            System.out.println();
        }

        if (foundWinner == 0) {
            System.out.println("🤝 It's a draw!");
        }

        printBoard(grid);

        if (vsComputer)
            System.out.println("📊 Score: Player1 = " + player1Score + " | Computer = " + computerScore);
        else
            System.out.println("📊 Score: Player1 = " + player1Score + " | Player2 = " + player2Score);

        saveScores(); 
    }

    
    public static void main(String[] args) {
        loadScores(); 

        System.out.println("==== Welcome to Tic Tac Toe ====");
        System.out.print("Choose mode (1 = Player vs Player, 2 = Player vs Computer): ");
        int mode = Integer.parseInt(in.nextLine().trim());
        boolean vsComputer = (mode == 2);

        String again;
        do {
            playGame(vsComputer);
            System.out.print("🔄 Do you want to play again? (y/n): ");
            again = in.nextLine().trim().toLowerCase();
        } while (again.equals("y"));

        System.out.println("👋 Thanks for playing! Final Score:");
        if (vsComputer)
            System.out.println("Player1 = " + player1Score + " | Computer = " + computerScore);
        else
            System.out.println("Player1 = " + player1Score + " | Player2 = " + player2Score);

        saveScores();
    }
}
