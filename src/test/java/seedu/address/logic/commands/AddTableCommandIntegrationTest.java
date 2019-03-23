package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.address.model.table.TableNumber;
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

    @Test
    public void execute_newTable_success() {
        List<TableStatus> tableStatuses = new ArrayList<>();
        tableStatuses.add(new TableStatus("1/4"));

        Model expectedModel = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
        expectedModel.addTable(new TableStatus("1/4"));

        assertCommandSuccess(Mode.RESTAURANT_MODE, new AddTableCommand(tableStatuses), model,
                commandHistory, AddTableCommand.MESSAGE_SUCCESS
                        + String.format(AddTableCommand.MESSAGE_TABLE_ADDED, new TableNumber("9"),
                        new TableStatus("1/4")), expectedModel);
    }

    @Test
    public void execute_newTables_success() {
        List<TableStatus> tableStatuses = new ArrayList<>();
        TableStatus tableStatus1 = new TableStatus("1/4");
        TableStatus tableStatus2 = new TableStatus("0/3");
        tableStatuses.add(tableStatus1);
        tableStatuses.add(tableStatus2);

        Model expectedModel = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
        expectedModel.addTable(tableStatus1);
        expectedModel.addTable(tableStatus2);


        assertCommandSuccess(Mode.RESTAURANT_MODE, new AddTableCommand(tableStatuses), model,
                commandHistory, AddTableCommand.MESSAGE_SUCCESS
                        + String.format(AddTableCommand.MESSAGE_TABLE_ADDED, new TableNumber("9"),
                        tableStatus1) + String.format(AddTableCommand.MESSAGE_TABLE_ADDED,
                        new TableNumber("10"), tableStatus2), expectedModel);
    }

}
