package ui.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ui.model.Relation.*;

public class Clause {

    private int id;
    private Map<String, Boolean> literals;
    private Clause derivedFirst;
    private Clause derivedSecond;

    public static Clause parse(String line) {
        HashMap<String, Boolean> literals = new HashMap<>();

        for (String token : line.split(" ")) {
            token = token.trim().toLowerCase();
            if (token.isBlank()) continue;
            if (token.equals("v")) continue;
            if (token.startsWith("~"))
                literals.put(token.substring(1), true);
            else literals.put(token, false);
        }

        return new Clause(literals, null, null);
    }

    public Clause(Map<String, Boolean> literals, Clause derivedFirst, Clause derivedSecond) {
        this.literals = literals;
        this.derivedFirst = derivedFirst;
        this.derivedSecond = derivedSecond;
    }

    public Clause(int id, Map<String, Boolean> literals, Clause derivedFirst, Clause derivedSecond) {
        this.id = id;
        this.literals = literals;
        this.derivedFirst = derivedFirst;
        this.derivedSecond = derivedSecond;
    }

    public Map<String, Boolean> getLiterals() {
        return literals;
    }

    public void setLiterals(HashMap<String, Boolean> literals) {
        this.literals = literals;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clause)) return false;
        Clause clause = (Clause) o;
        return getLiterals().equals(clause.getLiterals());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLiterals());
    }

    public Relation relationTo(Clause clause) {
        int matchCount = 0;
        Map<String, Boolean> others = clause.getLiterals();

        for (var literal : literals.entrySet())
            if (others.containsKey(literal.getKey()) && others.get(literal.getKey()) == literal.getValue())
                matchCount++;

        if (matchCount == literals.size() && matchCount == others.size()) return SAME;
        if (matchCount == literals.size() && others.size() > matchCount) return IS_SUBSET;
        if (matchCount == others.size()) return IS_SUPERSET;
        return UNRELATED;
    }

    public boolean hasComplement(Clause clause) {
        for (var literal : literals.entrySet()) {
            if (clause.literals.containsKey(literal.getKey()) && clause.literals.get(literal.getKey()) != literal.getValue())
                return true;
        }
        return false;
    }

    public String toString() {
        if (derivedFirst != null && derivedSecond != null)
            return String.format("%3d.  %s (%d,%d)", id, literalsToString(), derivedFirst.id, derivedSecond.id);
        return String.format("%3d.  %s", id, literalsToString());
    }

    private String literalsToString() {
        if (literals.isEmpty()) return "NILL";
        StringBuilder sb = new StringBuilder();
        for (var literal : literals.entrySet())
            sb.append(literal.getValue() ? "~" : "").append(literal.getKey()).append(" v ");
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    public Clause resolve(Clause second) {
        HashMap<String, Boolean> literals_combined = new HashMap<>(literals);

        for (var literalEntry : second.literals.entrySet()) {
            var current = literals_combined.get(literalEntry.getKey());
            if (current != null && current != literalEntry.getValue())
                literals_combined.remove(literalEntry.getKey());
            else
                literals_combined.put(literalEntry.getKey(), literalEntry.getValue());
        }

        return new Clause(literals_combined, this, second);
    }


    public int getId() {
        return id;
    }
}
