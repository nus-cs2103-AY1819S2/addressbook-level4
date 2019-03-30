package seedu.address.model.moduletaken;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.testutil.Assert;

public class ModuleInfoCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ModuleInfoCode(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ModuleInfoCode(invalidName));
    }

    @Test
    public void isValidModuleInfoCode() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> ModuleInfoCode.isValidModuleInfoCode(null));

        // invalid name
        assertFalse(ModuleInfoCode.isValidModuleInfoCode("")); // empty string
        assertFalse(ModuleInfoCode.isValidModuleInfoCode(" ")); // spaces only
        assertFalse(ModuleInfoCode.isValidModuleInfoCode("^")); // only non-alphanumeric characters
        assertFalse(ModuleInfoCode.isValidModuleInfoCode("MA152")); // 3 numbers only
        assertFalse(ModuleInfoCode.isValidModuleInfoCode("CS1010SJXT")); //4 suffix

        // valid name
        assertTrue(ModuleInfoCode.isValidModuleInfoCode("CS2101"));
        assertTrue(ModuleInfoCode.isValidModuleInfoCode("CS2103T")); // numbers only
        assertTrue(ModuleInfoCode.isValidModuleInfoCode("DSA1234PPP")); // alphanumeric characters
        assertTrue(ModuleInfoCode.isValidModuleInfoCode("ABC5819T")); // with capital letters
        assertTrue(ModuleInfoCode.isValidModuleInfoCode("CS1010JKS")); //3 suffix
    }
}
