package seedu.equipment.testutil;

import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.equipment.model.EquipmentManager;
//import Equipment;
import seedu.equipment.model.WorkList;

/**
 * A utility class containing a list of {@code WorkList} objects to be used in tests.
 */
public class TypicalWorkLists {

    public static final WorkList LISTA = new WorkList("2019-01-01", "Anchorvale CC");
    public static final WorkList LISTB = new WorkList("2018-01-01", "BOB");
    {
        LISTA.addEquipment(ANCHORVALECC);
        LISTB.addEquipment(BOB);
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
