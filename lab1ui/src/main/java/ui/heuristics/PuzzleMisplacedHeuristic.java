package ui.heuristics;

import ui.structures.State;

public class PuzzleMisplacedHeuristic implements Heuristic {

    @Override
    public Double apply(State state) {
        int misplaced = 0;

        var charArray = state.getName().toCharArray();

        misplaced += charArray[0] != '1' ? 1 : 0;
        misplaced += charArray[1] != '2' ? 1 : 0;
        misplaced += charArray[2] != '3' ? 1 : 0;
        misplaced += charArray[4] != '4' ? 1 : 0;
        misplaced += charArray[5] != '5' ? 1 : 0;
        misplaced += charArray[6] != '6' ? 1 : 0;
        misplaced += charArray[8] != '7' ? 1 : 0;
        misplaced += charArray[9] != '8' ? 1 : 0;

        return (double) misplaced;
    }

    @Override
    public String name() {
        return "Misplaced";
    }
}
