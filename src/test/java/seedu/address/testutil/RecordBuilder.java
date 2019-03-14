package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.record.Amount;
import seedu.address.model.record.Date;
import seedu.address.model.record.Description;
import seedu.address.model.record.Name;
import seedu.address.model.record.Record;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Record objects.
 */
public class RecordBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_AMOUNT = "12";
    public static final String DEFAULT_DATE = "12/12/2019";
    public static final String DEFAULT_DESCRIPTION = "";

    private Name name;
    private Amount amount;
    private Date date;
    private Description description;
    private Set<Category> categories;

    public RecordBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_DATE);
        description = new Description(DEFAULT_DESCRIPTION);
        categories = new HashSet<>();
    }

    /**
     * Initializes the RecordBuilder with the data of {@code recordToCopy}.
     * @param recordToCopy
     */
    public RecordBuilder(Record recordToCopy) {
        name = recordToCopy.getName();
        amount = recordToCopy.getAmount();
        date = recordToCopy.getDate();
        description = recordToCopy.getDescription();
        categories = new HashSet<>(recordToCopy.getCategories());
    }

    /**
     * Sets the {@code Name} of the {@code Record} that we are building.
     */
    public RecordBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code Record} that we are building.
     */
    public RecordBuilder withCategories(String ... categories) {
        this.categories = SampleDataUtil.getCategorySet(categories);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Record} that we are building.
     */
    public RecordBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Record} that we are building.
     */
    public RecordBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Record} that we are building.
     */
    public RecordBuilder withDescription(Description description) {
        this.description = new Description(description.value);
        return this;
    }

    /**
     * Creates a {@code Record} based on the variables sepcificed.
     * @return Record with fields specified by Class
     */
    public Record build() {
        return new Record(name, amount, date, description, categories);
    }

}
