package seedu.equipment.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.equipment.commons.exceptions.IllegalValueException;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.ReadOnlyEquipmentManager;
import seedu.equipment.model.equipment.Equipment;

/**
 * An Immutable EquipmentManager that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableEquipmentManager {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate equipment(s).";

    private final List<JsonAdaptedEquipment> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEquipmentManager} with the given persons.
     */
    @JsonCreator


    public JsonSerializableEquipmentManager(@JsonProperty("persons") List<JsonAdaptedEquipment> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyEquipmentManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEquipmentManager}.
     */
    public JsonSerializableEquipmentManager(ReadOnlyEquipmentManager source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedEquipment::new).collect(Collectors.toList()));
    }

    /**
     * Converts this equipment book into the model's {@code EquipmentManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EquipmentManager toModelType() throws IllegalValueException {
        EquipmentManager equipmentManager = new EquipmentManager();
        for (JsonAdaptedEquipment jsonAdaptedEquipment : persons) {
            Equipment equipment = jsonAdaptedEquipment.toModelType();
            if (equipmentManager.hasPerson(equipment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            equipmentManager.addPerson(equipment);
        }
        return equipmentManager;
    }

}
