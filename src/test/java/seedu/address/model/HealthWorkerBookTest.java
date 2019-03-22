package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BETTY;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalHealthWorkers.BETTY;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.testutil.Assert;
import seedu.address.testutil.HealthWorkerBuilder;

public class HealthWorkerBookTest {

    private final HealthWorkerBook healthWorkerBook = new HealthWorkerBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), healthWorkerBook.getHealthWorkerList());
    }

    @Test
    public void resetData() {
        // reset on null object
        Assert.assertThrows(NullPointerException.class, () -> healthWorkerBook.resetData(null));

        // replaced current list with another
        HealthWorkerBook newData = getTypicalHealthWorkerBook();
        healthWorkerBook.resetData(newData);
        assertEquals(healthWorkerBook, newData);

        // reset data with list containing duplicate persons
        HealthWorker editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY).withPhone(
                VALID_PHONE_BETTY)).build();
        List<HealthWorker> newHealthWorkers = Arrays.asList(ANDY, editedAndy);
        HealthWorkerBookStub newBook = new HealthWorkerBookStub(newHealthWorkers);
        Assert.assertThrows(DuplicatePersonException.class, () -> healthWorkerBook.resetData(newBook));
    }

    @Test
    public void addHealthWorker() {
        // null object
        Assert.assertThrows(NullPointerException.class, () -> healthWorkerBook.addHealthWorker(null));

        // duplicate HealthWorker
        healthWorkerBook.addHealthWorker(ANDY);
        Assert.assertThrows(DuplicatePersonException.class, () -> healthWorkerBook.addHealthWorker(ANDY));

        // same identity fields -> duplicate
        HealthWorker editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY).withPhone(
                VALID_PHONE_BETTY)).build();
        Assert.assertThrows(DuplicatePersonException.class, () -> healthWorkerBook.addHealthWorker(editedAndy));

        List<HealthWorker> newData = Arrays.asList(ANDY, BETTY);
        HealthWorkerBookStub newBook = new HealthWorkerBookStub(newData);
        healthWorkerBook.addHealthWorker(BETTY);
        assertEquals(newBook.getHealthWorkerList(), healthWorkerBook.getHealthWorkerList());
    }

    @Test
    public void removeHealthWorker() {
        // null object
        Assert.assertThrows(NullPointerException.class, () -> healthWorkerBook.removeHealthWorker(null));

        // non existent health worker
        Assert.assertThrows(PersonNotFoundException.class, () -> healthWorkerBook.removeHealthWorker(ANDY));

        healthWorkerBook.setHealthWorkers(Arrays.asList(ANDY, BETTY));
        HealthWorkerBookStub newData = new HealthWorkerBookStub(Arrays.asList(ANDY));
        healthWorkerBook.removeHealthWorker(BETTY);
        assertEquals(newData.getHealthWorkerList(), healthWorkerBook.getHealthWorkerList());
    }

    @Test
    public void setHealthWorker() {
        healthWorkerBook.addHealthWorker(ANDY);
        // null object
        Assert.assertThrows(NullPointerException.class, () -> healthWorkerBook.setHealthWorker(null, ANDY));
        Assert.assertThrows(NullPointerException.class, () -> healthWorkerBook.setHealthWorker(ANDY, null));

        // non existent object
        Assert.assertThrows(PersonNotFoundException.class, () -> healthWorkerBook.setHealthWorker(BETTY, ANDY));

        healthWorkerBook.setHealthWorker(ANDY, BETTY);
        assertEquals(Arrays.asList(BETTY), healthWorkerBook.getHealthWorkerList());
    }

    @Test
    public void hasHealthWorker() {
        // null object
        Assert.assertThrows(NullPointerException.class, () -> healthWorkerBook.hasHealthWorker(null));

        // health worker not in book
        assertFalse(healthWorkerBook.hasHealthWorker(ANDY));

        // health worker in book
        healthWorkerBook.addHealthWorker(ANDY);
        assertTrue(healthWorkerBook.hasHealthWorker(ANDY));

        // health worker with different fields, same identity -> return true
        HealthWorker editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY).withPhone(
                VALID_PHONE_BETTY)).build();
        assertTrue(healthWorkerBook.hasHealthWorker(editedAndy));
    }

    @Test
    public void getHealthWorkerList() {
        // modify immutable list
        Assert.assertThrows(UnsupportedOperationException.class, () -> healthWorkerBook.getHealthWorkerList()
                .remove(0));
    }

    // TODO: add tests for listeners.

    private static class HealthWorkerBookStub implements ReadOnlyHealthWorkerBook {
        private final ObservableList<HealthWorker> healthWorkers = FXCollections.observableArrayList();

        public HealthWorkerBookStub(Collection<HealthWorker> healthWorkers) {
            this.healthWorkers.setAll(healthWorkers);
        }

        @Override
        public ObservableList<HealthWorker> getHealthWorkerList() {
            return this.healthWorkers;
        }

        @Override
        public void addListener(InvalidationListener invalidationListener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener invalidationListener) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
