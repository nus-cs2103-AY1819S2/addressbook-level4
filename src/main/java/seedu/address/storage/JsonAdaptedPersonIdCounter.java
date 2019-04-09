/* @@author wayneswq */
package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.PersonIdCounter;

/**
 * Jackson-friendly version of {@link PersonIdCounter}.
 */
public class JsonAdaptedPersonIdCounter {

    private final int id;

    /**
     * Constructs a {@code JsonAdaptedPersonIdCounter} with the given {@code id}.
     */
    @JsonCreator
    protected JsonAdaptedPersonIdCounter(int id) {
        this.id = id;
    }

    /**
     * Converts a given {@code PersonIdCounter} into this class for Jackson use.
     */
    protected JsonAdaptedPersonIdCounter(PersonIdCounter source) {
        id = source.getCurrentMaxId();
    }

    @JsonValue
    public int getId() {
        return id;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code PersonIdCounter} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted PersonIdCounter.
     */
    public PersonIdCounter toModelType() throws IllegalValueException {
        PersonIdCounter.getInstance().setCurrentMaxId(id);
        return PersonIdCounter.getInstance();
    }

}
