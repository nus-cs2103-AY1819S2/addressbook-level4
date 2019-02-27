package seedu.address.model.tag;

import java.util.HashSet;
import java.util.Set;

/**
 * Class that represents a set of skills of a HealthWorker object.
 */
public class Skills {

    private Set<Specialisation> skills;

    /**
     * Constructs a Skills object from existing {@code skills}
     *
     * @param skills set of Specialisations to construct from
     */
    public Skills(Skills skills) {
        this.skills = skills.getSkills();
    }

    public Skills() {
        this.skills = new HashSet<>();
    }

    public Set<Specialisation> getSkills() {
        return this.skills;
    }

    /**
     * Checks if a given Specialisation is in the current skills set.
     * @param specialisation specialisation to check for.
     * @return true if Specialisation is in the set, false otherwise
     */
    public boolean contains(Specialisation specialisation) {
        return this.skills.contains(specialisation);
    }

    /**
     * Checks if a given Specialisation is in the current skills set by first
     * converting the string to Specialisation object.
     * @param specialisation string to check for.
     * @return true if Specialisation Enum of the string specialisation is in
     * the set, false otherwise
     */
    public boolean contains(String specialisation) {
        if (!Specialisation.isValidSpecialisation(specialisation)) {
            return false;
        }

        Specialisation specialisationEnum = Specialisation
                .parseString(specialisation);

        return this.skills.contains(specialisationEnum);
    }

    public void addSpecialisation(Specialisation specialisation) {
        this.skills.add(specialisation);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Skills: \n");
        for (Specialisation specialisation : this.skills) {
            stringBuilder.append(specialisation.name());
        }

        return stringBuilder.toString();
    }
}
