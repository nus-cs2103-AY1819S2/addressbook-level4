package seedu.address.model.equipment;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEquipments.ACHORVALECC;
import static seedu.address.testutil.TypicalEquipments.BOB;
import static seedu.address.testutil.TypicalWorkLists.LISTA;

import org.junit.Test;

public class WorkListTest {

    @Test
    public void deleteEquipmentMustExist() {
        LISTA.addEquipment(BOB);
        assertTrue(LISTA.deleteEquipment(BOB));
    }

    @Test
    public void isSameWorkList() {

        // same id -> same worklist.
        assertTrue(LISTA.getId().getId() == LISTA.getId().getId());
    }
}
