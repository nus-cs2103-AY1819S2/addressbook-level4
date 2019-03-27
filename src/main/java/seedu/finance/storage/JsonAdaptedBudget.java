package seedu.finance.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.record.Amount;


public class JsonAdaptedBudget {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";

    private final String budget;

    /**
     * Constructs a {@code JsonAdaptedRecord} with the given record details.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("budget") String budget) {
        this.budget = budget;
        }

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget source) {
        budget = source.toString();
    }

    /**
     * Converts this Jackson-friendly adapted record object into the model's {@code Budget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record.
     */
    public Budget toModelType() throws IllegalValueException {
        if (budget == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(budget)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }

        final Amount modelBudget = new Amount(budget);

        return new Budget(modelBudget);
    }

}
