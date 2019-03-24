package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPdfs.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PdfBook;
import seedu.address.model.ReadOnlyPdfBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.pdf.Pdf;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPdf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_pdfAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPdfAdded modelStub = new ModelStubAcceptingPdfAdded();
        Pdf validPdf = SAMPLE_PDF_4;

        CommandResult commandResult = new AddCommand(validPdf).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPdf), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPdf), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePdf_throwsCommandException() throws Exception {
        Pdf validPdf = SAMPLE_PDF_4;
        AddCommand addCommand = new AddCommand(validPdf);
        ModelStub modelStub = new ModelStubWithPdf(validPdf);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PDF);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Pdf samplePdf6 = SAMPLE_PDF_6;
        Pdf samplePdf7 = SAMPLE_PDF_7;
        AddCommand addCommandSamplePdf6 = new AddCommand(samplePdf6);
        AddCommand addCommandSamplePdf7 = new AddCommand(samplePdf7);

        // same object -> returns true
        assertTrue(addCommandSamplePdf6.equals(addCommandSamplePdf6));

        // same values -> returns true
        AddCommand addDCommandCopy = new AddCommand(samplePdf6);
        assertTrue(addCommandSamplePdf6.equals(addDCommandCopy));

        // different types -> returns false
        assertFalse(addCommandSamplePdf6.equals(1));

        // null -> returns false
        assertFalse(addCommandSamplePdf6.equals(null));

        // different pdf -> returns false
        assertFalse(addCommandSamplePdf6.equals(addCommandSamplePdf7));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getPdfBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPdfBookFilePath(Path pdfBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPdf(Pdf pdf) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPdfBook(ReadOnlyPdfBook pdfBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPdfBook getPdfBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPdf(Pdf pdf) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePdf(Pdf target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPdf(Pdf target, Pdf editedPdf) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Pdf> getFilteredPdfList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPdfList(Predicate<Pdf> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoPdfBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoPdfBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoPdfBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoPdfBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitPdfBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Pdf> selectedPdfProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Pdf getSelectedPdf() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPdf(Pdf pdf) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single pdf.
     */
    private class ModelStubWithPdf extends ModelStub {
        private final Pdf pdf;

        ModelStubWithPdf(Pdf pdf) {
            requireNonNull(pdf);
            this.pdf = pdf;
        }

        @Override
        public boolean hasPdf(Pdf pdf) {
            requireNonNull(pdf);
            return this.pdf.isSamePdf(pdf);
        }
    }

    /**
     * A Model stub that always accept the pdf being added.
     */
    private class ModelStubAcceptingPdfAdded extends ModelStub {
        final ArrayList<Pdf> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPdf(Pdf pdf) {
            requireNonNull(pdf);
            return personsAdded.stream().anyMatch(pdf::isSamePdf);
        }

        @Override
        public void addPdf(Pdf pdf) {
            requireNonNull(pdf);
            personsAdded.add(pdf);
        }

        @Override
        public void commitPdfBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyPdfBook getPdfBook() {
            return new PdfBook();
        }
    }

}
