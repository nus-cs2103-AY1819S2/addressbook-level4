package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Email;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Quantity;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Medicine}.
 */
class JsonAdaptedMedicine {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Medicine's %s field is missing!";

    private final String name;
    private final String quantity;
    private final String email;
    private final String company;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMedicine} with the given medicine details.
     */
    @JsonCreator
    public JsonAdaptedMedicine(@JsonProperty("name") String name, @JsonProperty("quantity") String quantity,
            @JsonProperty("email") String email, @JsonProperty("company") String company,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.quantity = quantity;
        this.email = email;
        this.company = company;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Medicine} into this class for Jackson use.
     */
    public JsonAdaptedMedicine(Medicine source) {
        name = source.getName().fullName;
        quantity = source.getQuantity().value;
        email = source.getEmail().value;
        company = source.getCompany().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted medicine object into the model's {@code Medicine} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted medicine.
     */
    public Medicine toModelType() throws IllegalValueException {
        final List<Tag> medicineTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            medicineTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName()));
        }
        if (!Company.isValidCompany(company)) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        final Company modelCompany = new Company(company);

        final Set<Tag> modelTags = new HashSet<>(medicineTags);
        return new Medicine(modelName, modelQuantity, modelEmail, modelCompany, modelTags);
    }

}
