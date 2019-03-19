package seedu.address.model.table;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalTables;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.exceptions.DuplicateOrderItemException;
import seedu.address.testutil.TableBuilder;

public class TablesTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Tables tables = new Tables();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), tables.getTableList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        tables.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTables_replacesData() {
        Tables newData = new Tables();
        for (Table table : getTypicalTables()) {
            newData.addTable(table);
        }
        tables.resetData(newData);
        assertEquals(newData, tables);
    }

    @Test
    public void resetData_withDuplicateTables_throwsDuplicateTablesException() {
        // Two order items with the same identity fields
        Table editedTable = new TableBuilder(TABLE1).build();
        List<Table> newTables = Arrays.asList(TABLE1, editedTable);
        TablesStub newData = new TablesStub(newTables);

        thrown.expect(DuplicateOrderItemException.class);
        tables.resetData(newData);
    }

    @Test
    public void hasTable_nullTable_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        tables.hasTable(null);
    }

    @Test
    public void hasTable_tableNotInTables_returnsFalse() {
        assertFalse(tables.hasTable(TABLE1));
    }

    @Test
    public void hasTable_tableInTables_returnsTrue() {
        tables.addTable(TABLE1);
        assertTrue(tables.hasTable(TABLE1));
    }

    @Test
    public void hasOrderItem_orderItemWithSameIdentityFieldsInOrders_returnsTrue() {
        tables.addTable(TABLE1);
        Table editedTable = new TableBuilder(TABLE1).withTableStatus("0/4").build();
        assertTrue(tables.hasTable(editedTable));
    }

    @Test
    public void getTableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        tables.getTableList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        tables.addListener(listener);
        tables.addTable(TABLE1);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        tables.addListener(listener);
        tables.removeListener(listener);
        tables.addTable(TABLE1);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyTables whose table list can violate interface constraints.
     */
    private static class TablesStub implements ReadOnlyTables {
        private final ObservableList<Table> tables = FXCollections.observableArrayList();

        TablesStub(Collection<Table> tableCollection) {
            this.tables.setAll(tableCollection);
        }

        @Override
        public ObservableList<Table> getTableList() {
            return tables;
        }

        @Override
        public boolean hasTable(Table table) {
            requireNonNull(table);
            return tables.contains(table);
        }

        @Override
        public void addTable(Table table) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TableNumber addTable(TableStatus tableStatus) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Table> getTableFromNumber(TableNumber tableNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTables(List<Table> tableList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTable(Table target, Table editedTable) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeTable(Table key) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isOccupied(TableNumber tableNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

    }
}
