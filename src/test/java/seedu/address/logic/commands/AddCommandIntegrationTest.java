package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.apparel.Apparel;
import seedu.address.testutil.ApparelBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Apparel validApparel = new ApparelBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addApparel(validApparel);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validApparel), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validApparel), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Apparel apparelInList = model.getAddressBook().getApparelList().get(0);
        assertCommandFailure(new AddCommand(apparelInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_APPAREL);
    }

}
