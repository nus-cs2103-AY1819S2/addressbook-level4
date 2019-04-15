/* @@author Carrein */
package seedu.address;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.ui.InitPanel;
import seedu.address.ui.Notifier;

public class NotifierTest {
    private final InitPanel initPanel = new InitPanel();


    @Test
    public void valid_register() {
        Notifier.addPropertyChangeListener(initPanel);
        assertTrue(Notifier.getSupport().hasListeners("initPanel"));
    }

    @Test
    public void invalid_register() {
        assertTrue(Notifier.getSupport().hasListeners(null));
    }

    @Test
    public void valid_unregister() {
        Notifier.removePropertyChangeListener(initPanel);
        assertFalse(Notifier.getSupport().hasListeners("initPanel"));
    }
}
