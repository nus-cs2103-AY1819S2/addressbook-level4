package seedu.address.model.recmodule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.course.CourseReqType.BD;
import static seedu.address.model.course.CourseReqType.CORE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.testutil.RecModuleBuilder;

public class RecModuleTest {

    private RecModuleBuilder rmb = new RecModuleBuilder();

    @Test
    public void constructor_nullModuleInfo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecModule(null));
        assertThrows(NullPointerException.class, () -> new RecModule(null, null));
    }

    @Test
    public void equals() {
        RecModule rm1 = rmb.create("CS1010");
        RecModule rm2 = rmb.create("CS1010", CORE);
        RecModule rm3 = rmb.create("CS2103T", CORE);
        RecModule rm4 = rmb.create("CS2103T", BD);

        assertTrue(rm1.equals(rm1)); // same object
        assertFalse(rm1.equals(rm2)); // type present in rm2 but not rm1
        assertFalse(rm2.equals(rm3)); // different module
        assertFalse(rm3.equals(rm4)); // different type
    }
}
