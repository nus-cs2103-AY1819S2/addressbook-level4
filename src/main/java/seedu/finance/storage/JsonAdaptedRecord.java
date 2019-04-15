package seedu.finance.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.model.category.Category;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.Name;
import seedu.finance.model.record.Record;

/**
 * Jackson-friendly version of {@link Record}.
 */
class JsonAdaptedRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";

    private final String name;
    private final String amount;
    private final String date;
    private final String description;
    private final JsonAdaptedCategory tagged;

    /**
     * Constructs a {@code JsonAdaptedRecord} with the given record details.
     */
    @JsonCreator
    public JsonAdaptedRecord(@JsonProperty("name") String name, @JsonProperty("amount") String amount,
                             @JsonProperty("date") String date, @JsonProperty("description") String description,
                             @JsonProperty("tagged") JsonAdaptedCategory tagged) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.tagged = tagged;
    }

    /**
     * Converts a given {@code Record} into this class for Jackson use.
     */
    public JsonAdaptedRecord(Record source) {
        name = source.getName().fullName;
        amount = source.getAmount().toString();
        date = source.getDate().toString();
        description = source.getDescription().value;
        tagged = new JsonAdaptedCategory(source.getCategory());
    }

    /**
     * Converts this Jackson-friendly adapted record object into the model's {@code Record} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record.
     */
    public Record toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final Description modelDescription = new Description(description);

        if (!Category.isValidCategoryName(tagged.getCategoryName())) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        final Category modelTag = new Category(tagged.getCategoryName());

        return new Record(modelName, modelAmount, modelDate, modelDescription, modelTag);
    }

}
