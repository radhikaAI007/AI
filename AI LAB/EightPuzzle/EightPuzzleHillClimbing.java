package EightPuzzle;

import java.util.Scanner;

class node {
    int[][] board;
    int depth;
    node parent;
    String action;

    node(int[][] board, int depth, node parent, String action) {
        this.board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = board[i][j];
            }
        }
        this.depth = depth;
        this.parent = parent;
        this.action = action;
    }
}

public class EightPuzzleHillClimbing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] initialBoard = new int[3][3];
        int[][] goalBoard = new int[3][3];

        System.out.println("Enter initial board configuration - 0 denotes empty position");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println("Enter value at position [" + i + "][" + j + "]");
                initialBoard[i][j] = scanner.nextInt();
            }
        }

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Enter final board configuration - 0 denotes empty position");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println("Enter value at position [" + i + "][" + j + "]");
                goalBoard[i][j] = scanner.nextInt();
            }
        }

        System.out.println("\n---------------------------Initial Board-------------------------------\n");
        displayBoard(initialBoard);

        System.out.println("\n---------------------------Final Board---------------------------------\n");
        displayBoard(goalBoard);

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("\nCalling Steepest Ascent Hill Climbing algorithm\n");
        solve(initialBoard, goalBoard);
        scanner.close();
    }

    public static void solve(int[][] initialBoard, int[][] goalBoard) {
        node root = new node(initialBoard, 0, null, "");
        steepestAscentHillClimbing(root, goalBoard, 0);
    }

    public static void steepestAscentHillClimbing(node currentNode, int[][] goalBoard, int formerMove) {
        int[] arr = { Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE };
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentNode.board[i][j] == 0) {
                    if (i > 0 && formerMove != 2) {
                        System.out.println("Examining child (move 0 upwards)");
                        arr[0] = evaluateBoard(currentNode.board, goalBoard, 1);
                    }
                    if (i < 2 && formerMove != 1) {
                        System.out.println("Examining child (move 0 downwards)");
                        arr[1] = evaluateBoard(currentNode.board, goalBoard, 2);
                    }
                    if (j > 0 && formerMove != 4) {
                        System.out.println("Examining child (move 0 leftwards)");
                        arr[2] = evaluateBoard(currentNode.board, goalBoard, 3);
                    }
                    if (j < 2 && formerMove != 3) {
                        System.out.println("Examining child (move 0 rightwards)");
                        arr[3] = evaluateBoard(currentNode.board, goalBoard, 4);
                    }
                }
            }
            System.out.println();
        }

        int localOptimum = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < 4; i++) {
            if (arr[i] < localOptimum) {
                localOptimum = arr[i];
                index = i + 1;
            }
        }

        moveTile(currentNode.board, index);
        System.out.println("Next state = minimum Manhattan distance:");
        displayBoard(currentNode.board);

        if (localOptimum == 0) {
            System.out.println("Goal state reached");
        } else {
            steepestAscentHillClimbing(currentNode, goalBoard, index);
        }
    }

    public static int evaluateBoard(int[][] currentBoard, int[][] goalBoard, int move) {
        int[][] tempBoard = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tempBoard[i][j] = currentBoard[i][j];
            }
        }
        moveTile(tempBoard, move);
        displayBoard(tempBoard);
        int manhattan = calculateManhattanDistance(tempBoard, goalBoard);
        System.out.println("Current Manhattan distance: " + manhattan + "\n\n\n");
        return manhattan;
    }

    public static void moveTile(int[][] board, int move) {
        boolean flag = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    switch (move) {
                        case 1:
                            if (i > 0) {
                                swap(board, i, j, i - 1, j);
                                flag = true;
                            }
                            break;
                        case 2:
                            if (i < 2) {
                                swap(board, i, j, i + 1, j);
                                flag = true;
                            }
                            break;
                        case 3:
                            if (j > 0) {
                                swap(board, i, j, i, j - 1);
                                flag = true;
                            }
                            break;
                        case 4:
                            if (j < 2) {
                                swap(board, i, j, i, j + 1);
                                flag = true;
                            }
                            break;
                    }
                    if (flag)
                        return;
                }
            }
        }
    }

    private static void swap(int[][] board, int x1, int y1, int x2, int y2) {
        int temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
    }

    public static int calculateManhattanDistance(int[][] currentBoard, int[][] goalBoard) {
        int manhattanDistance = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBoard[i][j] > 0) {
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if (goalBoard[k][l] == currentBoard[i][j]) {
                                manhattanDistance += Math.abs(i - k) + Math.abs(j - l);
                            }
                        }
                    }
                }
            }
        }
        return manhattanDistance;
    }

    public static void displayBoard(int[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%8d", board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
