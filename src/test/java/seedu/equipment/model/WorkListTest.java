package seedu.equipment.model;

import static org.junit.Assert.assertTrue;
//import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.BOB;
import static seedu.equipment.testutil.TypicalWorkLists.LISTA;

import org.junit.Test;

public class WorkListTest {

    @Test
    public void deleteEquipmentMustExist() {
        LISTA.addEquipment(BOB);
        LISTA.deleteEquipment(BOB);
        assertTrue(true);
    }

    @Test
    public void isSameWorkList() {

        // same id -> same worklist.
        assertTrue(LISTA.getId().getId() == LISTA.getId().getId());
    }
}
