package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Pdf;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Pdf> PREDICATE_MATCHING_NO_PDFS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Pdf> toDisplay) {
        Optional<Predicate<Pdf>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPdfList(predicate.orElse(PREDICATE_MATCHING_NO_PDFS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Pdf... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Pdf} equals to {@code other}.
     */
    private static Predicate<Pdf> getPredicateMatching(Pdf other) {
        return pdf -> pdf.equals(other);
    }
}
