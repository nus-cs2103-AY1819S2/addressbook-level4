package seedu.address.logic.commands;

//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPdfs.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PdfBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.pdf.Pdf;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PdfBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Pdf editedPdf = new PdfBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPdf).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(model.getFilteredPdfList().get(0), editedPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    /*
    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPdfList().size());
        Pdf lastPdf = model.getFilteredPdfList().get(indexLastPerson.getZeroBased());

        PdfBuilder personInList = new PdfBuilder(lastPdf);
        Pdf editedPdf = personInList.withName(VALID_NAME_BOB).withSize(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(lastPdf, editedPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Pdf editedPdf = model.getFilteredPdfList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.commitPdfBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Pdf pdfInFilteredList = model.getFilteredPdfList().get(INDEX_FIRST_PERSON.getZeroBased());
        Pdf editedPdf = new PdfBuilder(pdfInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(model.getFilteredPdfList().get(0), editedPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Pdf firstPdf = model.getFilteredPdfList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPdf).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit pdf in filtered list into a duplicate in address book
        Pdf pdfInList = model.getPdfBook().getPdfList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(pdfInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPdfList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    *//**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     *//*
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPdfBook().getPdfList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Pdf editedPdf = new PdfBuilder().build();
        Pdf pdfToEdit = model.getFilteredPdfList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPdf).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(pdfToEdit, editedPdf);
        expectedModel.commitPdfBook();

        // edit -> first pdf edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered pdf list to show all persons
        expectedModel.undoPdfBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first pdf edited again
        expectedModel.redoPdfBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPdfList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    *//**
     * 1. Edits a {@code Pdf} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited pdf in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the pdf object regardless of indexing.
     *//*
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Pdf editedPdf = new PdfBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPdf).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Pdf pdfToEdit = model.getFilteredPdfList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.setPdf(pdfToEdit, editedPdf);
        expectedModel.commitPdfBook();

        // edit -> edits second pdf in unfiltered pdf list / first pdf in filtered pdf list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered pdf list to show all persons
        expectedModel.undoPdfBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredPdfList().get(INDEX_FIRST_PERSON.getZeroBased()), pdfToEdit);
        // redo -> edits same second pdf in unfiltered pdf list
        expectedModel.redoPdfBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }*/

}
