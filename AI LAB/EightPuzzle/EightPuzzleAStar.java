package EightPuzzle;

import java.util.*;

class Node {
    Node parent;
    int[][] mat;
    int x, y;
    int depth;
    int cost;
    String action;

    public Node(int[][] mat, int x, int y, int newX, int newY, int depth, Node parent, String action) {
        this.mat = new int[mat.length][];
        for (int i = 0; i < mat.length; i++) {
            this.mat[i] = mat[i].clone();
        }
        this.x = newX;
        this.y = newY;
        swap(this.mat, x, y, newX, newY);
        this.depth = depth;
        this.parent = parent;
        this.action = action;
    }

    private void swap(int[][] mat, int x1, int y1, int x2, int y2) {
        int temp = mat[x1][y1];
        mat[x1][y1] = mat[x2][y2];
        mat[x2][y2] = temp;
    }
}

class CompareCost implements Comparator<Node> {
    public int compare(Node a, Node b) {
        return Integer.compare(a.cost, b.cost);
    }
}

public class EightPuzzleAStar {
    private static final int N = 3;

    public static boolean isGoalState(int[][] mat, int[][] goal) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (mat[i][j] != goal[i][j])
                    return false;
            }
        }
        return true;
    }

    public static int heuristics(int[][] mat, int[][] goal) {
        int h = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (mat[i][j] != 0 && mat[i][j] != goal[i][j]) {
                    h++;
                }
            }
        }
        return h;
    }

    public static void printMatrix(int[][] mat) {
        for (int[] row : mat) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printPath(Node root) {
        if (root == null)
            return;
        printPath(root.parent);
        if (root.action != null && !root.action.isEmpty()) {
            System.out.println("Move: " + root.action);
        }
        printMatrix(root.mat);
    }

    public static void solve(int[][] initial, int x, int y, int[][] goal) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new CompareCost());
        Node root = new Node(initial, x, y, x, y, 0, null, "");
        root.cost = heuristics(initial, goal);
        pq.add(root);

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (isGoalState(current.mat, goal)) {
                System.out.println("Goal state reached");
                printPath(current);
                System.out.println("Depth: " + current.depth);
                System.out.println("Cost: " + current.depth);
                return;
            }

            int[] dx = { 0, 0, -1, 1 };
            int[] dy = { 1, -1, 0, 0 };
            String[] directions = { "right", "left", "up", "down" };

            for (int i = 0; i < 4; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];

                if (newX >= 0 && newX < N && newY >= 0 && newY < N) {
                    Node child = new Node(current.mat, current.x, current.y, newX, newY, current.depth + 1, current,
                            directions[i]);
                    child.cost = child.depth + heuristics(child.mat, goal);
                    pq.add(child);
                }
            }
        }

        System.out.println("Goal state not reached");
    }

    public static void main(String[] args) {
        int[][] initial = {
                { 1, 2, 3 },
                { 0, 4, 6 },
                { 7, 5, 8 }
        };

        int[][] goal = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 }
        };

        int x = 1, y = 0;
        solve(initial, x, y, goal);
    }
}
