

import java.util.*;

public class TicTacToePruning {
    private static boolean gameEnded = false;
    private static boolean player = true;
    private static Scanner in = new Scanner(System.in);
    private static Board board = new Board();
    private static int branchesVisited = 0; // Counter for branches visited

    public static void main(String[] args) {
        System.out.println(board);
        long startTime = System.currentTimeMillis(); // Record start time

        while (!gameEnded) {
            Position position = null;
            if (player) {
                position = makeMove();
                board = new Board(board, position, PlayerSign.Cross);
            } else {
                board = findBestMove(board);
            }
            player = !player;
            System.out.println(board);
            evaluateGame();
        }
        long endTime = System.currentTimeMillis(); // Record end time
        System.out.println("Total branches visited: " + branchesVisited); // Output total branches visited
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds"); // Output execution time
    }

    private static Board findBestMove(Board board) {
        ArrayList<Position> positions = board.getFreePositions();
        Board bestChild = null;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        for (Position p : positions) {
            Board child = new Board(board, p, PlayerSign.Circle);
            int current = max(child, alpha, beta);
            System.out.println("Position of Child: " + p.row + "," + p.column);
            System.out.println("Child Score: " + current + "\n");
            System.out.println("Aplha in Child==>" + alpha);
            System.out.println("Beta in Child==>" + beta);
            if (current > alpha) {
                alpha = current;
                bestChild = child;
            }
        }
        return bestChild;
    }

    public static int max(Board board, int alpha, int beta) {
        GameState gameState = board.getGameState();
        if (gameState == GameState.CircleWin)
            return 1;
        else if (gameState == GameState.CrossWin)
            return -1;
        else if (gameState == GameState.Draw)
            return 0;
        ArrayList<Position> positions = board.getFreePositions();
        for (Position p : positions) {
            branchesVisited++; // Increment counter for each branch explored

            Board b = new Board(board, p, PlayerSign.Cross);
            alpha = Math.max(alpha, min(b, alpha, beta));

            if (beta <= alpha) {
                // System.out.println("Aplha in MAX==>" + alpha);
                // System.out.println("Beta in MAX==>" + beta);
                break;
            }
        }
        return alpha;
    }

    public static int min(Board board, int alpha, int beta) {
        GameState gameState = board.getGameState();
        if (gameState == GameState.CircleWin)
            return 1;
        else if (gameState == GameState.CrossWin)
            return -1;
        else if (gameState == GameState.Draw)
            return 0;
        ArrayList<Position> positions = board.getFreePositions();
        for (Position p : positions) {
            branchesVisited++; // Increment counter for each branch explored

            Board b = new Board(board, p, PlayerSign.Circle);
            beta = Math.min(beta, max(b, alpha, beta));

            if (beta <= alpha) {
                // System.out.println("Aplha in MIN==>" + alpha);
                // System.out.println("Beta in MIN==>" + beta);
                break;
            }
        }
        return beta;
    }

    public static void evaluateGame() {
        GameState gameState = board.getGameState();
        gameEnded = true;
        switch (gameState) {
            case CrossWin:
                System.out.println("Game Over! Cross Won!");
                break;
            case CircleWin:
                System.out.println("Game Over! Circle Won!");
                break;
            case Draw:
                System.out.println("Game Over! Game Drawn!");
                break;
            default:
                gameEnded = false;
                break;
        }
    }

    public static Position makeMove() {
        Position position = null;
        while (true) {
            System.out.print("Select row  0, 1 or 2: ");
            int row = getColOrRow();
            System.out.print("Select column  0, 1 or 2: ");
            int column = getColOrRow();
            position = new Position(column, row);
            if (board.isMarked(position))
                System.out.println("Position already marked!");
            else
                break;
        }
        return position;
    }

    private static int getColOrRow() {
        int ret = -1;
        while (true) {
            try {
                ret = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
            }
            if (ret < 0 | ret > 2)
                System.out.print("\nIllegal input... please re-enter: ");
            else
                break;
        }
        return ret;
    }
}

enum PlayerSign {
    Cross, Circle
}

enum GameState {
    Incomplete, CrossWin, CircleWin, Draw
}

final class Position {
    public final int column;
    public final int row;

    public Position(int column, int row) {
        this.column = column;
        this.row = row;
    }
}

class Board {
    private char[][] board; // e = empty, x = cross, o = circle.

    public Board() {
        board = new char[3][3];
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                board[x][y] = 'e'; // Board initially empty
    }

    public Board(Board from, Position position, PlayerSign sign) {
        board = new char[3][3];
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                board[x][y] = from.board[x][y];
        board[position.column][position.row] = sign == PlayerSign.Cross ? 'x' : 'o';
    }

    public ArrayList<Position> getFreePositions() {
        ArrayList<Position> retArr = new ArrayList<>();
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                if (board[x][y] == 'e')
                    retArr.add(new Position(x, y));
        return retArr;
    }

    public GameState getGameState() {
        if (checkWin('x'))
            return GameState.CrossWin;
        else if (checkWin('o'))
            return GameState.CircleWin;
        else if (getFreePositions().size() == 0)
            return GameState.Draw;
        else
            return GameState.Incomplete;
    }

    private boolean checkWin(char sign) { // 8 ways to win.
        if (board[1][1] == sign) {
            if (board[0][0] == sign && board[2][2] == sign)
                return true;
            if (board[0][2] == sign && board[2][0] == sign)
                return true;
            if (board[1][0] == sign && board[1][2] == sign)
                return true;
            if (board[0][1] == sign && board[2][1] == sign)
                return true;
        }
        if (board[0][0] == sign) {
            if (board[0][1] == sign && board[0][2] == sign)
                return true;
            if (board[1][0] == sign && board[2][0] == sign)
                return true;
        }
        if (board[2][2] == sign) {
            if (board[1][2] == sign && board[0][2] == sign)
                return true;
            if (board[2][1] == sign && board[2][0] == sign)
                return true;
        }
        return false;
    }

    public boolean isMarked(Position position) {
        if (board[position.column][position.row] != 'e')
            return true;
        return false;
    }

    public String toString() {
        String retString = "\n";
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (board[x][y] == 'x' || board[x][y] == 'o')
                    retString += "|" + board[x][y] + "|";
                else
                    retString += "| |";
            }
            retString += "\n";
        }
        return retString;
    }
}
