package ui.algorithms;

import ui.model.Entry;

import java.util.List;
import java.util.Map;

public interface MLAlgorithm {

    void configure(Map<String, String> args);

    void fit(List<Entry> dataset);

    String predict(Entry testCase);
}
