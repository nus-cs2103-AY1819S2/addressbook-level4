package seedu.address.testutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Quantity;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Medicine objects.
 */
public class MedicineBuilder {

    public static final String DEFAULT_NAME = "Paracetamol";
    public static final String DEFAULT_COMPANY = "Gilead Sciences";
    public static final String DEFAULT_QUANTITY = "0";
    public static final String DEFAULT_EXPIRY = "-";

    private Name name;
    private Company company;
    private Quantity quantity;
    private Expiry expiry;
    private Set<Tag> tags;
    private Map<BatchNumber, Batch> batches;

    public MedicineBuilder() {
        name = new Name(DEFAULT_NAME);
        company = new Company(DEFAULT_COMPANY);
        quantity = new Quantity(DEFAULT_QUANTITY);
        expiry = new Expiry(DEFAULT_EXPIRY);
        tags = new HashSet<>();
        batches = new HashMap<>();
    }

    /**
     * Initializes the MedicineBuilder with the data of {@code medicineToCopy}.
     */
    public MedicineBuilder(Medicine medicineToCopy) {
        name = medicineToCopy.getName();
        company = medicineToCopy.getCompany();
        quantity = medicineToCopy.getTotalQuantity();
        expiry = medicineToCopy.getNextExpiry();
        tags = new HashSet<>(medicineToCopy.getTags());
        batches = new HashMap<>(medicineToCopy.getBatches());
    }

    /**
     * Sets the {@code Name} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Adds {@code quantity} to the {@code Quantity} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withAddedQuantity(String quantity) {
        int sum = this.quantity.getNumericValue() + Integer.parseInt(quantity);
        this.quantity = new Quantity(Integer.toString(sum));
        return this;
    }

    /**
     * Sets the {@code Expiry} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withExpiry(String expiry) {
        this.expiry = new Expiry(expiry);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Medicine} that we are building.
     */
    public MedicineBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets {@code batches} to the {@code Medicine} that we are building.
     */
    public MedicineBuilder withBatches(Map<BatchNumber, Batch> batches) {
        this.batches = batches;
        return this;
    }

    /**
     * Parses the {@code batchDetails} into a {@code Map<BatchName, Batch>} and set it to the {@code Medicine} that we
     * are building.
     */
    public MedicineBuilder withBatches(String ... batchDetails) {
        this.batches = SampleDataUtil.getBatchSet(batchDetails);
        return this;
    }

    /**
     * Adds {@code batch} to the {@code Medicine} that we are building. Replaces existing value.
     */
    public MedicineBuilder withAddedBatch(Batch batch) {
        this.batches.put(batch.getBatchNumber(), batch);
        return this;
    }

    /**
     *  Sets {@code medicineToCopy}'s uneditable fields to the {@code Medicine} that we are building.
     */
    public MedicineBuilder withUneditableFields(Medicine medicineToCopy) {
        this.quantity = medicineToCopy.getTotalQuantity();
        this.expiry = medicineToCopy.getNextExpiry();
        this.batches = medicineToCopy.getBatches();
        return this;
    }

    public Medicine build() {
        return new Medicine(name, company, quantity, expiry, tags, batches);
    }

}
