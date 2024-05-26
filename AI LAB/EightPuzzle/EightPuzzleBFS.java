package EightPuzzle;
import java.util.*;

class Node {
    Node parent;
    int[][] mat;
    int x, y; // Position of the empty tile (0)
    int level; // Depth level

    public Node(int[][] mat, int x, int y, int newX, int newY, int level, Node parent) {
        this.parent = parent;
        this.mat = new int[EightPuzzleBFS.N][EightPuzzleBFS.N];
        for (int i = 0; i < EightPuzzleBFS.N; i++) {
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

public class EightPuzzleBFS {
    static final int N = 3;
    static final int[] row = { 1, 0, -1, 0 };
    static final int[] col = { 0, -1, 0, 1 };

    public static void main(String[] args) {
        int[][] initial = {
                { 1, 2, 3 },
                { 5, 6, 0 },
                { 7, 8, 4 }
        };

        int[][] goal = {
                { 1, 2, 3 },
                { 7, 5, 6 },
                { 8, 4, 0 }
        };

        int x = 1, y = 2;
        solve(initial, x, y, goal);
    }

    public static void solve(int[][] initial, int x, int y, int[][] goal) {
        Queue<Node> q = new LinkedList<>();
        Node root = new Node(initial, x, y, x, y, 0, null);
        q.add(root);

        while (!q.isEmpty()) {
            Node curr = q.poll();

            if (isEqual(curr.mat, goal)) {
                printPath(curr);
                return;
            }

            for (int i = 0; i < 4; i++) {
                int newX = curr.x + row[i];
                int newY = curr.y + col[i];

                if (isSafe(newX, newY)) {
                    Node child = new Node(curr.mat, curr.x, curr.y, newX, newY, curr.level + 1, curr);
                    q.add(child);
                }
            }
        }
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
        System.out.println();
    }
}
