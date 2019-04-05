package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Year;
import seedu.address.model.tag.Specialisation;


/**
 * Jackson-friendly version of {@link Doctor}.
 */
class JsonAdaptedDoctor {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Doctor's %s field is missing!";

    private final String name;
    private final String phone;
    private final String gender;
    private final String year;
    private final List<JsonAdaptedSpecialisation> specs = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDoctor} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedDoctor(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("gender") String gender, @JsonProperty("year") String year,
                             @JsonProperty("specs") List<JsonAdaptedSpecialisation> specs) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.year = year;
        if (specs != null) {
            this.specs.addAll(specs);
        }
    }

    /**
     * Converts a given {@code Doctor} into this class for Jackson use.
     */
    public JsonAdaptedDoctor(Doctor source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        gender = source.getGender().value;
        year = source.getYear().value;
        specs.addAll(source.getSpecs().stream()
                .map(JsonAdaptedSpecialisation::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Doctor} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted doctor.
     */
    public Doctor toModelType() throws IllegalValueException {
        final List<Specialisation> doctorSpecs = new ArrayList<>();
        for (JsonAdaptedSpecialisation spec : specs) {
            doctorSpecs.add(spec.toModelType());
        }

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

        if (year == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Year.class.getSimpleName()));
        }
        if (!Year.isValidYear(year)) {
            throw new IllegalValueException(Year.MESSAGE_CONSTRAINTS);
        }
        final Year modelYear = new Year(year);

        final Set<Specialisation> modelSpecs = new HashSet<>(doctorSpecs);
        return new Doctor(modelName, modelPhone, modelGender, modelYear, modelSpecs);
    }

}
