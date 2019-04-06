package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.HealthWorkerBook;
import seedu.address.model.ReadOnlyHealthWorkerBook;
import seedu.address.model.person.healthworker.HealthWorker;

/**
 * An Immutable HealthWorkerBook that is serializable to JSON format.
 */
@JsonRootName(value = "healthWorkerBook")
class JsonSerializableHealthWorkerBook {

    public static final String MESSAGE_DUPLICATE_HEALTHWORKER =
            "HealthWorkers list contains duplicate healthWorker(s).";


    private final List<JsonAdaptedHealthWorker> healthWorkers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableHealthWorkerBook} with the given healthWorkers.
     */
    @JsonCreator
    public JsonSerializableHealthWorkerBook(@JsonProperty("healthWorkers")
                                                        List<JsonAdaptedHealthWorker> healthWorkers) {
        this.healthWorkers.addAll(healthWorkers);
    }

    /**
     * Converts a given {@code ReadOnlyHealthWorkerBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableHealthWorkerBook}.
     */
    public JsonSerializableHealthWorkerBook(ReadOnlyHealthWorkerBook source) {
        healthWorkers.addAll(source.getHealthWorkerList().stream()
                .map(JsonAdaptedHealthWorker::new).collect(Collectors.toList()));

    }

    /**
     * Converts this address book into the model's {@code HealthWorkerBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public HealthWorkerBook toModelType() throws IllegalValueException {
        HealthWorkerBook healthWorkerBook = new HealthWorkerBook();
        for (JsonAdaptedHealthWorker jsonAdaptedHealthWorker : healthWorkers) {
            HealthWorker healthWorker = jsonAdaptedHealthWorker.toModelType();
            if (healthWorkerBook.hasHealthWorker(healthWorker)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_HEALTHWORKER);
            }
            healthWorkerBook.addHealthWorker(healthWorker);
        }
        return healthWorkerBook;
    }



}
