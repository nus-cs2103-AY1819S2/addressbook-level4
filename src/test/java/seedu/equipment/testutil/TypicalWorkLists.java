package seedu.equipment.testutil;

import static seedu.equipment.logic.commands.CommandTestUtil.ASSIGNEE_DESC_LISTA;
import static seedu.equipment.logic.commands.CommandTestUtil.DATE_DESC_LISTA;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.BOB;
import static seedu.equipment.testutil.TypicalEquipments.TECKGHEECC;
import static seedu.equipment.testutil.TypicalEquipments.AYERRAJAHCC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.WorkList;

/**
 * A utility class containing a list of {@code WorkList} objects to be used in tests.
 */
public class TypicalWorkLists {

    public static final WorkList LISTA = new WorkList("01 January 2019", "Anchorvale CC");
    public static final WorkList LISTB = new WorkList("01 January 2019", "BOB");
    public static final WorkList LISTC = new WorkList(DATE_DESC_LISTA, ASSIGNEE_DESC_LISTA);
    public static final WorkList LISTD = new WorkList("09 May 2020", "Liu Xuan");
    public static final WorkList LISTE = new WorkList("10 May 2019", "Yiqun");
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
        return new ArrayList<>(Arrays.asList(LISTA, LISTB));
    }
}
