package seedu.address.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void setLogLevel() {
        Config defaultConfig = new Config();
        defaultConfig.setLogLevel(Level.ALL);
        assertEquals(defaultConfig.getLogLevel(), Level.ALL);
    }

    @Test
    public void setUserPrefsFilePath() {
        Config defaultConfig = new Config();
        Path path = Paths.get(".");
        defaultConfig.setUserPrefsFilePath(path);
        assertEquals(defaultConfig.getUserPrefsFilePath(), path);
    }

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
        assertTrue(defaultConfig.equals(defaultConfig));
        assertFalse(defaultConfig.equals(5));
    }


}
