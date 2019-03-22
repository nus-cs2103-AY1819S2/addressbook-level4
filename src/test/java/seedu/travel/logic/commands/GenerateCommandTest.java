package seedu.travel.logic.commands;

import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

import seedu.travel.model.Model;
import seedu.travel.model.ModelManager;
import seedu.travel.model.UserPrefs;

public class GenerateCommandTest {

    private Model model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());

}
