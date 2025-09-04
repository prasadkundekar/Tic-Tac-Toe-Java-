import java.util.Scanner;

public class TicTacToe {

    static Scanner in = new Scanner(System.in);
    static int player1Score = 0, player2Score = 0;

   
    public static int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine();
            int num = 0;
            try {
                num = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("❌ Invalid integer!");
                continue;
            }
            if (num < 0 || num > 2) {
                System.out.println("❌ Integer must be between 0 and 2");
                continue;
            }
            return num;
        }
    }

    // ✅ Check rows
    public static boolean checkRows(int[][] A) {
        for (int i = 0; i < A.length; i++) {
            if ((A[i][0] == A[i][1]) && (A[i][1] == A[i][2]) && A[i][0] != 0)
                return true;
        }
        return false;
    }

    // ✅ Fixed checkCols()
    public static boolean checkCols(int[][] A) {
        for (int i = 0; i < A[0].length; i++) {
            if ((A[0][i] == A[1][i]) && (A[1][i] == A[2][i]) && A[0][i] != 0)
                return true;
        }
        return false;
    }

    // ✅ Check diagonals
    public static boolean checkDiags(int[][] A) {
        if ((A[0][0] == A[1][1]) && (A[1][1] == A[2][2]) && A[0][0] != 0)
            return true;
        else if ((A[0][2] == A[1][1]) && (A[1][1] == A[2][0]) && A[1][1] != 0)
            return true;
        else
            return false;
    }

    // ✅ Overall winner check
    public static boolean checkHit(int[][] A) {
        return (checkRows(A) || checkCols(A) || checkDiags(A));
    }
    
    // ✅ Check if cell is empty
    public static boolean isFree(int[][] A, int row, int col) {
        return A[row][col] == 0;
    }

    // ✅ Player turn
    public static boolean getWinner(String turnPrompt, int[][] A, int playerNumber) {
        System.out.println(turnPrompt);
        int row = 0, col = 0;
        while (true) {
            row = getValidInt("Enter row (0-2): ");
            col = getValidInt("Enter col (0-2): ");
            if (isFree(A, row, col)) {
                break;
            }
            System.out.printf("⚠️ [%d,%d] is already filled!\n", row, col);
        }
        A[row][col] = playerNumber;
        return checkHit(A);
    }

    // ✅ Print board with X and O
    public static void printBoard(int[][] A) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                char symbol;
                if (A[i][j] == 1) symbol = 'X';
                else if (A[i][j] == 2) symbol = 'O';
                else symbol = ' ';
                System.out.print(symbol + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    // ✅ Run one game
    public static void playGame() {
        int[][] grid = new int[3][3];
        int foundWinner = 0;

        printBoard(grid);

        for (int i = 0; i < 9; i++) {
            if (i % 2 == 0) { // Player 1
                if (getWinner("🎮 Player 1 (X) turn", grid, 1)) {
                    foundWinner = 1;
                    System.out.println("🏆 Player 1 WINS!");
                    player1Score++;
                    break;
                }
            } else { // Player 2
                if (getWinner("🎮 Player 2 (O) turn", grid, 2)) {
                    foundWinner = 1;
                    System.out.println("🏆 Player 2 WINS!");
                    player2Score++;
                    break;
                }
            }
            printBoard(grid);
            System.out.println();
        }

        if (foundWinner == 0) {
            System.out.println("🤝 It's a draw!");
        }

        printBoard(grid);
        System.out.println("📊 Score: Player1 = " + player1Score + " | Player2 = " + player2Score);
    }

    
    public static void main(String[] args) {
        System.out.println("==== Welcome to Tic Tac Toe ====");
        String again;
        do {
            playGame();
            System.out.print("🔄 Do you want to play again? (y/n): ");
            again = in.nextLine().trim().toLowerCase();
        } while (again.equals("y"));

        System.out.println("👋 Thanks for playing! Final Score:");
        System.out.println("Player1 = " + player1Score + " | Player2 = " + player2Score);
    }
}
