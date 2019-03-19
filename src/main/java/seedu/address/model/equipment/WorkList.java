package seedu.address.model.equipment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    public WorkList(String date, String name) {
        requireAllNonNull(date, name);
        this.date = date;
        this.assignee = name;
        this.equipments = new HashSet<>();
        this.id = new WorkListId();
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

    //Add an equipment to the work list.
    public void addEquipment(Equipment e) {
        this.equipments.add(e);
    }

    //Delete an equipment from the work list.
    public void deleteEquipment(Equipment e) {
        this.equipments.remove(e);
    }

    //List all the equipments in the work list.
    public Equipment[] listEquipment() {
        return (Equipment[]) this.equipments.toArray();
    }

    /**
     * Returns true if both worklists have the same WorkListId.
     */
    public boolean isSameWorkList(WorkList otherWorkList) {
        if (otherWorkList == this) {
            return true;
        }

        return this.getId().getId() == otherWorkList.getId().getId();
    }
}
