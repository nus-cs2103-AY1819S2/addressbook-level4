package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ArchiveBook;
import seedu.address.model.ReadOnlyArchiveBook;
import seedu.address.model.person.Person;

/**
 * An Immutable ArchiveBook that is serializable to JSON format.
 */
@JsonRootName(value = "archivebook")
class JsonSerializableArchiveBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableArchiveBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableArchiveBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyArchiveBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableArchiveBook}.
     */
    public JsonSerializableArchiveBook(ReadOnlyArchiveBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this archive book into the model's {@code ArchiveBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ArchiveBook toModelType() throws IllegalValueException {
        ArchiveBook archiveBook = new ArchiveBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (archiveBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            archiveBook.addPerson(person);
        }
        return archiveBook;
    }

}

