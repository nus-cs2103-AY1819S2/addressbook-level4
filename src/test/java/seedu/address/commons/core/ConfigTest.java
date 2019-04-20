package seedu.address.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.logging.Level;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "Current log level : INFO\n"
                + "Preference file Location : preferences.json";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);

        // same object -> returns true
        assertTrue(defaultConfig.equals(defaultConfig));

        // comparing with an object which is not a Config -> return false
        assertNotEquals(defaultConfig, new Object());
        assertNotEquals(defaultConfig, 0);
        assertNotEquals(defaultConfig, null);

        // comparing with another Config which is different -> return false
        Config anotherConfig = new Config();
        anotherConfig.setLogLevel(Level.FINE);
        assertNotEquals(defaultConfig, anotherConfig);
    }
}
