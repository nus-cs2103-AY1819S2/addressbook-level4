package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String id;
    private final String name;
    private final String phone;
    private final String gender;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */

    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("id") String id,
                             @JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("gender") String gender) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */

    public JsonAdaptedPerson(Person source) {
        id = String.valueOf(source.getId());
        name = source.getName().fullName;
        phone = source.getPhone().value;
        gender = source.getGender().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */

    public Person toModelType() throws IllegalValueException {
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PersonId.class.getSimpleName()));
        }
        if (!PersonId.isValidPersonId(id)) {
            throw new IllegalValueException(PersonId.MESSAGE_CONSTRAINTS);
        }
        final PersonId modelId = new PersonId(id);
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        return new Person(modelId, modelName, modelPhone, modelGender);
    }

    protected String getId() {
        return id;
    }

    protected String getName() {
        return name;
    }

    protected String getPhone() {
        return phone;
    }

    protected String getGender() {
        return gender;
    }
}
