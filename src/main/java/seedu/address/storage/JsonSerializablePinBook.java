package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PinBook;
import seedu.address.model.ReadOnlyPinBook;
import seedu.address.model.person.Person;

/**
 * An Immutable PinBook that is serializable to JSON format.
 */
@JsonRootName(value = "pinbook")
class JsonSerializablePinBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePinBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializablePinBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyPinBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePinBook}.
     */
    public JsonSerializablePinBook(ReadOnlyPinBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this pin book into the model's {@code PinBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PinBook toModelType() throws IllegalValueException {
        PinBook pinBook = new PinBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (pinBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            pinBook.addPerson(person);
        }
        return pinBook;
    }

}

