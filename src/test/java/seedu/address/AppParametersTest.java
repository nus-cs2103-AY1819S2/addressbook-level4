package seedu.address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import javafx.application.Application;

public class AppParametersTest {

    private final ParametersStub parametersStub = new ParametersStub();
    private final AppParameters expected = new AppParameters();

    @Test
    public void parse_validConfigPath_success() {
        parametersStub.namedParameters.put("config", "config.json");
        expected.setConfigPath(Paths.get("config.json"));
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_nullConfigPath_success() {
        parametersStub.namedParameters.put("config", null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_invalidConfigPath_success() {
        parametersStub.namedParameters.put("config", "a\0");
        expected.setConfigPath(null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void equals() {
        parametersStub.namedParameters.put("config", "config.json");
        expected.setConfigPath(Paths.get("config.json"));

        // same object
        assertEquals(expected, expected);

        // different type
        assertNotEquals(5, expected);

        // different object
        assertNotEquals(expected, parametersStub);
    }

    @Test
    public void hashcode() {
        parametersStub.namedParameters.put("config", "config.json");
        AppParameters appParameters = AppParameters.parse(parametersStub);

        AppParameters copy = new AppParameters();
        copy.setConfigPath(Paths.get("config.json"));

        AppParameters different = new AppParameters();
        different.setConfigPath(Paths.get("random"));

        // same value
        assertEquals(appParameters.hashCode(), copy.hashCode());

        // different values
        assertNotEquals(appParameters.hashCode(), different.hashCode());
    }

    private static class ParametersStub extends Application.Parameters {
        private Map<String, String> namedParameters = new HashMap<>();

        @Override
        public List<String> getRaw() {
            throw new AssertionError("should not be called");
        }

        @Override
        public List<String> getUnnamed() {
            throw new AssertionError("should not be called");
        }

        @Override
        public Map<String, String> getNamed() {
            return Collections.unmodifiableMap(namedParameters);
        }
    }
}
