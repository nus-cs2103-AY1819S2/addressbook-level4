package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Medicine> PREDICATE_MATCHING_NO_MEDICINES = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Medicine> toDisplay) {
        Optional<Predicate<Medicine>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredMedicineList(predicate.orElse(PREDICATE_MATCHING_NO_MEDICINES));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Medicine... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Medicine} equals to {@code other}.
     */
    private static Predicate<Medicine> getPredicateMatching(Medicine other) {
        return medicine -> medicine.equals(other);
    }
}
