package seedu.finance.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.record.Amount;

/**
 * Jackson-friendly version of {@link Budget}.
 */
public class JsonAdaptedCategoryBudget {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final String totalBudget;

    private final String currentBudget;

    /**
     * Constructs a {@code JsonAdaptedBudget} with the given record details.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("totalBudget") String totalBudget,
                             @JsonProperty("currentBudget") String currentBudget) {
        this.totalBudget = totalBudget;
        this.currentBudget = currentBudget;
    }

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget source) {
        currentBudget = Double.toString(source.getCurrentBudget());
        totalBudget = Double.toString(source.getTotalBudget());
    }

    /**
     * Converts this Jackson-friendly adapted record object into the model's {@code Budget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record.
     */
    public Budget toModelType() throws IllegalValueException {
        if (currentBudget == null || totalBudget == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        try {
            Double currBudget = Double.parseDouble(currentBudget);
            Double totBudget = Double.parseDouble(totalBudget);
            return new Budget(totBudget, currBudget);
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException(String.format(Amount.MESSAGE_CONSTRAINTS));
        }
    }

}
