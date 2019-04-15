package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Quantity;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Medicine}.
 */
class JsonAdaptedMedicine {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Medicine's %s field is missing!";

    private final String name;
    private final String company;
    private final String totalQuantity;
    private final String nextExpiry;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedBatch> batches = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMedicine} with the given medicine details.
     */
    @JsonCreator
    public JsonAdaptedMedicine(@JsonProperty("name") String name, @JsonProperty("company") String company,
            @JsonProperty("totalQuantity") String quantity, @JsonProperty("nextExpiry") String expiry,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
            @JsonProperty("batches") List<JsonAdaptedBatch> batches) {
        this.name = name;
        this.company = company;
        this.totalQuantity = quantity;
        this.nextExpiry = expiry;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (batches != null) {
            this.batches.addAll(batches);
        }
    }

    /**
     * Converts a given {@code Medicine} into this class for Jackson use.
     */
    public JsonAdaptedMedicine(Medicine source) {
        name = source.getName().fullName;
        company = source.getCompany().companyName;
        totalQuantity = source.getTotalQuantity().toString();
        nextExpiry = source.getNextExpiry().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        batches.addAll(source.getBatches().values()
                .stream()
                .map(JsonAdaptedBatch::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted medicine object into the model's {@code Medicine} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted medicine.
     */
    public Medicine toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName()));
        }
        if (!Company.isValidCompany(company)) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        final Company modelCompany = new Company(company);

        if (totalQuantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(totalQuantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(totalQuantity);

        if (nextExpiry == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Expiry.class.getSimpleName()));
        }
        if (!Expiry.isValidDate(nextExpiry)) {
            throw new IllegalValueException(Expiry.MESSAGE_CONSTRAINTS);
        }
        final Expiry modelExpiry = new Expiry(nextExpiry);

        final List<Tag> medicineTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            medicineTags.add(tag.toModelType());
        }

        final Map<BatchNumber, Batch> medicineBatches = new HashMap<>();
        for (JsonAdaptedBatch batch : batches) {
            Batch modelTypeBatch = batch.toModelType();
            medicineBatches.put(modelTypeBatch.getBatchNumber(), modelTypeBatch);
        }

        final Set<Tag> modelTags = new HashSet<>(medicineTags);

        return new Medicine(modelName, modelCompany, modelQuantity, modelExpiry, modelTags, medicineBatches);
    }
}
