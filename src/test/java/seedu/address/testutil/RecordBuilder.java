package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.record.Address;
import seedu.address.model.record.Amount;
import seedu.address.model.record.Date;
import seedu.address.model.record.Description;
import seedu.address.model.record.Email;
import seedu.address.model.record.Name;
import seedu.address.model.record.Phone;
import seedu.address.model.record.Record;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Record objects.
 */
public class RecordBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_AMOUNT = "12";
    public static final String DEFAULT_DATE = "12/12/2019";
    public static final String DEFAULT_DESCRIPTION = "";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Amount amount;
    private Date date;
    private Description description;
    private Set<Tag> tags;

    public RecordBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        amount = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_DATE);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the RecordBuilder with the data of {@code recordToCopy}.
     * @param recordToCopy
     */
    public RecordBuilder(Record recordToCopy) {
        name = recordToCopy.getName();
        phone = recordToCopy.getPhone();
        email = recordToCopy.getEmail();
        address = recordToCopy.getAddress();
        amount = recordToCopy.getAmount();
        date = recordToCopy.getDate();
        description = recordToCopy.getDescription();
        tags = new HashSet<>(recordToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Record} that we are building.
     */
    public RecordBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Record} that we are building.
     */
    public RecordBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Record} that we are building.
     */
    public RecordBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Record} that we are building.
     */
    public RecordBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Record} that we are building.
     */
    public RecordBuilder withEmail(String email) {
        this.email = new Email(email);
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
        return new Record(name, phone, email, address, amount, date,
                description, tags);
    }

}
