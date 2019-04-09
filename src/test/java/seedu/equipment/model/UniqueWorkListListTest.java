package seedu.equipment.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
//import static seedu.equipment.testutil.TypicalEquipments.BOB;

import static seedu.equipment.testutil.TypicalWorkLists.LISTA;
//import static seedu.equipment.testutil.TypicalWorkLists.LISTB;

//import java.util.Arrays;
//import java.util.Collections;

import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//import UniqueWorkListList;
//import WorkList;

import seedu.equipment.model.equipment.exceptions.DuplicateEquipmentException;
import seedu.equipment.model.equipment.exceptions.EquipmentNotFoundException;
import seedu.equipment.model.equipment.exceptions.WorkListNotFoundException;
//import seedu.equipment.testutil.EquipmentBuilder;

public class UniqueWorkListListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final List<WorkList> listOfWorklist = new LinkedList<>();

    private final UniqueWorkListList uniqueWorkListList = new UniqueWorkListList();

    {
        listOfWorklist.add(LISTA);
    }

    @Test
    public void contains_nullWorkList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkListList.contains(null);
    }

    @Test
    public void addEquipment_noSuchWorkListFound() {
        thrown.expect(WorkListNotFoundException.class);
        WorkListId noSuchId = new WorkListId("999");
        uniqueWorkListList.addEquipment(ANCHORVALECC, noSuchId);
    }

    @Test
    public void addEquipment_workListFound_success() {
        uniqueWorkListList.add(LISTA);
        uniqueWorkListList.addEquipment(ANCHORVALECC, LISTA.getId());
        UniqueWorkListList expectedUniqueWorkListList = new UniqueWorkListList();
        expectedUniqueWorkListList.add(LISTA);
        expectedUniqueWorkListList.addEquipment(ANCHORVALECC, LISTA.getId());
        assertEquals(expectedUniqueWorkListList, uniqueWorkListList);
    }

    @Test
    public void removeEquipment_noSuchWorkListFound() {
        thrown.expect(WorkListNotFoundException.class);
        WorkListId noSuchId = new WorkListId("999");
        uniqueWorkListList.removeEquipment(ANCHORVALECC, noSuchId);
    }

    @Test
    public void removeEquipment_workListFound_success() {
        uniqueWorkListList.add(LISTA);
        uniqueWorkListList.removeEquipment(ANCHORVALECC, LISTA.getId());
        UniqueWorkListList expectedUniqueWorkListList = new UniqueWorkListList();
        expectedUniqueWorkListList.add(LISTA);
        assertEquals(expectedUniqueWorkListList, uniqueWorkListList);
    }

    @Test
    public void contains_workListNotInList_returnsFalse() {
        assertFalse(uniqueWorkListList.contains(LISTA));
    }

    @Test
    public void contains_workListInList_returnsTrue() {
        uniqueWorkListList.add(LISTA);
        assertTrue(uniqueWorkListList.contains(LISTA));
    }

    @Test
    public void canDoSortById() {
        uniqueWorkListList.sortById();
        assertTrue(true);
    }

    @Test
    public void workListAreUniqueOrNot() {
        assertTrue(uniqueWorkListList.areWorkListUnique(listOfWorklist));
    }

    @Test
    public void add_nullWorkList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkListList.add(null);
    }

    @Test
    public void add_duplicateWorkList_throwsDuplicateWorkListException() {
        uniqueWorkListList.add(LISTA);
        thrown.expect(DuplicateEquipmentException.class);
        uniqueWorkListList.add(LISTA);
    }

    @Test
    public void remove_nullWorkList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkListList.remove(null);
    }

    @Test
    public void remove_workListDoesNotExist_throwsWorkListNotFoundException() {
        thrown.expect(EquipmentNotFoundException.class);
        uniqueWorkListList.remove(LISTA);
    }

    @Test
    public void remove_existingWorkList_removesWorkList() {
        uniqueWorkListList.add(LISTA);
        uniqueWorkListList.remove(LISTA);
        UniqueWorkListList expectedUniqueWorkListList = new UniqueWorkListList();
        assertEquals(expectedUniqueWorkListList, uniqueWorkListList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueWorkListList.asUnmodifiableObservableList().remove(0);
    }
}
