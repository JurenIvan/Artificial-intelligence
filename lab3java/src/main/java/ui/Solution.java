package ui;

import ui.algorithms.ID3;
import ui.model.Entry;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {

        Inputter inputter = Inputter.getInstance(args[0], args[1], args[2]);

        var id3 = new ID3();
        id3.fit(inputter.getTrainingEntries());
        System.out.println(id3);
        List<String> solutions = new ArrayList<>();
        for (var entry : inputter.getTestEntries())
            solutions.add(id3.predict(entry));

        solutions.forEach(e -> System.out.print(e + " "));

        int correctCnt = 0;
        for (int i = 0; i < solutions.size(); i++) {
            if (solutions.get(i).equals(inputter.getTestEntries().get(i).getValue(Entry.getClassifierName())))
                correctCnt++;
        }
        System.out.printf("\n%.5f%n", correctCnt / (double) solutions.size());

        int possibleClassifierStates = Entry.getPossibleValues(Entry.getClassifierName()).size();
        int[][] confusionMatrix = new int[possibleClassifierStates][possibleClassifierStates];
        List<String> possibleStates = new ArrayList<>(Entry.getPossibleValues(Entry.getClassifierName()));
        possibleStates.sort(String::compareTo);

        for (int i = 0; i < solutions.size(); i++) {
            String pred = solutions.get(i);
            String actual = inputter.getTestEntries().get(i).getValue(Entry.getClassifierName());

            confusionMatrix[possibleStates.indexOf(pred)][possibleStates.indexOf(actual)]++;
        }


        for (int i = 0; i < possibleStates.size(); i++) {
            for (int j = 0; j < possibleStates.size(); j++) {
                System.out.print(confusionMatrix[j][i] + " ");
            }
            System.out.println();
        }

    }
}
