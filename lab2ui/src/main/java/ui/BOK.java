package ui;

import ui.model.Clause;
import ui.model.Pair;
import ui.model.Relation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ui.model.Relation.*;

public class BOK {

    private List<Clause> clauses = new ArrayList<>();

    public boolean addClause(Clause clause) {
        return BOK.addClauseToList(clauses, clause);
    }

    public boolean removeClause(Clause clause) {
        boolean removed = clauses.remove(clause);
        numerateClauses(clauses);
        return removed;
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
        numerateClauses(list);
        return true;
    }

    private static void numerateClauses(List<Clause> list) {
        for (int i = 0; i < list.size(); i++)
            list.get(i).setId(i + 1);
    }

    public Set<Clause> resolve(Clause clause) {
        numerateClauses(clauses);
        clause.setId(clauses.size() + 1);
        Set<Clause> oldClauses = new HashSet<>(clauses);
        Set<Clause> newClauses = new HashSet<>();
        newClauses.add(clause);

        while (true) {
            for (Pair<Clause, Clause> pair : selectClauses(newClauses, oldClauses)) {
                Clause resolvent = pair.getFirst().resolve(pair.getSecond());
                if (resolvent.getLiterals().isEmpty()) {
                    newClauses.add(resolvent);
                    return newClauses;
                } else {
                    if (resolvent.getLiterals().size() < pair.getFirst().getLiterals().size() + pair.getSecond().getLiterals().size()){
                        resolvent.setId(oldClauses.size() + newClauses.size());
                    newClauses.add(resolvent);}
                }
            }
            if (oldClauses.containsAll(newClauses))
                return newClauses;
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
