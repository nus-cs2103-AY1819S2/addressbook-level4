package seedu.address.logic.commands.request;

import org.junit.jupiter.api.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.request.Request;
import seedu.address.testutil.EditRequestDescriptorBuilder;
import seedu.address.testutil.RequestBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

class EditRequestCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(),
        getTypicalHealthWorkerBook(), getTypicalRequestBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    @Test
    void execute() {
        Request editedRequest =
            new RequestBuilder().withAddress(DEFAULT_ADDRESS).withHealthStaff(ANDY).build();
        EditRequestCommand.EditRequestDescriptor descriptor =
            new EditRequestDescriptorBuilder(editedRequest).build();
        EditRequestCommand editRequestCommand = new EditRequestCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditRequestCommand.MESSAGE_EDIT_REQUEST_SUCCESS,
            editedRequest);

        Model expectedModel = new ModelManager(model.getAddressBook(),
            model.getHealthWorkerBook(), model.getRequestBook(), new UserPrefs());
        expectedModel.updateRequest(model.getFilteredRequestList().get(0), editedRequest);
        expectedModel.commitRequestBook();

        assertCommandSuccess(editRequestCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }
}