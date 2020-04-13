package ui.heuristics;

import ui.structures.State;

public class PuzzleManhattanHeuristic implements Heuristic {
    @Override
    public Double apply(State state) {
        int result = 0;

        char[] arr = state.getName().replace("_", "").replace("x", "9").toCharArray();
        for (int i = 1; i <= 9; i++) {
            var zapisanBroj = Integer.parseInt(arr[i - 1] + "");
            var xZapisanog = ((zapisanBroj - 1) / 3) + 1;
            var yZapisanog = ((zapisanBroj - 1) % 3) + 1;

            var xTrenutni = ((i - 1) / 3) + 1;
            var yTrenutni = ((i - 1) % 3) + 1;

            result += (Math.abs(yTrenutni - yZapisanog) + Math.abs(xTrenutni - xZapisanog));
        }
        return (double) result;
    }

    @Override
    public String name() {
        return "Manhattan";
    }
}
