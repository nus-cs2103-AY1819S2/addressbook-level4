package seedu.finance.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.category.Category;

/**
 * Jackson-friendly version of {@link CategoryBudget}.
 */
public class JsonAdaptedCategoryBudget {

    //public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final JsonAdaptedCategory category;

    private final String totalBudget;
    private final String currentSpending;

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    @JsonCreator
    public JsonAdaptedCategoryBudget(CategoryBudget source) {
        totalBudget = Double.toString(source.getTotalBudget());
        currentSpending = Double.toString(source.getCurrentSpendings());
        category = new JsonAdaptedCategory(source.getCategory());
    }

    /**
     * Converts this Jackson-friendly adapted record object into the model's {@code Budget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record.
     */
    public CategoryBudget toModelType() throws IllegalValueException {
        if (!Category.isValidCategoryName(category.getCategoryName())) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        Double currSpending = Double.parseDouble(currentSpending);
        Double totBudget = Double.parseDouble(totalBudget);
        Double currBudget = totBudget - currSpending;
        return new CategoryBudget(category.getCategoryName(), totBudget, currBudget);

    }

}


