import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

class Node {
    int jug1, jug2;
    int depth;
    Node parent;
    String action;

    Node(int jug1, int jug2, int depth, Node parent, String action) {
        this.jug1 = jug1;
        this.jug2 = jug2;
        this.depth = depth;
        this.parent = parent;
        this.action = action;
    }
}

public class WaterJugDFS {
    public static void main(String[] args) {
        int jug1Capacity = 3;
        int jug2Capacity = 4;
        int target = 2;

        solve(jug1Capacity, jug2Capacity, target);
    }

    static void solve(int jug1Capacity,int jug2Capacity,int target){
        Stack<Node> stack=new Stack<>();
        Set<String> visited=new HashSet<>();

        Node root=new Node(0, 0, 0, null, "");
        stack.push(root);
        visited.add("0,0");

        while(!stack.isEmpty()){
            Node curr=stack.pop();

            if(isGoalState(curr, target)){
                System.out.println("\nGoal state is reached");
                printPath(curr);
                System.out.println("Depth=>"+curr.depth);
                    System.out.println();
                return;
            }

            List<Node> nextState=new ArrayList<>();
            nextState.add(new Node(jug1Capacity, curr.jug2, curr.depth+1, curr, "Fill Jug 1"));
            nextState.add(new Node(curr.jug1, jug2Capacity, curr.depth+1, curr, "Fill Jug 2"));
            nextState.add(new Node(0, curr.jug2, curr.depth+1, curr, "Empty Jug 1"));
            nextState.add(new Node(curr.jug1, 0, curr.depth+1, curr, "Empty Jug 2"));
            nextState.add(new Node(Math.max(0,curr.jug1-(jug2Capacity-curr.jug2)), Math.min(jug2Capacity,curr.jug1+curr.jug2), curr.depth+1, curr, "Jug 1 -> Jug 2"));
            nextState.add(new Node(Math.min(jug1Capacity,curr.jug1+curr.jug2), Math.max(0,curr.jug2-(jug1Capacity-curr.jug1)), curr.depth+1, curr, "Jug 2 -> Jug 1"));
        
            for(Node state: nextState){
                String stateMove= state.jug1 + "," + state.jug2 ;
                if(!visited.contains(stateMove)){
                stack.push(state);
                visited.add(stateMove);
                }
            }

        }
            System.out.println("Goal not reached!") ;
    }

    public static boolean isGoalState(Node node, int target) {
        return node.jug1 == target || node.jug2 == target;
    }

    public static void printPath(Node node) {
        if (node == null) {
            return;
        }
        printPath(node.parent);
        if (!node.action.isEmpty()) {
            System.out.println(node.action);
        }
        System.out.println("Jug1: " + node.jug1 + ", Jug2: " + node.jug2);
    }
}
