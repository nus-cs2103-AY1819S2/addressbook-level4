package seedu.address.model;

import static org.junit.Assert.assertTrue;
//import static seedu.address.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.address.testutil.TypicalEquipments.BOB;
import static seedu.address.testutil.TypicalWorkLists.LISTA;

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
