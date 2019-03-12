package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRecords.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.record.Record;
import seedu.address.testutil.RecordBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class SpendCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Record validRecord = new RecordBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addRecord(validRecord);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new SpendCommand(validRecord), model, commandHistory,
                String.format(SpendCommand.MESSAGE_SUCCESS, validRecord), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Record recordInList = model.getAddressBook().getRecordList().get(0);
        assertCommandFailure(new SpendCommand(recordInList), model, commandHistory,
                SpendCommand.MESSAGE_DUPLICATE_RECORD);
    }

}
