package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.nextofkin.NextOfKin;
import seedu.address.model.nextofkin.NextOfKinRelation;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Jackson-friendly version of {@link NextOfKin}
 */
public class JsonAdaptedNextOfKin {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "NextOfKin's %s field is missing!";

    private final String kinName;
    private final String kinRelation;
    private final String kinEmail;
    private final String kinPhone;
    private final String kinAddress;

    /**
     * Constructs a {@code JsonAdaptedNextOfKin} with the given details.
     */
    @JsonCreator
    public JsonAdaptedNextOfKin(@JsonProperty("kinName") String kinName,
                                @JsonProperty("kinRelation") String kinRelation,
                                @JsonProperty("kinEmail") String kinEmail,
                                @JsonProperty("kinPhone") String kinPhone,
                                @JsonProperty("kinAddress") String kinAddress) {
        this.kinName = kinName;
        this.kinRelation = kinRelation;
        this.kinPhone = kinPhone;
        this.kinAddress = kinAddress;
        this.kinEmail = kinEmail;
    }

    /**
     * Converts a given {@code NextOfKin} into this class for Jackson use.
     */
    public JsonAdaptedNextOfKin(NextOfKin source) {
        kinName = source.getName().toString();
        kinRelation = source.getKinRelation().getRelationship();
        kinPhone = source.getPhone().toString();
        kinAddress = source.getAddress().toString();
        kinEmail = source.getEmail().toString();
    }

    /**
     * Converts this Jackson-Friendly adapted NextOfKin object into the model's {@code NextOfKin} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted nextOfKin.
     */
    public NextOfKin toModelType() throws IllegalValueException {
        if (kinName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(kinName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelKinName = new Name(kinName);

        if (kinRelation == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                NextOfKinRelation.class.getSimpleName()));
        }
        if (!NextOfKinRelation.isValidNextOfKinRelation(kinRelation)) {
            throw new IllegalValueException(NextOfKinRelation.MESSAGE_CONSTRAINTS);
        }
        final NextOfKinRelation modelKinRelation = new NextOfKinRelation(kinRelation);

        if (kinPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(kinPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelKinPhone = new Phone(kinPhone);

        if (kinAddress == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(kinAddress)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelKinAddress = new Address(kinAddress);

        if (kinEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(kinAddress)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelKinEmail = new Email(kinEmail);

        return new NextOfKin(modelKinName, modelKinPhone, modelKinEmail, modelKinAddress, null, modelKinRelation);
    }
}
