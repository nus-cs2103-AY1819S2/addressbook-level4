package seedu.address.model.recmodule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.course.CourseReqType.BD;
import static seedu.address.model.course.CourseReqType.CORE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduleinfo.ModuleInfoTitle;
import seedu.address.testutil.RecModuleBuilder;

public class RecModuleTest {

    private ModuleInfoCode cs1010 = new ModuleInfoCode("CS1010");
    private ModuleInfoCode cs2103t = new ModuleInfoCode("CS2103T");
    /* dummy titles */
    private ModuleInfoTitle title = new ModuleInfoTitle("title");
    private ModuleInfoTitle title2 = new ModuleInfoTitle("title2");

    @Test
    public void constructor_nullCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecModule(null, title));
    }

    @Test
    public void equals() {
        RecModule rm1 = new RecModuleBuilder(cs1010, title).build();
        RecModule rm2 = new RecModuleBuilder(cs1010, title).withType(CORE).build();
        RecModule rm3 = new RecModuleBuilder(cs2103t, title).withType(CORE).build();
        RecModule rm4 = new RecModuleBuilder(cs2103t, title).withType(BD).build();
        RecModule rm5 = new RecModuleBuilder(cs1010, title2).build();

        assertTrue(rm1.equals(rm1)); // same object
        assertFalse(rm1.equals(rm2)); // type present in rm2 but not rm1
        assertFalse(rm2.equals(rm3)); // different code
        assertFalse(rm1.equals(rm5)); // different title
        assertFalse(rm3.equals(rm4)); // different type
    }
}
