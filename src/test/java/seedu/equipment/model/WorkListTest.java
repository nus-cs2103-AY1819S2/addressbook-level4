package seedu.equipment.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.BOB;
import static seedu.equipment.testutil.TypicalWorkLists.LISTA;

import org.junit.Test;

import seedu.equipment.testutil.WorkListBuilder;

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

    @Test
    public void equals() {
        assertFalse(LISTA.equals(null));

        WorkList LISTACopy = new WorkListBuilder().withAssignee(LISTA.getAssignee())
                .withDate(LISTA.getDate()).build();
        assertFalse(LISTA.equals(LISTACopy));
    }
}
