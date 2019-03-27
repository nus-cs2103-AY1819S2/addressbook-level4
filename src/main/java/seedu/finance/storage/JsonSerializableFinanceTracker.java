package seedu.finance.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.model.record.Record;

/**
 * An Immutable FinanceTracker that is serializable to JSON format.
 */
@JsonRootName(value = "financetracker")
class JsonSerializableFinanceTracker {

    public static final String MESSAGE_DUPLICATE_RECORD = "Records list contains duplicate record(s).";

    private final List<JsonAdaptedRecord> records = new ArrayList<>();

    private final JsonAdaptedBudget budget;

    /**
     * Constructs a {@code JsonSerializableFinanceTracker} with the given records.
     */
    @JsonCreator
    public JsonSerializableFinanceTracker(@JsonProperty("records") List<JsonAdaptedRecord> records,
                                          @JsonProperty("budget") JsonAdaptedBudget budget) {
        this.records.addAll(records);
        this.budget = budget;
    }

    /**
     * Converts a given {@code ReadOnlyFinanceTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFinanceTracker}.
     */
    public JsonSerializableFinanceTracker(ReadOnlyFinanceTracker source) {
        records.addAll(source.getRecordList().stream().map(JsonAdaptedRecord::new).collect(Collectors.toList()));
        budget = new JsonAdaptedBudget(source.getBudget().toString());
    }

    /**
     * Converts this finance tracker into the model's {@code FinanceTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FinanceTracker toModelType() throws IllegalValueException {
        FinanceTracker financeTracker = new FinanceTracker();
        for (JsonAdaptedRecord jsonAdaptedRecord : records) {
            Record record = jsonAdaptedRecord.toModelType();
            if (financeTracker.hasRecord(record)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECORD);
            }
            financeTracker.addRecord(record);
        }
        financeTracker.addBudget(budget.toModelType().valueProperty().get());

        return financeTracker;
    }

}
