package seedu.equipment.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.equipment.model.WorkList;
import seedu.equipment.model.WorkListId;
import seedu.equipment.model.equipment.Equipment;

/**
 * A utility class to help with building WorkList objects.
 */
public class WorkListBuilder {

    public static final String DEFAULT_DATE = "12-02-2019";
    public static final String DEFAULT_ASSIGNEE = "Mei Yen";
    public static final String DEFAULT_ID = "201";

    private String date;
    private String assignee;
    private WorkListId id;
    private Set<Equipment> equipmentSet;

    public WorkListBuilder() {
        date = DEFAULT_DATE;
        assignee = DEFAULT_ASSIGNEE;
        id = new WorkListId(DEFAULT_ID);
        equipmentSet = new HashSet<>();
    }

    /**
     * Initializes the WorkListBuilder with the data of {@code workListToCopy}.
     */
    public WorkListBuilder(WorkList workListToCopy) {
        date = workListToCopy.getDate();
        assignee = workListToCopy.getAssignee();
        id = workListToCopy.getId();
        equipmentSet = workListToCopy.getEquipments();
    }

    /**
     * Sets the {@code Date} of the {@code WorkList} that we are building.
     */
    public WorkListBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Parses the {@code assignee} of the {@code WorkList} that we are building.
     */
    public WorkListBuilder withAssignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    /**
     * Parses the {@code WorkListId} of the {@code WorkList} that we are building.
     */
    public WorkListBuilder withId(String id) {
        this.id = new WorkListId(id);
        return this;
    }

    public WorkList build() {
        return new WorkList(date, assignee, id);
    }

}
