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

        Set<Clause> oldClauses = new HashSet<>(clauses);
        List<Clause> newClauses = new ArrayList<>(clause.negateDisjunction());
        List<Clause> allClausesUsed = new LinkedList<>(newClauses);
        List<Clause> allClausesEver = new LinkedList<>(newClauses);

        while (true) {
            for (Pair<Clause, Clause> pair : selectClauses(newClauses, oldClauses)) {
                Clause resolvent = pair.getFirst().resolve(pair.getSecond());

                allClausesEver.add(resolvent);
                if (addClauseToList(newClauses, resolvent))
                    allClausesUsed.add(resolvent);

                if (resolvent.getLiterals().isEmpty())
                    return mode == Mode.LOUD ? allClausesEver : allClausesUsed;
            }
            if (oldClauses.containsAll(newClauses))
                return mode == Mode.LOUD ? allClausesEver : allClausesUsed;
            else
                oldClauses.addAll(newClauses);
        }
    }

    private List<Pair<Clause, Clause>> selectClauses(Collection<Clause> newClauses, Collection<Clause> oldClauses) {
        HashSet<Pair<Clause, Clause>> pairs = new HashSet<>();

        for (var newClause : newClauses) {

            for (var oldClause : oldClauses)
                if (newClause.hasOnlyOneComplement(oldClause))
                    pairs.add(new Pair<>(newClause, oldClause));

            for (var alsoNewClause : newClauses)
                if (newClause != alsoNewClause && newClause.hasOnlyOneComplement(alsoNewClause))
                    pairs.add(new Pair<>(newClause, alsoNewClause));
        }
        return new ArrayList<>(pairs);
    }

    public List<Clause> getClauses() {
        return clauses;
    }
}
