package EightPuzzle;
import java.util.Arrays;

class Node {
    Node parent;
    int[][] mat;
    int x, y;
    int level;

    public Node(int[][] mat, int x, int y, int newX, int newY, int level, Node parent) {
        this.parent = parent;
        this.mat = new int[EightPuzzleDFS.N][EightPuzzleDFS.N];
        for (int i = 0; i < EightPuzzleDFS.N; i++) {
            this.mat[i] = mat[i].clone();
        }
                // Swap the positions of the blank tile and the new position

        this.mat[x][y] = mat[newX][newY];
        this.mat[newX][newY] = 0;
        this.level = level;
        this.x = newX;
        this.y = newY;
    }
}

public class EightPuzzleDFS {
    static final int N = 3;
    static final int[] row = {0, -1, 1, 0};
    static final int[] col = {-1, 0, 0, 1};

    public static void main(String[] args) {
        int[][] initial = {
                {1, 2, 3},
                {5, 6, 0},
                {7, 8, 4}
        };

        int[][] goal = {
                {1, 2, 3},
                {0, 5, 6},
                {7, 8, 4}
        };

        int x = 1, y = 2;
        int depth = 10;
        solve(initial, x, y, goal, depth);
    }

    public static void solve(int[][] initial, int x, int y, int[][] goal, int depth) {
        Node root = new Node(initial, x, y, x, y, 0, null);
        if (!solveDFS(root, goal, depth)) {
            System.out.println("Solution not found within depth limit");
        }
    }

    public static boolean solveDFS(Node root, int[][] goal, int depth) {
        if (isEqual(root.mat, goal)) {
            printPath(root);
            return true;
        }

        if (depth <= 0) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            int newX = root.x + row[i];
            int newY = root.y + col[i];
            if (isSafe(newX, newY)) {
                Node child = new Node(root.mat, root.x, root.y, newX, newY, root.level + 1, root);
                if (solveDFS(child, goal, depth - 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSafe(int x, int y) {
        return (x >= 0 && x < N && y >= 0 && y < N);
    }

    public static boolean isEqual(int[][] mat1, int[][] mat2) {
        for (int i = 0; i < N; i++) {
            if (!Arrays.equals(mat1[i], mat2[i])) {
                return false;
            }
        }
        return true;
    }

    public static void printMatrix(int[][] mat) {
        for (int[] row : mat) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void printPath(Node root) {
        if (root == null) {
            return;
        }
        printPath(root.parent);
        printMatrix(root.mat);
        System.out.println("Depth: " + root.level);
        System.out.println();
    }
}
