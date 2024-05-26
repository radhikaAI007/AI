// package TicTacToe;
import java.util.Scanner;

public class TicTacToeNonAI {

    static int[] board = { 0, 2, 2, 2, 2, 2, 2, 2, 2, 2 }; // 2 represents empty, 3 represents X, 5 represents O

    static boolean checkWin(int player) {
        for (int i = 1; i <= 9; i += 3) {
            if (board[i] == player && board[i + 1] == player && board[i + 2] == player) {
                return true; // Win in a row
            }
        }

        for (int i = 1; i <= 3; i++) {
            if (board[i] == player && board[i + 3] == player && board[i + 6] == player) {
                return true; // Win in a column
            }
        }

        if (board[1] == player && board[5] == player && board[9] == player) {
            return true; // Win in the main diagonal
        }

        if (board[3] == player && board[5] == player && board[7] == player) {
            return true; // Win in the other diagonal
        }

        return false; // No win yet
    }

    // Function to check the possibility of winning for a player or machine
    static int posswin(int p) {
        int pos;
        for (int i = 1; i <= 9; i += 3) { // Check rows
            pos = i;
            if (board[pos] * board[pos + 1] * board[pos + 2] == p * p * 2) {
                if (board[pos] == 2)
                    return pos;
                if (board[pos + 1] == 2)
                    return pos + 1;
                if (board[pos + 2] == 2)
                    return pos + 2;
            }
        }

        for (int i = 1; i <= 3; i++) { // Check columns
            pos = i;
            if (board[pos] * board[pos + 3] * board[pos + 6] == p * p * 2) {
                if (board[pos] == 2)
                    return pos;
                if (board[pos + 3] == 2)
                    return pos + 3;
                if (board[pos + 6] == 2)
                    return pos + 6;
            }
        }

        pos = 1; //Main diagonal empty position we have to find
        if (board[pos] * board[pos + 4] * board[pos + 8] == p * p * 2) {
            if (board[pos] == 2)
                return pos;
            if (board[pos + 4] == 2)
                return pos + 4;
            if (board[pos + 8] == 2)
                return pos + 8;
        }

        pos = 3;  //Find empty position in other diagonal
        if (board[pos] * board[pos + 2] * board[pos + 4] == p * p * 2) {
            if (board[pos] == 2)
                return pos;
            if (board[pos + 2] == 2)
                return pos + 2;
            if (board[pos + 4] == 2)
                return pos + 4;
        }

        return 0;
    }

    static void makemove(int player) {
        if (board[5] == 2) {
            board[5] = player;
            return;
        }

        int winningMove = posswin(player);
        if (winningMove != 0 && board[winningMove] == 2) {
            board[winningMove] = player;
            return;
        }

        int blockingMove = posswin(player == 3 ? 5 : 3);
        if (blockingMove != 0 && board[blockingMove] == 2) {
            board[blockingMove] = player;
            return;
        }

        int[] corners = { 1, 3, 7, 9 };
        for (int i = 0; i < 4; ++i) {
            if (board[corners[i]] == 2) {
                board[corners[i]] = player;
                return;
            }
        }

        for (int i = 1; i <= 9; ++i) {
            if (board[i] == 2) {
                board[i] = player;
                return;
            }
        }
    }

    static void displayBoard() {
        System.out.println();
        for (int i = 1; i <= 9; i++) {
            if (board[i] == 2) {
                System.out.print("_");
            } else if (board[i] == 3) {
                System.out.print("X");
            } else {
                System.out.print("O");
            }

            if (i % 3 == 0) {
                System.out.println();
            } else {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }

    static int gameover() {
        if (checkWin(3)) {
            displayBoard();
            System.out.println("Game Over. You win!");
            return 1;
        }

        if (checkWin(5)) {
            displayBoard();
            System.out.println("Game Over. You lose!");
            return 1;
        }

        for (int i = 1; i <= 9; i++) {
            if (board[i] == 2) {
                return 0; // Game is not over yet
            }
        }

        displayBoard();
        System.out.println("Game Over. It's a draw!");
        return 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Tic Tac Toe!");

        while (true) {
            displayBoard();

            int playerMove;
            System.out.print("Enter your move (1-9): ");
            playerMove = scanner.nextInt();
            if (playerMove < 1 || playerMove > 9 || board[playerMove] != 2) {
                System.out.println("Invalid move. Please choose a valid empty position.");
                continue;
            }
            board[playerMove] = 3;

            if (gameover() == 1) {
                break;
            }

            makemove(5);

            if (gameover() == 1) {
                break;
            }
        }

        scanner.close();
    }
}