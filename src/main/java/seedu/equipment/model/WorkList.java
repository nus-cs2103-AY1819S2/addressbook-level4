package seedu.equipment.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import seedu.equipment.commons.util.CollectionUtil;

import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.SerialNumber;

/**
 * Representing a WorkList containing the equipments users want to work on.
 */
public class WorkList {

    //Identity fields
    private final String date;
    private final String assignee;
    private final WorkListId id;

    //Data fields
    private final Set<Equipment> equipments;

    /**
     * Every field must be present and not null.
     */
    public WorkList(String date, String name, WorkListId id) {
        CollectionUtil.requireAllNonNull(date, name, id);
        this.date = date;
        this.assignee = name;
        this.equipments = new HashSet<>();
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getAssignee() {
        return assignee;
    }

    public WorkListId getId() {
        return this.id;
    }

    public Set<Equipment> getEquipments() {
        return Collections.unmodifiableSet(equipments);
    }

    /**
     * @return true if the equipment with {@code serialNumber} exist in the work list.
     */
    public boolean hasEquipment(SerialNumber serialNumber) {
        Iterator<Equipment> ir = equipments.iterator();
        int size = equipments.size();
        for (int i = 0; i < size; i++) {
            Equipment thisEquipment = ir.next();
            if (thisEquipment.getSerialNumber().serialNumber.equals(serialNumber.serialNumber)) {
                return true;
            }
        }
        return false;
    }

    //Add an equipment to the work list.
    public void addEquipment(Equipment e) {
        this.equipments.add(e);
    }

    //Delete an equipment from the work list.
    public void deleteEquipment(Equipment e) {
        this.equipments.remove(e);
    }

    //List all the equipments in the work list.
    //public Equipment[] listEquipment() {
    //    return (Equipment[]) this.equipments.toArray();
    //}

    /**
     * Returns true if both WorkLists have the same identity and data fields.
     * This defines a stronger notion of equality between two WorkLists.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof WorkList)) {
            return false;
        }

        WorkList otherWorkList = (WorkList) other;
        return otherWorkList.getAssignee().equals(getAssignee())
                && otherWorkList.getDate().equals(getDate())
                && otherWorkList.getId().equals(getId())
                && otherWorkList.getEquipments().equals(getEquipments());
    }

    /**
     * Returns true if both worklists have the same WorkListId.
     */
    public boolean isSameWorkList(WorkList otherWorkList) {
        if (otherWorkList == this) {
            return true;
        }

        return otherWorkList != null && otherWorkList.getId().equals(getId());
    }

    @Override
    public String toString() {
        return getId().value;
    }
}
