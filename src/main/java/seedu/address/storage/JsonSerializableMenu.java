package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.RestOrRant;
import seedu.address.model.person.Person;

/**
 * An Immutable RestOrRant that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableMenu {
/*
    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    private final List<JsonAdaptedPerson> persons = new ArrayList<>(); */
    /**
     * Constructs a {@code JsonSerializableRestOrRant} with the given persons.
     */
/*    @JsonCreator
    public JsonSerializableMenu(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    } */

    /**
     * Converts a given {@code ReadOnlyRestOrRant} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRestOrRant}.
     */
 /*   public JsonSerializableMenu(ReadOnlyRestOrRant source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    } */

    /**
     * Converts this address book into the model's {@code RestOrRant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
/*    public RestOrRant toModelType() throws IllegalValueException {
        RestOrRant restOrRant = new RestOrRant();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (restOrRant.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            restOrRant.addPerson(person);
        }
        return restOrRant;
    } */

}
