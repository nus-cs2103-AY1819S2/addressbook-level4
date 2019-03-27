package seedu.equipment.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.equipment.model.WorkList;
import seedu.equipment.model.equipment.Equipment;

/**
 * A utility class to help with building WorkList objects.
 */
public class WorkListBuilder {

    public static final String DEFAULT_DATE = "2019-02-12";
    public static final String DEFAULT_ASSIGNEE = "Mei Yen";

    private String date;
    private String assignee;
    private Set<Equipment> equipmentSet;

    public WorkListBuilder() {
        date = DEFAULT_DATE;
        assignee = DEFAULT_ASSIGNEE;
        equipmentSet = new HashSet<>();
    }

    /**
     * Initializes the WorkListBuilder with the data of {@code workListToCopy}.
     */
    public WorkListBuilder(WorkList workListToCopy) {
        date = workListToCopy.getDate();
        assignee = workListToCopy.getAssignee();
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

    public WorkList build() {
        return new WorkList(date, assignee);
    }

}
