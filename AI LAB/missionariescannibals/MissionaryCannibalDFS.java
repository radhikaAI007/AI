package missionariescannibals;
import java.util.*;

class State {
    int missionariesLeft, cannibalsLeft, boat; // boat = 1 means boat is on the starting side, boat = 0 means on the other side
    int depth;
    State parent;
    String action;

    State(int missionariesLeft, int cannibalsLeft, int boat, int depth, State parent, String action) {
        this.missionariesLeft = missionariesLeft;
        this.cannibalsLeft = cannibalsLeft;
        this.boat = boat;
        this.depth = depth;
        this.parent = parent;
        this.action = action;
    }
}

public class MissionaryCannibalDFS {
    public static void main(String[] args) {
        int totalMissionaries = 3;
        int totalCannibals = 3;

        solve(totalMissionaries, totalCannibals);
    }

    public static void solve(int totalMissionaries, int totalCannibals) {
        Stack<State> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        State root = new State(totalMissionaries, totalCannibals, 1, 0, null, "");
        stack.push(root);
        visited.add(encodeState(root));

        while (!stack.isEmpty()) {
            State current = stack.pop();

            if (isGoalState(current)) {
                System.out.println("\nGoal state reached");
                printPath(current);
                System.out.println("Depth: " + current.depth);
                return;
            }

            List<State> nextStates = getNextStates(current, totalMissionaries, totalCannibals);

            for (State next : nextStates) {
                String stateKey = encodeState(next);
                if (!visited.contains(stateKey)) {
                    stack.push(next);
                    visited.add(stateKey);
                }
            }
        }

        System.out.println("Goal state not reached");
    }

    public static boolean isGoalState(State state) {
        return state.missionariesLeft == 0 && state.cannibalsLeft == 0 && state.boat == 0;
    }

    public static List<State> getNextStates(State current, int totalMissionaries, int totalCannibals) {
        List<State> nextStates = new ArrayList<>();

        int[][] moves = {
            {1, 0}, // One missionary
            {2, 0}, // Two missionaries
            {0, 1}, // One cannibal
            {0, 2}, // Two cannibals
            {1, 1}  // One missionary and one cannibal
        };

        for (int[] move : moves) {
            int missionariesMove = move[0];
            int cannibalsMove = move[1];

            if (current.boat == 1) { // Boat on the starting side
                int newMissionariesLeft = current.missionariesLeft - missionariesMove;
                int newCannibalsLeft = current.cannibalsLeft - cannibalsMove;
                int newBoat = 0;

                if (isValidState(newMissionariesLeft, newCannibalsLeft, totalMissionaries, totalCannibals)) {
                    nextStates.add(new State(newMissionariesLeft, newCannibalsLeft, newBoat, current.depth + 1, current, 
                        "Move " + missionariesMove + " missionaries and " + cannibalsMove + " cannibals to the other side"));
                }
            } else { // Boat on the other side
                int newMissionariesLeft = current.missionariesLeft + missionariesMove;
                int newCannibalsLeft = current.cannibalsLeft + cannibalsMove;
                int newBoat = 1;

                if (isValidState(newMissionariesLeft, newCannibalsLeft, totalMissionaries, totalCannibals)) {
                    nextStates.add(new State(newMissionariesLeft, newCannibalsLeft, newBoat, current.depth + 1, current, 
                        "Move " + missionariesMove + " missionaries and " + cannibalsMove + " cannibals back to the starting side"));
                }
            }
        }

        return nextStates;
    }

    public static boolean isValidState(int missionariesLeft, int cannibalsLeft, int totalMissionaries, int totalCannibals) {
        if (missionariesLeft < 0 || cannibalsLeft < 0 || missionariesLeft > totalMissionaries || cannibalsLeft > totalCannibals) {
            return false;
        }
        if ((missionariesLeft > 0 && missionariesLeft < cannibalsLeft) || 
            (totalMissionaries - missionariesLeft > 0 && totalMissionaries - missionariesLeft < totalCannibals - cannibalsLeft)) {
            return false;
        }
        return true;
    }

    public static String encodeState(State state) {
        return state.missionariesLeft + "," + state.cannibalsLeft + "," + state.boat;
    }

    public static void printPath(State state) {
        if (state == null) {
            return;
        }
        printPath(state.parent);
        if (!state.action.isEmpty()) {
            System.out.println(state.action);
        }
        System.out.println("Missionaries Left: " + state.missionariesLeft + ", Cannibals Left: " + state.cannibalsLeft + ", Boat: " + (state.boat == 1 ? "Starting side" : "Other side"));
    }
}
