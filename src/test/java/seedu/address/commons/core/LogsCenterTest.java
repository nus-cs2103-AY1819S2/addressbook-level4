package seedu.address.commons.core;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import org.junit.Test;

public class LogsCenterTest {
    @Test
    public void getLogger_nullParameter_returnLoggerWithEmptyName() {
        Logger logger = LogsCenter.getLogger((Class<Object>) null);
        // pass in null class -> should return logger with empty name
        assertEquals(Logger.getLogger(""), logger);
    }
}
