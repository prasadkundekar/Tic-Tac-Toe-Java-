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
