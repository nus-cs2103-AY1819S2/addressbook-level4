package seedu.equipment.testutil;

import static seedu.equipment.logic.commands.CommandTestUtil.ASSIGNEE_DESC_LISTA;
import static seedu.equipment.logic.commands.CommandTestUtil.DATE_DESC_LISTA;
import static seedu.equipment.logic.commands.CommandTestUtil.ID_DESC_LISTA;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ASSIGNEE_LISTA;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_DATE_LISTA;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ID_LISTA;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.AYERRAJAHCC;
import static seedu.equipment.testutil.TypicalEquipments.BOB;
import static seedu.equipment.testutil.TypicalEquipments.TECKGHEECC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.WorkListId;

/**
 * A utility class containing a list of {@code WorkList} objects to be used in tests.
 */
public class TypicalWorkLists {

    public static final WorkList LISTA = new WorkListBuilder().withDate("07 June 2020").withAssignee("Rachel")
            .withId("3").build();
    //public static final WorkList LISTB = new WorkListBuilder().withDate("08 July 2030").withAssignee("Yiqun")
    //        .withId("4").build();
    public static final WorkList LISTB = new WorkList("08 July 2030", "Yiqun", new WorkListId("4"));
    public static final WorkList LISTC = new WorkListBuilder().withDate(VALID_DATE_LISTA)
            .withAssignee(VALID_ASSIGNEE_LISTA).withId(VALID_ID_LISTA).build();
    public static final WorkList LISTD = new WorkListBuilder().withDate("09 August 2033").withAssignee("Liu Xuan")
            .withId("10").build();
    public static final WorkList LISTE = new WorkListBuilder().withDate("20 September 2022").withAssignee("Bob")
            .withId("11").build();
    {
        LISTA.addEquipment(ANCHORVALECC);
        LISTB.addEquipment(BOB);
        LISTD.addEquipment(TECKGHEECC);
        LISTE.addEquipment(AYERRAJAHCC);
    }

    /**
     * Returns an {@code EquipmentManager} with all the typical persons.
     */
    public static EquipmentManager getTypicalEquipmentManager() {
        EquipmentManager ab = new EquipmentManager();
        for (WorkList worklist : getTypicalWorkLists()) {
            ab.addWorkList(worklist);
        }
        return ab;
    }

    public static List<WorkList> getTypicalWorkLists() {
        return new ArrayList<>(Arrays.asList(LISTA,
                LISTB, LISTD, LISTE));
    }
}
