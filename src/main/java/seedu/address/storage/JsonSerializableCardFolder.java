package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CardFolder;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.person.Person;

/**
 * An Immutable CardFolder that is serializable to JSON format.
 */
@JsonRootName(value = "cardfolder")
class JsonSerializableCardFolder {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCardFolder} with the given persons.
     */
    @JsonCreator
    public JsonSerializableCardFolder(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyCardFolder} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCardFolder}.
     */
    public JsonSerializableCardFolder(ReadOnlyCardFolder source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this card folder into the model's {@code CardFolder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CardFolder toModelType() throws IllegalValueException {
        CardFolder cardFolder = new CardFolder();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (cardFolder.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            cardFolder.addPerson(person);
        }
        return cardFolder;
    }

}
