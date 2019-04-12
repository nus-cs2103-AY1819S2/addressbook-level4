package seedu.finance.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private final String currentBudget;

    /**
     * Constructs a {@code JsonAdaptedCategoryBudget} with the given record details.
     */
    @JsonCreator
    public JsonAdaptedCategoryBudget(@JsonProperty("category") JsonAdaptedCategory category,
                             @JsonProperty("totalBudget") String totalBudget,
                             @JsonProperty("currentBudget") String currentBudget) {
        this.category = category;
        this.totalBudget = totalBudget;
        this.currentBudget = currentBudget;
    }

    /**
     * Converts a given {@code CategoryBudget} into this class for Jackson use.
     */
    public JsonAdaptedCategoryBudget(CategoryBudget source) {
        totalBudget = Double.toString(source.getTotalBudget());
        currentBudget = Double.toString(source.getCurrentBudget());
        category = new JsonAdaptedCategory(source.getCategory());
    }

    /**
     * Converts this Jackson-friendly adapted record object into the model's {@code CategoryBudget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record.
     */
    public CategoryBudget toModelType() throws IllegalValueException {
        if (!Category.isValidCategoryName(category.getCategoryName())) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        Double currBudget = Double.parseDouble(currentBudget);
        Double totBudget = Double.parseDouble(totalBudget);
        return new CategoryBudget(category.getCategoryName(), totBudget, currBudget);

    }

}


