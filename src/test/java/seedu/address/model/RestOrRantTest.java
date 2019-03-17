package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;

public class RestOrRantTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RestOrRant restOrRant = new RestOrRant();

    @Test
    public void constructor() {
        assertNotNull(restOrRant.getMenu());
        assertNotNull(restOrRant.getOrders());
        assertNotNull(restOrRant.getTables());
        assertNotNull(restOrRant.getStatistics());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restOrRant.resetData(null, null, null, null);
    }

    @Test
    public void resetData_withValidReadOnlyRestOrRant_replacesData() {
        RestOrRant newData = getTypicalRestOrRant();
        restOrRant.resetData(newData.getOrders(), newData.getMenu(), newData.getTables(), newData.getStatistics());
        assertEquals(newData, restOrRant);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        restOrRant.addListener(listener);
        restOrRant.indicateModified();
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        restOrRant.addListener(listener);
        restOrRant.removeListener(listener);
        restOrRant.indicateModified();
        assertEquals(0, counter.get());
    }

}
