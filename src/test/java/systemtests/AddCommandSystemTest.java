package systemtests;

import static seedu.pdf.logic.commands.CommandTestUtil.DIR_2_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.FILEPATH_1_VALID;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_2;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_2_DUPLICATE;

import org.junit.Test;

import seedu.pdf.logic.commands.AddCommand;
import seedu.pdf.logic.commands.RedoCommand;
import seedu.pdf.logic.commands.UndoCommand;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.testutil.PdfBuilder;
import seedu.pdf.testutil.PdfUtil;

public class AddCommandSystemTest extends PdfBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a pdf without tags to a non-empty pdf book, command with leading spaces and trailing spaces
         * -> added
         */
        Pdf toAdd = SAMPLE_PDF_1;

        String command = AddCommand.COMMAND_WORD + " " + PREFIX_FILE + FILEPATH_1_VALID;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding SAMPLE_PDF_1 to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding SAMPLE_PDF_1 to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPdf(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add another pdf with different properties */
        toAdd = new PdfBuilder(SAMPLE_PDF_2).build();
        command = AddCommand.COMMAND_WORD + DIR_2_VALID;
        assertCommandSuccess(command, toAdd);

        /* Case: add a pdf with the same name but different directory -> added*/
        toAdd = SAMPLE_PDF_2_DUPLICATE;
        command = PdfUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty pdf book -> added */
        deleteAllPdf();
        assertCommandSuccess(SAMPLE_PDF_1);

        /*
        *//* Case: add a pdf with tags, command with parameters in random order -> added *//*
        toAdd = SAMPLE_PDF_3;
        command = AddCommand.COMMAND_WORD + TAG_DESC_CS2103T + DIR_3_VALID;
        assertCommandSuccess(command, toAdd);

        *//* Case: add a pdf, missing tags -> added *//*
        assertCommandSuccess(HOON);

        *//* -------------------------- Perform add operation on the shown filtered list ------------------------
        ------ *//*

        *//* Case: filters the pdf list before adding -> added *//*
        showPdfWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        *//* ------------------------ Perform add operation while a pdf card is selected ------------------------
        --- *//*

        *//* Case: selects first card in the pdf list, add a pdf -> added, card selection remains unchanged *//*
        selectPdf(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        *//* ----------------------------------- Perform invalid add operations ---------------------------------
        ------ *//*

        *//* Case: add a duplicate pdf -> rejected *//*
        command = PdfUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PDF);

        *//* Case: add a duplicate pdf except with different phone -> rejected *//*
        toAdd = new PdfBuilder(HOON).withSize(VALID_PHONE_BOB).build();
        command = PdfUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PDF);

        *//* Case: add a duplicate pdf except with different email -> rejected *//*
        toAdd = new PdfBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
        command = PdfUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PDF);

        *//* Case: add a duplicate pdf except with different pdf -> rejected *//*
        toAdd = new PdfBuilder(HOON).withDirectory(VALID_ADDRESS_BOB).build();
        command = PdfUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PDF);

        *//* Case: add a duplicate pdf except with different tags -> rejected *//*
        command = PdfUtil.getAddCommand(HOON) + " " + PREFIX_TAG_ADD.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PDF);

        *//* Case: missing name -> rejected *//*
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        *//* Case: missing phone -> rejected *//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        *//* Case: missing email -> rejected *//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        *//* Case: missing pdf -> rejected *//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        *//* Case: invalid keyword -> rejected *//*
        command = "adds " + PdfUtil.getPdfDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        *//* Case: invalid name -> rejected *//*
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        *//* Case: invalid phone -> rejected *//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_CONSTRAINTS);

        *//* Case: invalid email -> rejected *//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_CONSTRAINTS);

        *//* Case: invalid pdf -> rejected *//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC;
        assertCommandFailure(command, Address.MESSAGE_CONSTRAINTS);

        *//* Case: invalid tag -> rejected *//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);*/
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PdfListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code PdfBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PdfBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Pdf toAdd) {
        assertCommandSuccess(PdfUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Pdf)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Pdf)
     */
    private void assertCommandSuccess(String command, Pdf toAdd) {
        Model expectedModel = getModel();
        expectedModel.addPdf(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Pdf)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PdfListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Pdf)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PdfListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code PdfBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PdfBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
