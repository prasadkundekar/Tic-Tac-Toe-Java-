import java.util.Scanner;

public class TicTacToe {

    static Scanner in = new Scanner(System.in);
    static int player1Score = 0, player2Score = 0;

    // ✅ Get valid integer input
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

    
    // ✅ Main method with replay option
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
