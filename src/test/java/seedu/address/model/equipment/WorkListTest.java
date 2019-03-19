package seedu.address.model.equipment;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEquipments.ACHORVALECC;
import static seedu.address.testutil.TypicalEquipments.BOB;

import org.junit.Test;

public class WorkListTest {

    @Test
    public void isSameWorkList() {

        WorkList list1 = new WorkList("2018-01-01", "Alice");
        list1.addEquipment(ACHORVALECC);
        WorkList list2 = new WorkList("2019-01-01", "Bob");
        list2.addEquipment(BOB);
        // same id -> same worklist.
        assertTrue(list1.getId().getId() == list1.getId().getId());
    }
}
