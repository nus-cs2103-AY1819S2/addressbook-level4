package seedu.address.model.tag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that represents a set of conditions of a Patient object.
 */
public class Conditions {

    private Set<ConditionTag> conditions;

    public Conditions() {
        this.conditions = new HashSet<>();
    }

    /**
     * Constructs a Conditions object from existing {@code conditions}
     *
     * @param conditions Conditions object to construct from
     */
    public Conditions(Conditions conditions) {
        this.conditions = new HashSet<>(conditions.getConditions());
    }

    /**
     * Constructs a Skills object from existing {@code skills} Set of
     * Specialisations.
     * TODO: Consider using variable argument list.
     *
     * @param conditions Set object containing Specialisation to construct from.
     */
    public Conditions(Collection<ConditionTag> conditions) {
        this.conditions = new HashSet<>(conditions);
    }

    public Set<ConditionTag> getConditions() {
        return this.conditions;
    }

    /**
     * Checks if a given conditionTag is in the current Conditions set.
     *
     * @param conditionTag conditionTag to check for.
     * @return true if conditionTag is in the set, false otherwise
     */
    public boolean contains(ConditionTag conditionTag) {
        return this.conditions.contains(conditionTag);
    }

    /**
     * Checks if a given conditionTag is in the current conditions set by first
     * converting the string to ConditionTag object.
     *
     * @param conditionTag string to check for.
     * @return true if ConditionTag object of the string conditionTag is in
     * the set, false otherwise
     */
    public boolean contains(String conditionTag) {
        if (!ConditionTag.isValidConditionTagName(conditionTag)) {
            return false;
        }

        ConditionTag conditionTagObject = ConditionTag.parseString(conditionTag);

        return this.conditions.contains(conditionTagObject);
    }

    public void addConditionTag(ConditionTag conditionTag) {
        this.conditions.add(conditionTag);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ConditionTag conditionTag : this.conditions) {
            stringBuilder.append(conditionTag.getName());
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Skills)) {
            return false;
        }

        return this.conditions.equals(((Conditions) other).getConditions());
    }
}
