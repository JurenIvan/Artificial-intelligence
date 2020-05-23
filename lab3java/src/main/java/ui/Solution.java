package ui;

import ui.algorithms.ID3;

public class Solution {

    public static void main(String[] args) {

        Inputter inputter = Inputter.getInstance(args[0], args[1], args[2]);

        //weather,temperature,humidity,wind,play
        System.out.println(ID3.informationGain(inputter.getTrainingEntries(), "play"));

        var id3 = new ID3();
        id3.fit(inputter.getTrainingEntries());
    }
}
