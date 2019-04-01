package seedu.address.model.person.healthworker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BETTY;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalHealthWorkers.BETTY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.HealthWorkerBuilder;

/**
 * @author Lookaz
 */
public class UniqueHealthWorkerListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueHealthWorkerList uniqueHealthWorkerList = new UniqueHealthWorkerList();

    @Test
    public void contains() {
        // null health worker
        Assert.assertThrows(NullPointerException.class, () ->
                uniqueHealthWorkerList.contains(null));

        // health worker not in list -> returns false
        assertFalse(uniqueHealthWorkerList.contains(ANDY));

        // health worker in list -> returns true
        uniqueHealthWorkerList.add(ANDY);
        assertTrue(uniqueHealthWorkerList.contains(ANDY));

        // Identity fields same -> returns true
        HealthWorker editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY)
                .withPhone(VALID_PHONE_BETTY)).build();
        assertTrue(uniqueHealthWorkerList.contains(editedAndy));
    }

    @Test
    public void add() {
        // null health worker
        thrown.expect(NullPointerException.class);
        uniqueHealthWorkerList.add(null);

        // duplicate health worker
        uniqueHealthWorkerList.add(ANDY);
        Assert.assertThrows(DuplicatePersonException.class, () ->
                uniqueHealthWorkerList.add(ANDY));

        // same identity fields -> duplicate
        HealthWorker editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY)
                .withPhone(VALID_PHONE_BETTY)).build();
        Assert.assertThrows(DuplicatePersonException.class, () ->
                uniqueHealthWorkerList.add(editedAndy));
    }

    @Test
    public void setHealthWorker() {
        // null health worker
        Assert.assertThrows(NullPointerException.class, () ->
                uniqueHealthWorkerList.setHealthWorker(null, ANDY));
        Assert.assertThrows(NullPointerException.class, () ->
                uniqueHealthWorkerList.setHealthWorker(ANDY, null));

        // target health worker not in list
        Assert.assertThrows(PersonNotFoundException.class, () ->
                uniqueHealthWorkerList.setHealthWorker(ANDY, ANDY));

        // edit same person -> same list
        uniqueHealthWorkerList.add(ANDY);
        uniqueHealthWorkerList.setHealthWorker(ANDY, ANDY);
        UniqueHealthWorkerList expectedUniqueHealthWorkerList = new UniqueHealthWorkerList();
        expectedUniqueHealthWorkerList.add(ANDY);
        assertEquals(expectedUniqueHealthWorkerList, uniqueHealthWorkerList);

        // edited health worker same identity -> same list
        HealthWorker editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY)
                .withPhone(VALID_PHONE_BETTY)).build();
        uniqueHealthWorkerList.setHealthWorker(ANDY, editedAndy);
        expectedUniqueHealthWorkerList = new UniqueHealthWorkerList();
        expectedUniqueHealthWorkerList.add(editedAndy);
        assertEquals(expectedUniqueHealthWorkerList, uniqueHealthWorkerList);

        // edited health worker to different identity
        uniqueHealthWorkerList.setHealthWorker(editedAndy, BETTY);
        expectedUniqueHealthWorkerList = new UniqueHealthWorkerList();
        expectedUniqueHealthWorkerList.add(BETTY);
        assertEquals(expectedUniqueHealthWorkerList, uniqueHealthWorkerList);

        // adding duplicate health workers
        uniqueHealthWorkerList.add(ANDY);
        Assert.assertThrows(DuplicatePersonException.class, () ->
                uniqueHealthWorkerList.setHealthWorker(ANDY, BETTY));
    }

    @Test
    public void remove() {
        // remove null health worker
        Assert.assertThrows(NullPointerException.class, () ->
                uniqueHealthWorkerList.remove(null));

        // remove health worker that is not in list
        Assert.assertThrows(PersonNotFoundException.class, () ->
                uniqueHealthWorkerList.remove(ANDY));

        // remove existing heath worker
        uniqueHealthWorkerList.add(ANDY);
        uniqueHealthWorkerList.remove(ANDY);
        UniqueHealthWorkerList expectedUniqueHealthWorkerList = new UniqueHealthWorkerList();
        assertEquals(expectedUniqueHealthWorkerList, uniqueHealthWorkerList);

        // different fields -> object not in list
        HealthWorker editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY)
                .withPhone(VALID_PHONE_BETTY)).build();
        Assert.assertThrows(PersonNotFoundException.class, () ->
                uniqueHealthWorkerList.remove(editedAndy));
    }

    @Test
    public void setPersons() {
        // replace with null
        Assert.assertThrows(NullPointerException.class, () ->
                uniqueHealthWorkerList.setHealthWorkers((UniqueHealthWorkerList) null));
        Assert.assertThrows(NullPointerException.class, () ->
                uniqueHealthWorkerList.setHealthWorkers((List<HealthWorker>) null));

        // replace current list with given
        uniqueHealthWorkerList.add(ANDY);
        UniqueHealthWorkerList expectedUniqueHealthWorkerList = new UniqueHealthWorkerList();
        expectedUniqueHealthWorkerList.add(BETTY);
        uniqueHealthWorkerList.setHealthWorkers(expectedUniqueHealthWorkerList);
        assertEquals(expectedUniqueHealthWorkerList, uniqueHealthWorkerList);

        uniqueHealthWorkerList.setHealthWorkers(Collections.singletonList(BETTY));
        assertEquals(expectedUniqueHealthWorkerList, uniqueHealthWorkerList);

        // replacing with list with duplicates
        List<HealthWorker> listWithDuplicatePersons = Arrays.asList(ANDY, ANDY);
        Assert.assertThrows(DuplicatePersonException.class, () ->
                uniqueHealthWorkerList.setHealthWorkers(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList() {
        // attempt to modify list
        Assert.assertThrows(UnsupportedOperationException.class, () ->
                uniqueHealthWorkerList.asUnmodifiableObservableList().remove(0));
    }
}
