package ui;

import ui.model.Clause;
import ui.model.Pair;
import ui.model.Relation;

import java.util.*;

import static ui.model.Relation.*;

public class BOK {

    private List<Clause> clauses = new ArrayList<>();

    public boolean addClause(Clause clause) {
        return BOK.addClauseToList(clauses, clause);
    }

    public boolean removeClause(Clause clause) {
        return clauses.remove(clause);
    }

    public static boolean addClauseToList(List<Clause> list, Clause clause) {
        for (int i = 0; i < list.size(); i++) {
            Relation relation = list.get(i).relationTo(clause);
            if (relation == UNRELATED) continue;
            if (relation == IS_SUBSET) return false;
            if (relation == IS_SUPERSET) list.remove(i--);
            if (relation == SAME) return false;
        }
        list.add(clause);
        return true;
    }

    private static void numerateClauses(List<Clause> list) {
        Clause.counter = 1;
        for (int i = 0; i < list.size(); i++)
            list.get(i).setId(Clause.counter++);
    }

    public Collection<Clause> resolve(Clause clause, Mode mode) {
        numerateClauses(clauses);
        clause.setId(Clause.counter++);

        Set<Clause> newClauses = new HashSet<>();
        Set<Clause> oldClauses = new HashSet<>(clauses);
        List<Clause> allClauses = new ArrayList<>();

        newClauses.add(clause);
        allClauses.add(clause);

        while (true) {
            for (Pair<Clause, Clause> pair : selectClauses(newClauses, oldClauses)) {
                Clause resolvent = pair.getFirst().resolve(pair.getSecond());
                if (resolvent.getLiterals().isEmpty()) {
                    allClauses.add(resolvent);
                    newClauses.add(resolvent);
                    return mode == Mode.LOUD ? allClauses : newClauses;
                } else {
                    allClauses.add(resolvent);
                    newClauses.add(resolvent);
                }
            }
            if (oldClauses.containsAll(newClauses))
                return mode == Mode.LOUD ? allClauses : newClauses;
            else
                oldClauses.addAll(newClauses);
        }
    }

    private List<Pair<Clause, Clause>> selectClauses(Set<Clause> newClauses, Set<Clause> oldClauses) {
        HashSet<Pair<Clause, Clause>> pairs = new HashSet<>();

        for (var newClause : newClauses) {

            for (var oldClause : oldClauses)
                if (newClause.hasComplement(oldClause)) pairs.add(new Pair<>(newClause, oldClause));

            for (var alsoNewClause : newClauses)
                if (newClause.hasComplement(alsoNewClause)) pairs.add(new Pair<>(newClause, alsoNewClause));
        }
        return new ArrayList<>(pairs);
    }

    public List<Clause> getClauses() {
        return clauses;
    }
}
