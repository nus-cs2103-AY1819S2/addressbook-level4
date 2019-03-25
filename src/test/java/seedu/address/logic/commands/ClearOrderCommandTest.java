package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W09;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W12;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestOrRant;
import seedu.address.model.UserPrefs;
import seedu.address.model.table.Table;
import seedu.address.testutil.TypicalRestOrRant;

public class ClearOrderCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_noSelectedTable_failure() {
        Model model = new ModelManager();
        assertCommandFailure(Mode.TABLE_MODE, new ClearOrderCommand(), model, commandHistory,
                ClearOrderCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_emptyOrderList_success() {
        Model model = new ModelManager();
        Table table = TABLE1;
        model.addTable(table);
        model.setSelectedTable(table);
        Model expectedModel = new ModelManager();
        expectedModel.addTable(table);
        expectedModel.setSelectedTable(table);
        expectedModel.updateOrders();
        
        assertCommandSuccess(Mode.TABLE_MODE, new ClearOrderCommand(), model, commandHistory,
                String.format(ClearOrderCommand.MESSAGE_SUCCESS, table.getTableNumber()), expectedModel);
    }

    @Test
    public void execute_nonEmptyOrderList_success() {
        Model model = new ModelManager();
        Table table = TABLE1;
        model.addTable(table);
        model.setSelectedTable(table);
        Model expectedModel = new ModelManager();
        expectedModel.addTable(table);
        expectedModel.setSelectedTable(table);
        model.addOrderItem(TABLE1_W09);
        model.addOrderItem(TABLE1_W12);

        assertCommandSuccess(Mode.TABLE_MODE, new ClearOrderCommand(), model, commandHistory,
                String.format(ClearOrderCommand.MESSAGE_SUCCESS, table.getTableNumber()), expectedModel);
    }

}
