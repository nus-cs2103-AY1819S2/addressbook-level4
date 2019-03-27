package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1;
import static seedu.address.testutil.TypicalRestOrRant.TABLE2;
import static seedu.address.testutil.TypicalRestOrRant.TABLE3;
import static seedu.address.testutil.TypicalRestOrRant.TABLE4;
import static seedu.address.testutil.TypicalRestOrRant.TABLE5;
import static seedu.address.testutil.TypicalRestOrRant.TABLE6;
import static seedu.address.testutil.TypicalRestOrRant.TABLE7;
import static seedu.address.testutil.TypicalRestOrRant.TABLE8;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.table.Table;
import seedu.address.testutil.TableBuilder;

public class ClearTableCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    public static final String UNOCCUPIED_TABLE_STATUS = "0/4";

    @Test
    public void execute_noTablesInRestaurant_failure() {
        Model model = new ModelManager();
        assertCommandFailure(Mode.RESTAURANT_MODE, new ClearTableCommand(), model, commandHistory,
                ClearTableCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_occupiedTableInRestaurant_failure() {
        Model model = new ModelManager();
        Table table = TABLE1;
        model.addTable(table);
        Model expectedModel = new ModelManager();
        expectedModel.addTable(table);

        assertCommandFailure(Mode.RESTAURANT_MODE, new ClearTableCommand(), model, commandHistory,
                ClearTableCommand.INVALID_RESTAURANT_STATE);
    }

    @Test
    public void execute_singleUnoccupiedTableInRestaurant_success() {
        Model model = new ModelManager();
        Table table = new TableBuilder(TABLE1).withTableStatus(UNOCCUPIED_TABLE_STATUS).build();
        model.addTable(table);
        Model expectedModel = new ModelManager();

        assertCommandSuccess(Mode.RESTAURANT_MODE, new ClearTableCommand(), model, commandHistory,
                ClearTableCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_multipleUnoccupiedTableInRestaurant_success() {
        Model model = new ModelManager();
        model.addTable(new TableBuilder(TABLE1).withTableStatus(UNOCCUPIED_TABLE_STATUS).build());
        model.addTable(new TableBuilder(TABLE2).withTableStatus(UNOCCUPIED_TABLE_STATUS).build());
        model.addTable(new TableBuilder(TABLE3).withTableStatus(UNOCCUPIED_TABLE_STATUS).build());
        model.addTable(new TableBuilder(TABLE4).withTableStatus(UNOCCUPIED_TABLE_STATUS).build());
        model.addTable(new TableBuilder(TABLE5).withTableStatus(UNOCCUPIED_TABLE_STATUS).build());
        model.addTable(new TableBuilder(TABLE6).withTableStatus(UNOCCUPIED_TABLE_STATUS).build());
        model.addTable(new TableBuilder(TABLE7).withTableStatus(UNOCCUPIED_TABLE_STATUS).build());
        model.addTable(new TableBuilder(TABLE8).withTableStatus(UNOCCUPIED_TABLE_STATUS).build());
        Model expectedModel = new ModelManager();

        assertCommandSuccess(Mode.RESTAURANT_MODE, new ClearTableCommand(), model, commandHistory,
                ClearTableCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
