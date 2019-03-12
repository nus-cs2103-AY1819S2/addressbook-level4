package seedu.address.model.equipment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEquipments.ALICE;
import static seedu.address.testutil.TypicalEquipments.BOB;

import org.junit.Test;

public class WorkListTest {

    @Test
    public void isSameWorkList() {

        private WorkList list1 = new WorkList();
        list1.addEquipment(ALICE);
        private WorkList list2 = new WorkList();
        list2.addEquipment(BOB);
        // same id -> same worklist.
        assertTrue(list1.getId().getId() == list1.getId().getId());
        assertFalse(list1.getId().getId() == list2.getId().getId());
    }
}
