package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.prescription.EditPrescriptionCommand;
import seedu.address.model.DocX;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.prescription.Prescription;
import seedu.address.testutil.EditPrescriptionDescriptorBuilder;
import seedu.address.testutil.PrescriptionBuilder;
import seedu.address.testutil.TypicalPrescriptions;

/**
 * Contains integration tests (interaction with the Model)
 * and unit tests for EditPrescriptionCommand.
 */
public class EditPrescriptionCommandTest {

    private Model model = new ModelManager(TypicalPrescriptions.getTypicalDocX_prescription(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredMedHistList_success() {
        Index indexLastPrescription = Index.fromOneBased(model.getFilteredPrescriptionList().size());
        Prescription lastPrescription = model.getFilteredPrescriptionList().get(indexLastPrescription.getZeroBased());

        PrescriptionBuilder prescriptionInList = new PrescriptionBuilder(lastPrescription);
        Prescription editedPrescription = prescriptionInList.withDescription(VALID_DESCRIPTION).build();

        EditPrescriptionCommand.EditPrescriptionDescriptor descriptor = new EditPrescriptionDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION).build();
        EditPrescriptionCommand editPrescriptionCommand =
                new EditPrescriptionCommand(indexLastPrescription, descriptor);

        String expectedMessage =
                String.format(EditPrescriptionCommand.MESSAGE_EDIT_PRESCRIPTION_SUCCESS, editedPrescription);

        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());
        expectedModel.setPrescription(lastPrescription, editedPrescription);
        expectedModel.commitDocX();

        assertCommandSuccess(editPrescriptionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

}

