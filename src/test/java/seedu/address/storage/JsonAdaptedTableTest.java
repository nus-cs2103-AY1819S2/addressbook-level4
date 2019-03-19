package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;
import seedu.address.testutil.Assert;

public class JsonAdaptedTableTest {

    private static final String INVALID_TABLE_NUMBER = "abc";
    private static final String INVALID_TABLE_STATUS = "@bc";

    private static final String VALID_TABLE_NUMBER = TABLE1.getTableNumber().toString();
    private static final String VALID_TABLE_STATUS = TABLE1.getTableStatus().toString();

    @Test
    public void toModelType_validTableDetails_returnsTable() throws Exception {
        JsonAdaptedTable table = new JsonAdaptedTable(TABLE1);
        assertEquals(TABLE1, table.toModelType());
    }

    @Test
    public void toModelType_invalidTableNumber_throwsIllegalValueException() {
        JsonAdaptedTable table = new JsonAdaptedTable(INVALID_TABLE_NUMBER, VALID_TABLE_STATUS);
        String expectedMessage = TableNumber.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, table::toModelType);
    }

    @Test
    public void toModelType_nullTableNumber_throwsIllegalValueException() {
        JsonAdaptedTable table = new JsonAdaptedTable(null, VALID_TABLE_STATUS);
        String expectedMessage = TableNumber.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, table::toModelType);
    }

    @Test
    public void toModelType_invalidTableStatus_throwsIllegalValueException() {
        JsonAdaptedTable table =
                new JsonAdaptedTable(VALID_TABLE_NUMBER, INVALID_TABLE_STATUS);
        String expectedMessage = TableStatus.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, table::toModelType);
    }

    @Test
    public void toModelType_nullTableStatus_throwsIllegalValueException() {
        JsonAdaptedTable table = new JsonAdaptedTable(VALID_TABLE_NUMBER, null);
        String expectedMessage = String.format(TableStatus.MESSAGE_CONSTRAINTS);
        Assert.assertThrows(IllegalValueException.class, expectedMessage, table::toModelType);
    }

}
