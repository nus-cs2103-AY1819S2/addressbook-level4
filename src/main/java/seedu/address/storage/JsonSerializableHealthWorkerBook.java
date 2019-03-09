package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

//import seedu.address.model.person.HealthWorker;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableHealthWorkerBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private static HashMap<String, Person> personHashMap = new HashMap<>();
    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    //private final List<JsonAdaptedHealthWorker> healthWorkers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableHealthWorkerBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableHealthWorkerBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        //healthWorkers.addAll(source.getHealthWorkerList().stream()
        //.map(JsonAdaptedHealthWorker::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
            personHashMap.put(person.getNric().toString(), person);
        }
        /*for (JsonAdaptedHealthWorker jsonAdaptedHealthWorker: healthWorkers) {
            HealthWorker healthWorker = jsonAdaptedHealthWorker.toModelType();
            if (addressBook.hasHealthWorker(healthWorker)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
        }*/
        return addressBook;
    }

    public static Person obtainPersonFromHashmap(String nric) {

        return personHashMap.get(nric);

    }

}
