package seedu.address.model.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1;
import static seedu.address.testutil.TypicalRestOrRant.TABLE2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.order.exceptions.DuplicateOrderItemException;
import seedu.address.model.order.exceptions.OrderItemNotFoundException;
import seedu.address.model.table.exceptions.DuplicateTableException;
import seedu.address.model.table.exceptions.TableNotFoundException;
import seedu.address.testutil.TableBuilder;

public class UniqueTableListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueTableList uniqueTableList = new UniqueTableList();

    @Test
    public void contains_nullTable_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTableList.contains(null);
    }

    @Test
    public void contains_tableNotInList_returnsFalse() {
        assertFalse(uniqueTableList.contains(TABLE1));
    }

    @Test
    public void contains_tableInList_returnsTrue() {
        uniqueTableList.add(TABLE1);
        assertTrue(uniqueTableList.contains(TABLE1));
    }

    @Test
    public void add_nullTable_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTableList.add(null);
    }

    @Test
    public void add_duplicateTable_throwsDuplicateOrderItemException() {
        uniqueTableList.add(TABLE1);
        thrown.expect(DuplicateTableException.class);
        uniqueTableList.add(TABLE1);
    }

    @Test
    public void setTable_nullTargetTable_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTableList.setTable(null, TABLE1);
    }

    @Test
    public void setTable_nullEditedTable_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTableList.setTable(TABLE1, null);
    }

    @Test
    public void setTable_targetTableNotInList_throwsOrderItemNotFoundException() {
        thrown.expect(TableNotFoundException.class);
        uniqueTableList.setTable(TABLE1, TABLE1);
    }

    @Test
    public void setTable_editedTableIsSameTable_success() {
        uniqueTableList.add(TABLE1);
        uniqueTableList.setTable(TABLE1, TABLE1);
        UniqueTableList expectedUniqueTableList = new UniqueTableList();
        expectedUniqueTableList.add(TABLE1);
        assertEquals(expectedUniqueTableList, uniqueTableList);
    }

    @Test
    public void setTable_editedTableHasSameIdentity_success() {
        uniqueTableList.add(TABLE1);
        Table editedTable = new TableBuilder(TABLE1).withTableStatus("0/4").build();
        uniqueTableList.setTable(TABLE1, editedTable);
        UniqueTableList expectedUniqueTableList = new UniqueTableList();
        expectedUniqueTableList.add(editedTable);
        assertEquals(expectedUniqueTableList, uniqueTableList);
    }

    @Test
    public void setTable_editedTableHasDifferentIdentity_success() {
        uniqueTableList.add(TABLE1);
        uniqueTableList.setTable(TABLE1, TABLE2);
        UniqueTableList expectedUniqueTableList = new UniqueTableList();
        expectedUniqueTableList.add(TABLE2);
        assertEquals(expectedUniqueTableList, uniqueTableList);
    }

    @Test
    public void setTable_editedTableHasNonUniqueIdentity_throwsDuplicateOrderItemException() {
        uniqueTableList.add(TABLE1);
        uniqueTableList.add(TABLE2);
        thrown.expect(DuplicateTableException.class);
        uniqueTableList.setTable(TABLE1, TABLE2);
    }

    @Test
    public void remove_nullTable_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTableList.remove(null);
    }

    @Test
    public void remove_tableDoesNotExist_throwsTableNotFoundException() {
        thrown.expect(OrderItemNotFoundException.class);
        uniqueTableList.remove(TABLE1);
    }

    @Test
    public void remove_existingTable_removesTable() {
        uniqueTableList.add(TABLE1);
        uniqueTableList.remove(TABLE1);
        UniqueTableList expectedUniqueTableList = new UniqueTableList();
        assertEquals(expectedUniqueTableList, uniqueTableList);
    }

    @Test
    public void setTable_nullUniqueTableList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTableList.setTables((UniqueTableList) null);
    }

    @Test
    public void setTable_uniqueTableList_replacesOwnListWithProvidedUniqueTableList() {
        uniqueTableList.add(TABLE1);
        UniqueTableList expectedUniqueTableList = new UniqueTableList();
        expectedUniqueTableList.add(TABLE1);
        uniqueTableList.setTables(expectedUniqueTableList);
        assertEquals(expectedUniqueTableList, uniqueTableList);
    }

    @Test
    public void setTable_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTableList.setTables((List<Table>) null);
    }

    @Test
    public void setTable_list_replacesOwnListWithProvidedList() {
        uniqueTableList.add(TABLE1);
        List<Table> tableList = Collections.singletonList(TABLE1);
        uniqueTableList.setTables(tableList);
        UniqueTableList expectedUniqueTableList = new UniqueTableList();
        expectedUniqueTableList.add(TABLE1);
        assertEquals(expectedUniqueTableList, uniqueTableList);
    }

    @Test
    public void setTable_listWithDuplicateTable_throwsDuplicateOrderItemException() {
        List<Table> listWithDuplicateTable = Arrays.asList(TABLE1, TABLE1);
        thrown.expect(DuplicateOrderItemException.class);
        uniqueTableList.setTables(listWithDuplicateTable);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueTableList.asUnmodifiableObservableList().remove(0);
    }
}
