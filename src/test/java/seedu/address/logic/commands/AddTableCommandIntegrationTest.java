package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRestOrRant.TABLE6;
import static seedu.address.testutil.TypicalRestOrRant.TABLE8;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableStatus;

/**
 * Contains integration tests (interaction with the Model) for {@code AddTableCommand}.
 */
public class AddTableCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    }

    //    @Test TODO
    //    public void execute_newTable_success() {
    //        List<TableStatus> tableStatuses = new ArrayList<>();
    //        tableStatuses.add(new TableStatus("1/4"));
    //
    //        Model expectedModel = new ModelManager();
    //        expectedModel.addTable(TABLE8);
    //
    //        assertCommandSuccess(Mode.RESTAURANT_MODE, new AddTableCommand(tableStatuses), model,
    //                commandHistory, AddTableCommand.MESSAGE_SUCCESS
    //                        + String.format(AddTableCommand.MESSAGE_TABLE_ADDED, TABLE8.getTableNumber().toString(),
    //                        TABLE8.getTableNumber().toString()), expectedModel);
    //    }
    //
    //    @Test
    //    public void execute_newTables_success() {
    //        List<TableStatus> tableStatuses = new ArrayList<>();
    //        tableStatuses.add(new TableStatus("1/4"));
    //        tableStatuses.add(new TableStatus("0/3"));
    //
    //        Model expectedModel = new ModelManager();
    //        expectedModel.addTable(TABLE8);
    //        expectedModel.addTable(TABLE6);
    //
    //
    //        assertCommandSuccess(Mode.RESTAURANT_MODE, new AddTableCommand(tableStatuses), model,
    //                commandHistory, AddTableCommand.MESSAGE_SUCCESS
    //                        + String.format(AddTableCommand.MESSAGE_TABLE_ADDED, TABLE8.getTableNumber().toString(),
    //                        TABLE8.getTableStatus().toString()) + String.format(AddTableCommand.MESSAGE_TABLE_ADDED,
    //                        TABLE6.getTableStatus().toString(), TABLE6.getTableStatus().toString()), expectedModel);
    //    }

}
