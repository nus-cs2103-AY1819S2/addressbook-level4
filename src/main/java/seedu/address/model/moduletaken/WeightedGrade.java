package seedu.address.model.moduletaken;

import java.util.Objects;

import seedu.address.model.moduleinfo.ModuleInfoCredits;

/**
 * Represents the combination of the grade attained for the module weighted by the amount of credits.
 */
public class WeightedGrade {
    public final double score;
    public final ModuleInfoCredits credits;

    public WeightedGrade(double score, ModuleInfoCredits credits) {
        this.score = score;
        this.credits = credits;
    }

    public double getScore() {
        return this.score;
    }

    public ModuleInfoCredits getModuleInfoCredits() {
        return this.credits;
    }

    /**
     * Returns true if both WeightedGrades have the same score and credits.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof WeightedGrade)) {
            return false;
        }

        WeightedGrade otherWeightedGrade = (WeightedGrade) other;
        return otherWeightedGrade.getScore() == getScore()
                && otherWeightedGrade.getModuleInfoCredits().equals(getModuleInfoCredits());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(score, credits);
    }
}
