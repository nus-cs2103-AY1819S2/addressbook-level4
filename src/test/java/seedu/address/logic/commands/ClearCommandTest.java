package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.FoodDiary;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PostalDataSet;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyFoodDiary_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitFoodDiary();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyFoodDiary_success() {
        Model model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
        Model expectedModel = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
        expectedModel.setFoodDiary(new FoodDiary());
        expectedModel.commitFoodDiary();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
