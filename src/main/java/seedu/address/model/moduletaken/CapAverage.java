package seedu.address.model.moduletaken;

import java.text.DecimalFormat;
import java.util.ArrayList;

import seedu.address.model.moduleinfo.ModuleInfoCredits;

/**
 * Represents the average cap based on the weights of all the counted caps in the list.
 */
public class CapAverage {

    public static final double SINGLE_CREDIT = 1;
    public static final String MESSAGE_CONSTRAINTS =
            "Cap limit should from 0.00 to 5.00 to 2 decimal places";
    public static final String VALIDATION_REGEX = "(([0-4](\\.[0-9]([0-9])?)?)|(5(\\.00?)?))";
    private ArrayList<WeightedGrade> weightedGrades;

    /**
     * Constructs a {@code CapAverage}.
     */
    public CapAverage() {
        this.weightedGrades = new ArrayList<>();
    }

    /**
     * Constructs a {@code CapAverage}.
     */
    public CapAverage(double capAverage) {
        this.weightedGrades = new ArrayList<>();
        addWeightedGrade(capAverage, new ModuleInfoCredits(SINGLE_CREDIT));
    }

    /**
     * Adds a cap into the list of caps to be averaged
     *
     * @param gradeCap The grade point to be added to the list of caps
     * @param credits Amount of module credits to weigh the cap in the average
     */
    public void addWeightedGrade(double gradeCap, ModuleInfoCredits credits) {
        WeightedGrade toAdd = new WeightedGrade(gradeCap, credits);
        weightedGrades.add(toAdd);
    }

    @Override
    public String toString() {
        return String.valueOf(getCapLimit());
    }

    /**
     * Returns true if a given string is a valid cap average.
     */
    public static boolean isValidCapAverage(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Computes the average CAP of the list of WeightedGrades
     *
     * @return the average CAP
     */
    public double getCapLimit() {
        if (weightedGrades.isEmpty()) {
            return 0;
        }

        double sum = 0;
        double sumCredits = 0;
        for (int i = 0; i < weightedGrades.size(); i++) {
            double score = weightedGrades.get(i).getScore();
            double credit = weightedGrades.get(i).getModuleInfoCredits().getCredits();
            sum += score * credit;
            sumCredits += credit;
        }
        return roundCap(sum / sumCredits);
    }

    /**
     * Returns the rounded CAP to 2 decimal places.
     *
     * @param unroundedCap A raw CAP with any number of decimal places
     * @return a rounded cap to 2 decimal places
     */
    public double roundCap(double unroundedCap) {
        DecimalFormat twoDecimalPlaces = new DecimalFormat("##.00");
        String twoDecimalPlacesString = twoDecimalPlaces.format(unroundedCap);
        return Double.parseDouble(twoDecimalPlacesString);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CapAverage // instanceof handles nulls
                && getCapLimit() == ((CapAverage) other).getCapLimit()); // state check
    }

    @Override
    public int hashCode() {
        return weightedGrades.hashCode();
    }
}
