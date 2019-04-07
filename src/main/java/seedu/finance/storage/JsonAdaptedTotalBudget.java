package seedu.finance.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.budget.TotalBudget;
import seedu.finance.model.record.Amount;

import java.util.ArrayList;
import java.util.List;

/**
 * Jackson-friendly version of {@link Budget}.
 */
public class JsonAdaptedTotalBudget {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final String totalBudget;

    private final String currentBudget;

    private final List<JsonAdaptedCategoryBudget> categoryBudgets = new ArrayList();

    /**
     * Constructs a {@code JsonAdaptedBudget} with the given record details.
     */
    @JsonCreator
    public JsonAdaptedTotalBudget(@JsonProperty("totalBudget") String totalBudget,
                             @JsonProperty("currentBudget") String currentBudget,
                             @JsonProperty("categoryBudgets") List<JsonAdaptedCategoryBudget> categoryBudgets) {
        this.totalBudget = totalBudget;
        this.currentBudget = currentBudget;
        categoryBudgets.addAll(categoryBudgets);
    }

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    public JsonAdaptedTotalBudget(TotalBudget source) {
        currentBudget = Double.toString(source.getCurrentBudget());
        totalBudget = Double.toString(source.getTotalBudget());
        categoryBudgets.addAll(source.getCategoryBudgets());
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
