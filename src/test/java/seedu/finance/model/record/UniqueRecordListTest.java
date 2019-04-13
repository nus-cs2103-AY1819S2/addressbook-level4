package seedu.finance.model.record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static seedu.finance.testutil.TypicalRecords.APPLE;
import static seedu.finance.testutil.TypicalRecords.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.finance.model.record.exceptions.RecordNotFoundException;
import seedu.finance.testutil.RecordBuilder;

public class UniqueRecordListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueRecordList uniqueRecordList = new UniqueRecordList();

    @Test
    public void contains_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.contains(null);
    }

    @Test
    public void contains_recordNotInList_returnsFalse() {
        assertFalse(uniqueRecordList.contains(APPLE));
    }

    @Test
    public void contains_recordInList_returnsTrue() {
        uniqueRecordList.add(APPLE);
        assertTrue(uniqueRecordList.contains(APPLE));
    }

    @Test
    public void contains_recordWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRecordList.add(APPLE);
        Record editedApple = new RecordBuilder(APPLE).withAmount(VALID_AMOUNT_BOB)
                .withCategory(VALID_CATEGORY_HUSBAND)
                .build();
        assertTrue(uniqueRecordList.contains(editedApple));
    }

    @Test
    public void add_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.add(null);
    }

    @Test
    public void add_duplicateRecord_success() {
        uniqueRecordList.add(APPLE);
        uniqueRecordList.add(APPLE);
        assertEquals(Arrays.asList(APPLE, APPLE), uniqueRecordList.asUnmodifiableObservableList());
    }

    @Test
    public void setRecord_nullTargetRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecord(null, APPLE);
    }

    @Test
    public void setRecord_nullEditedRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecord(APPLE, null);
    }

    @Test
    public void setRecord_targetRecordNotInList_throwsRecordNotFoundException() {
        thrown.expect(RecordNotFoundException.class);
        uniqueRecordList.setRecord(APPLE, APPLE);
    }

    @Test
    public void setRecord_editedRecordIsSameRecord_success() {
        uniqueRecordList.add(APPLE);
        uniqueRecordList.setRecord(APPLE, APPLE);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(APPLE);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecord_editedRecordHasSameIdentity_success() {
        uniqueRecordList.add(APPLE);
        Record editedApple = new RecordBuilder(APPLE).withAmount(VALID_AMOUNT_BOB)
                .withCategory(VALID_CATEGORY_HUSBAND)
                .build();
        uniqueRecordList.setRecord(APPLE, editedApple);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(editedApple);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecord_editedRecordHasDifferentIdentity_success() {
        uniqueRecordList.add(APPLE);
        uniqueRecordList.setRecord(APPLE, BOB);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(BOB);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecord_editedRecordHasNonUniqueIdentity_success() {
        uniqueRecordList.add(APPLE);
        uniqueRecordList.add(BOB);
        uniqueRecordList.setRecord(APPLE, BOB);
        assertEquals(Arrays.asList(BOB, BOB), uniqueRecordList.asUnmodifiableObservableList());
    }

    @Test
    public void remove_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.remove(null);
    }

    @Test
    public void remove_recordDoesNotExist_throwsRecordNotFoundException() {
        thrown.expect(RecordNotFoundException.class);
        uniqueRecordList.remove(APPLE);
    }

    @Test
    public void remove_existingRecord_removesRecord() {
        uniqueRecordList.add(APPLE);
        uniqueRecordList.remove(APPLE);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecords_nullUniqueRecordList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecords((UniqueRecordList) null);
    }

    @Test
    public void setRecords_uniqueRecordList_replacesOwnListWithProvidedUniqueRecordList() {
        uniqueRecordList.add(APPLE);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(BOB);
        uniqueRecordList.setRecords(expectedUniqueRecordList);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecords_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecords((List<Record>) null);
    }

    @Test
    public void setRecords_list_replacesOwnListWithProvidedList() {
        uniqueRecordList.add(APPLE);
        List<Record> recordList = Collections.singletonList(BOB);
        uniqueRecordList.setRecords(recordList);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(BOB);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecords_listWithDuplicateRecords_success() {
        List<Record> listWithDuplicateRecords = Arrays.asList(APPLE, APPLE);
        uniqueRecordList.setRecords(listWithDuplicateRecords);

        assertEquals(Arrays.asList(APPLE, APPLE), uniqueRecordList.asUnmodifiableObservableList());


    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRecordList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void sameUniqueRecordList() {
        UniqueRecordList uniqueRecordListOne = new UniqueRecordList();
        UniqueRecordList uniqueRecordListTwo = new UniqueRecordList();

        uniqueRecordListOne.add(APPLE);
        assertFalse(uniqueRecordListOne.hashCode() == uniqueRecordListTwo.hashCode());

        uniqueRecordListTwo.add(APPLE);
        assertTrue(uniqueRecordListOne.hashCode() == uniqueRecordListTwo.hashCode());

        assertTrue(uniqueRecordListOne.iterator().next() == uniqueRecordListTwo.iterator().next());
    }
}
