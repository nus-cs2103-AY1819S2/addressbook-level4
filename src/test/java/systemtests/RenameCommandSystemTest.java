package systemtests;

//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
//import static seedu.pdf.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
//import static seedu.pdf.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
//import static seedu.pdf.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
//import static seedu.pdf.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
//import static seedu.pdf.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
//import static seedu.pdf.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
//import static seedu.pdf.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static seedu.pdf.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
//import static seedu.pdf.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
//import static seedu.pdf.logic.commands.CommandTestUtil.NAME_DESC_AMY;
//import static seedu.pdf.logic.commands.CommandTestUtil.NAME_DESC_BOB;
//import static seedu.pdf.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
//import static seedu.pdf.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
//import static seedu.pdf.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
//import static seedu.pdf.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
//import static seedu.pdf.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
//import static seedu.pdf.logic.commands.CommandTestUtil.VALID_NAME_AMY;
//import static seedu.pdf.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static seedu.pdf.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
//import static seedu.pdf.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.pdf.logic.parser.CliSyntax.PREFIX_TAG_ADD;
import static seedu.pdf.model.Model.PREDICATE_SHOW_ALL_PDFS;

import seedu.pdf.commons.core.index.Index;
//import static seedu.pdf.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.pdf.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static seedu.pdf.testutil.TypicalPdfs.AMY;
//import static seedu.pdf.testutil.TypicalPdfs.BOB;
//import static seedu.pdf.testutil.TypicalPdfs.KEYWORD_MATCHING_MEIER;
//import org.junit.Test;
//import seedu.pdf.commons.core.Messages;
import seedu.pdf.logic.commands.RenameCommand;
//import seedu.pdf.logic.commands.RedoCommand;
//import seedu.pdf.logic.commands.UndoCommand;
import seedu.pdf.model.Model;
//import seedu.pdf.model.pdf.Address;
//import seedu.pdf.model.pdf.Email;
//import seedu.pdf.model.pdf.Name;
import seedu.pdf.model.pdf.Pdf;
//import seedu.pdf.model.pdf.Phone;
//import seedu.pdf.model.tag.Tag;
//import seedu.pdf.testutil.PdfBuilder;
//import seedu.pdf.testutil.PdfUtil;

public class RenameCommandSystemTest extends AddressBookSystemTest {

    /*

    @Test
    public void edit() {
        Model model = getModel();

        *//* ----------------- Performing edit operation while an unfiltered list is being shown -------
        --------------- *//*

        *//* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         *//*
        Index index = INDEX_FIRST_PERSON;
        String command = " " + RenameCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
                + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + "  " + ADDRESS_DESC_BOB + " " + TAG_DESC_HUSBAND + " ";
        Pdf editedPdf = new PdfBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedPdf);

        *//* Case: undo editing the last pdf in the list -> last pdf restored *//*
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        *//* Case: redo editing the last pdf in the list -> last pdf edited again *//*
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setPdf(getModel().getFilteredPdfList().get(INDEX_FIRST_PERSON.getZeroBased()), editedPdf);
        assertCommandSuccess(command, model, expectedResultMessage);

        *//* Case: edit a pdf with new values same as existing values -> edited *//*
        command = RenameCommand.COMMAND_WORD + " "
                + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        *//* Case: edit a pdf with new values same as another pdf's values but with different name -> edited *//*
        assertTrue(getModel().getPdfBook().getPdfList().contains(BOB));
        index = INDEX_SECOND_PERSON;
        assertNotEquals(getModel().getFilteredPdfList().get(index.getZeroBased()), BOB);
        command = RenameCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_A + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedPdf = new PdfBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedPdf);

        *//* Case: edit a pdf with new values same as another pdf's values but with different phone and email
         * -> edited
         *//*
        index = INDEX_SECOND_PERSON;
        command = RenameCommand.COMMAND_WORD + " " + index.getOneBased()
        + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedPdf = new PdfBuilder(BOB).withSize(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build();
        assertCommandSuccess(command, index, editedPdf);

        *//* Case: clear tags -> cleared *//*
        index = INDEX_FIRST_PERSON;
        command = RenameCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG_ADD.getPrefix();
        Pdf pdfToEdit = getModel().getFilteredPdfList().get(index.getZeroBased());
        editedPdf = new PdfBuilder(pdfToEdit).withTags().build();
        assertCommandSuccess(command, index, editedPdf);

        *//* ------------------ Performing edit operation while a filtered list is being shown -----------------
        ------- *//*

        *//* Case: filtered pdf list, edit index within bounds of pdf book and pdf list -> edited *//*
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPdfList().size());
        command = RenameCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        pdfToEdit = getModel().getFilteredPdfList().get(index.getZeroBased());
        editedPdf = new PdfBuilder(pdfToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedPdf);

        *//* Case: filtered pdf list, edit index within bounds of pdf book but out of bounds of pdf list
         * -> rejected
         *//*
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getPdfBook().getPdfList().size();
        assertCommandFailure(RenameCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);

        *//* --------------------- Performing edit operation while a pdf card is selected ----------------------
        ---- *//*

        *//* Case: selects first card in the pdf list, edit a pdf -> edited, card selection remains unchanged but
         * browser url changes
         *//*
        showAllPersons();
        index = INDEX_FIRST_PERSON;
        selectPerson(index);
        command = RenameCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_A + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new pdf's name
        assertCommandSuccess(command, index, AMY, index);

        *//* --------------------------------- Performing invalid edit operation --------------------------------
        ------ *//*

        *//* Case: invalid index (0) -> rejected *//*
        assertCommandFailure(RenameCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RenameCommand.MESSAGE_USAGE));

        *//* Case: invalid index (-1) -> rejected *//*
        assertCommandFailure(RenameCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RenameCommand.MESSAGE_USAGE));

        *//* Case: invalid index (size + 1) -> rejected *//*
        invalidIndex = getModel().getFilteredPdfList().size() + 1;
        assertCommandFailure(RenameCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);

        *//* Case: missing index -> rejected *//*
        assertCommandFailure(RenameCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RenameCommand.MESSAGE_USAGE));

        *//* Case: missing all fields -> rejected *//*
        assertCommandFailure(RenameCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                RenameCommand.MESSAGE_NOT_EDITED);

        *//* Case: invalid name -> rejected *//*
        assertCommandFailure(RenameCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        *//* Case: invalid phone -> rejected *//*
        assertCommandFailure(RenameCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        *//* Case: invalid email -> rejected *//*
        assertCommandFailure(RenameCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS);

        *//* Case: invalid pdf -> rejected *//*
        assertCommandFailure(RenameCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS);

        *//* Case: invalid tag -> rejected *//*
        assertCommandFailure(RenameCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS);

        *//* Case: edit a pdf with new values same as another pdf's values -> rejected *//*
        executeCommand(PdfUtil.getAddCommand(BOB));
        assertTrue(getModel().getPdfBook().getPdfList().contains(BOB));
        index = INDEX_FIRST_PERSON;
        assertFalse(getModel().getFilteredPdfList().get(index.getZeroBased()).equals(BOB));
        command = RenameCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, RenameCommand.MESSAGE_DUPLICATE_PDF);

        *//* Case: edit a pdf with new values same as another pdf's values but with different tags -> rejected *//*
        command = RenameCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, RenameCommand.MESSAGE_DUPLICATE_PDF);

        *//* Case: edit a pdf with new values same as another pdf's values but with different pdf -> rejected *//*
        command = RenameCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, RenameCommand.MESSAGE_DUPLICATE_PDF);

        *//* Case: edit a pdf with new values same as another pdf's values but with different phone -> rejected *//*
        command = RenameCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, RenameCommand.MESSAGE_DUPLICATE_PDF);

        *//* Case: edit a pdf with new values same as another pdf's values but with different email -> rejected *//*
        command = RenameCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, RenameCommand.MESSAGE_DUPLICATE_PDF);
    } */

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Pdf, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see RenameCommandSystemTest#assertCommandSuccess(String, Index, Pdf, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Pdf editedPdf) {
        assertCommandSuccess(command, toEdit, editedPdf, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code RenameCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the pdf at index {@code toEdit} being
     * updated to values specified {@code editedPdf}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see RenameCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Pdf editedPdf,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setPdf(expectedModel.getFilteredPdfList().get(toEdit.getZeroBased()), editedPdf);
        expectedModel.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);

        assertCommandSuccess(command, expectedModel,
                String.format(RenameCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see RenameCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
