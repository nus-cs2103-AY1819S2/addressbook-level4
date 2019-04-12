package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TransformationSet;
import seedu.address.model.UserPrefs;

public class SetPresetCommandTest {
    private Album album = Album.getInstance();
    private TransformationSet transformationSet = TransformationSet.getInstance();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CurrentEdit currentEdit = new CurrentEditManager();
    private CommandHistory commandHistory = new CommandHistory();


}
