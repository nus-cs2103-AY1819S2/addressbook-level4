package seedu.address.testutil;

import static seedu.address.testutil.TypicalEquipments.ACHORVALECC;
import static seedu.address.testutil.TypicalEquipments.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EquipmentManager;
//import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.WorkList;

/**
 * A utility class containing a list of {@code WorkList} objects to be used in tests.
 */
public class TypicalWorkLists {

    public static final WorkList LISTA = new WorkList("2019-01-01", "Anchorvale CC");
    public static final WorkList LISTB = new WorkList("2018-01-01", "BOB");
    {
        LISTA.addEquipment(ACHORVALECC);
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
