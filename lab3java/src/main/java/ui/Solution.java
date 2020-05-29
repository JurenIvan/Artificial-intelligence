package ui;

import ui.algorithms.ID3;
import ui.algorithms.MLAlgorithm;
import ui.algorithms.RF;

import java.util.ArrayList;
import java.util.List;

import static ui.model.Entry.*;

public class Solution {

    public static void main(String[] args) {
        Inputter inputter = Inputter.getInstance(args[0], args[1], args[2]);
        MLAlgorithm mlAlgorithm = inputter.getConfigArgs().get("model").equals("ID3") ? new ID3(getAttrNames()) : new RF();
        mlAlgorithm.configure(inputter.getConfigArgs());
        mlAlgorithm.fit(inputter.getTrainingEntries());
        
        if (inputter.getConfigArgs().get("model").equals("ID3"))
            System.out.println(mlAlgorithm);

        List<String> solutions = new ArrayList<>();
        for (var entry : inputter.getTestEntries())
            solutions.add(mlAlgorithm.predict(entry));

        solutions.forEach(e -> System.out.print(e + " "));

        int correctCnt = 0;
        for (int i = 0; i < solutions.size(); i++) {
            if (solutions.get(i).equals(inputter.getTestEntries().get(i).getValue(getClassifierName())))
                correctCnt++;
        }
        System.out.printf("\n%.5f%n", correctCnt / (double) solutions.size());

        int possibleClassifierStates = getPossibleValues(getClassifierName()).size();
        int[][] confusionMatrix = new int[possibleClassifierStates][possibleClassifierStates];
        List<String> possibleStates = new ArrayList<>(getPossibleValues(getClassifierName()));
        possibleStates.sort(String::compareTo);

        for (int i = 0; i < solutions.size(); i++) {
            String pred = solutions.get(i);
            String actual = inputter.getTestEntries().get(i).getValue(getClassifierName());

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
