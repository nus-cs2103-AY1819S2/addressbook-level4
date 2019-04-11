package seedu.address.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class GuiSettingsTest {
    @Test
    public void equals_sameGuiSettings_equalIsTrue() {
        GuiSettings one = new GuiSettings();
        GuiSettings another = new GuiSettings();

        // compare same -> returns true
        assertEquals(one, one);

        // compare default GuiSettings -> returns true
        assertEquals(one, another);
    }

    @Test
    public void equals_differentObject_equalIsFalse() {
        GuiSettings one = new GuiSettings();
        Object another = new Object();

        // compare with Object -> returns false
        assertNotEquals(one, another);
        assertNotEquals(one, null);
    }
}
