package seedu.equipment.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.equipment.commons.exceptions.IllegalValueException;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.WorkListId;
import seedu.equipment.model.equipment.Date;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;

/**
 * Jackson-friendly version of {@link WorkList}.
 */
class JsonAdaptedWorkList {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Equipment's %s field is missing!";

    private final String assignee;
    private final String workListId;
    private final String date;
    private final List<JsonAdaptedEquipment> equipments = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedWorkList} with the given WorkList details.
     */
    @JsonCreator
    public JsonAdaptedWorkList(@JsonProperty("assignee") String assignee, @JsonProperty("workListId") String workListId,
                                @JsonProperty("date") String date,
                                @JsonProperty("equipments") List<JsonAdaptedEquipment> equipments) {
        this.assignee = assignee;
        this.workListId = workListId;
        this.date = date;
        if (equipments != null) {
            this.equipments.addAll(equipments);
        }
    }

    /**
     * Converts a given {@code WorkList} into this class for Jackson use.
     */
    public JsonAdaptedWorkList(WorkList source) {
        assignee = source.getAssignee();
        workListId = source.getId().value;
        date = source.getDate();
        equipments.addAll(source.getEquipments().stream()
                .map(JsonAdaptedEquipment::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted WorkList object into the model's {@code WorkList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted WorkList.
     */
    public WorkList toModelType() throws IllegalValueException {
        final List<Equipment> equipmentInside = new ArrayList<>();
        for (JsonAdaptedEquipment equip : equipments) {
            equipmentInside.add(equip.toModelType());
        }

        if (assignee == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        final String modelAssignee = new String(assignee);

        if (workListId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    WorkListId.class.getSimpleName()));
        }
        if (!WorkListId.isValidWorkListId(workListId)) {
            throw new IllegalValueException(WorkListId.MESSAGE_CONSTRAINTS);
        }
        final WorkListId modelWorkListId = new WorkListId(workListId);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        final Set<Equipment> modelEquipments = new HashSet<>(equipmentInside);

        WorkList thisWorkList = new WorkList(modelDate.toString(), modelAssignee, modelWorkListId);

        int numOfEquipments = modelEquipments.size();
        Iterator<Equipment> ir = modelEquipments.iterator();
        for(int i = 0; i < numOfEquipments; i++) {
            thisWorkList.addEquipment(ir.next());
        }
        return thisWorkList;
    }

}
