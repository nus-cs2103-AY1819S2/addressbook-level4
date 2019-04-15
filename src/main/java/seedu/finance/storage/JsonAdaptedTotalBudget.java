package seedu.finance.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.finance.commons.exceptions.IllegalValueException;

import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.budget.TotalBudget;
import seedu.finance.model.record.Amount;

/**
 * Jackson-friendly version of {@link TotalBudget}.
 */
public class JsonAdaptedTotalBudget {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final String totalBudget;

    private final String currentBudget;

    private final List<JsonAdaptedCategoryBudget> categoryBudgets = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTotalBudget} with the given budget details.
     */
    @JsonCreator
    public JsonAdaptedTotalBudget(@JsonProperty("totalBudget") String totalBudget,
                             @JsonProperty("currentBudget") String currentBudget,
                             @JsonProperty("categoryBudgets") List<JsonAdaptedCategoryBudget> categoryBudgets) {
        this.totalBudget = totalBudget;
        this.currentBudget = currentBudget;
        this.categoryBudgets.addAll(categoryBudgets);
    }

    /**
     * Converts a given {@code TotalBudget} into this class for Jackson use.
     */
    public JsonAdaptedTotalBudget(TotalBudget source) {
        currentBudget = Double.toString(source.getCurrentBudget());
        totalBudget = Double.toString(source.getTotalBudget());
        this.categoryBudgets.addAll(
                source.getCategoryBudgets()
                .stream()
                .map(JsonAdaptedCategoryBudget::new)
                .collect(Collectors.toList())
        );
    }

    /**
     * Converts this Jackson-friendly adapted record object into the model's {@code TotalBudget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record.
     */
    public TotalBudget toModelType() throws IllegalValueException {
        if (currentBudget == null || totalBudget == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        try {
            Double currBudget = Double.parseDouble(currentBudget);
            Double totBudget = Double.parseDouble(totalBudget);
            TotalBudget totalBudget = new TotalBudget(totBudget, currBudget);
            Iterator<JsonAdaptedCategoryBudget> itr = categoryBudgets.listIterator();
            HashSet<CategoryBudget> catBudgets = new HashSet<>();
            while (itr.hasNext()) {
                catBudgets.add(itr.next().toModelType());
            }
            totalBudget.setCategoryBudgets(catBudgets);
            return totalBudget;
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException(String.format(Amount.MESSAGE_CONSTRAINTS));
        }
    }

}
