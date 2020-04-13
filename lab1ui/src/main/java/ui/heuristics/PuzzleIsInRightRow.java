package ui.heuristics;

import ui.structures.State;

public class PuzzleIsInRightRow implements Heuristic {
    @Override
    public Double apply(State state) {
        int isInRightRow = 0;

        var charArray = state.getName().toCharArray();

        isInRightRow += charArray[0] == '1' | charArray[1] == '1' | charArray[2] == '1' ? 0 : 1;
        isInRightRow += charArray[0] == '2' | charArray[1] == '2' | charArray[2] == '2' ? 0 : 1;
        isInRightRow += charArray[0] == '3' | charArray[1] == '3' | charArray[2] == '3' ? 0 : 1;
        isInRightRow += charArray[4] == '4' | charArray[5] == '4' | charArray[6] == '4' ? 0 : 1;
        isInRightRow += charArray[4] == '5' | charArray[5] == '5' | charArray[6] == '5' ? 0 : 1;
        isInRightRow += charArray[4] == '6' | charArray[5] == '6' | charArray[6] == '6' ? 0 : 1;
        isInRightRow += charArray[7] == '7' | charArray[8] == '7' | charArray[9] == '7' ? 0 : 1;
        isInRightRow += charArray[7] == '8' | charArray[8] == '8' | charArray[9] == '8' ? 0 : 1;

        return (double) isInRightRow;
    }

    @Override
    public String name() {
        return "Is In Right Row";
    }
}
