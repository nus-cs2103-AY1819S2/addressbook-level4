package seedu.address.storage;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.healthworker.Organization;
import seedu.address.model.tag.Skills;
import seedu.address.model.tag.Specialisation;

/**
 * Jackson-friendly version of {@link HealthWorker}.
 */
class JsonAdaptedHealthWorker extends JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String organization;
    private final String skills;

    /**
     * Constructs a {@code JsonAdaptedHealthWorker} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedHealthWorker(@JsonProperty("name") String name,
                                   @JsonProperty("phone") String phone,
                                   @JsonProperty("nric") String nric,
                                   @JsonProperty("organization") String organisation,
                                   @JsonProperty("skills") String skills) {
        super(name, phone, nric);
        this.organization = organisation;
        this.skills = skills;
    }

    /**
     * Converts a given {@code HealthWorker} into this class for Jackson use.
     */
    public JsonAdaptedHealthWorker(HealthWorker source) {
        super(source);
        this.organization = source.getOrganization().getOrgName();
        this.skills = source.getSkills().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code HealthWorker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted HealthWorker.
     */
    @Override
    public HealthWorker toModelType() throws IllegalValueException {

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

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        if (organization == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Organization.class.getSimpleName()));
        }

        if (!Organization.isValidOrgName(organization)) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Organization.class.getSimpleName()));
        }

        final Organization modelOrganisation = new Organization(organization);

        if (skills == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Skills.class.getSimpleName()));
        }

        Set<Specialisation> set = new HashSet<>();
        String[] skillsArr = this.skills.split(" ");
        for (String skill : skillsArr) {
            Specialisation spec = Specialisation.parseString(skill);
            set.add(spec);
        }
        final Skills modelSkills = new Skills(set);

        return new HealthWorker(modelName, modelNric, modelPhone, modelOrganisation, modelSkills);

    }

}
