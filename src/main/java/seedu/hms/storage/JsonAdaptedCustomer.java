package seedu.hms.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.hms.commons.exceptions.IllegalValueException;
import seedu.hms.model.customer.Address;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.DateOfBirth;
import seedu.hms.model.customer.Email;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.customer.Name;
import seedu.hms.model.customer.Phone;
import seedu.hms.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Customer}.
 */
class JsonAdaptedCustomer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Customer's %s field is missing!";

    private final String name;
    private final String phone;
    private final String dob;
    private final String email;
    private final String idnum;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedCustomer(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                               @JsonProperty("dob") String dob,
                               @JsonProperty("email") String email, @JsonProperty("idnum") String idnum,
                               @JsonProperty("hms") String address,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.dob = dob;
        this.email = email;
        this.idnum = idnum;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedCustomer(Customer source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        dob = source.getDateOfBirth().value;
        email = source.getEmail().value;
        idnum = source.getIdNum().value;
        address = source.getAddress().value;
        tagged.addAll(source.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted customer object into the model's {@code Customer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted customer.
     */
    public Customer toModelType() throws IllegalValueException {
        final List<Tag> customerTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            customerTags.add(tag.toModelType());
        }

        final Name modelName = getName();

        final Phone modelPhone = getPhone();

        final DateOfBirth modelDob = getDateOfBirth();

        final Email modelEmail = getEmail();

        final IdentificationNo modelIdNum = getIdNum();

        final Address modelAddress = getAddress();

        final Set<Tag> modelTags = new HashSet<>(customerTags);
        return new Customer(modelName, modelPhone, modelDob, modelEmail, modelIdNum, modelAddress, modelTags);
    }

    private Address getAddress() throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(address);
    }

    private Email getEmail() throws IllegalValueException {
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(email);
    }

    private DateOfBirth getDateOfBirth() throws IllegalValueException {
        if (dob == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                DateOfBirth.class.getSimpleName()));
        }
        if (!DateOfBirth.isValidDob(dob)) {
            throw new IllegalValueException(DateOfBirth.MESSAGE_CONSTRAINTS);
        }
        return new DateOfBirth(dob);
    }

    private Phone getPhone() throws IllegalValueException {
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(phone);
    }

    private IdentificationNo getIdNum() throws IllegalValueException {
        if (idnum == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                IdentificationNo.class.getSimpleName()));
        }
        if (!IdentificationNo.isValidIdNum(idnum)) {
            throw new IllegalValueException(IdentificationNo.MESSAGE_CONSTRAINTS);
        }
        return new IdentificationNo(idnum);
    }

    private Name getName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

}
