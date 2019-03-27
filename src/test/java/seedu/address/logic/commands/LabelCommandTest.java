package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.LabelCommand.DEFAULT_FILENAME;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileName;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicine.Medicine;

public class LabelCommandTest {

    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private FileName fileName = new FileName(DEFAULT_FILENAME);

    @Test
    public void execute_labelMedicineAtIndexOneDefaultFileNameUnfilteredList_success()
            throws IOException, CommandException {
        Medicine medicineToLabel = model.getFilteredMedicineList().get(0);

        LabelCommand labelCommand = new LabelCommand(INDEX_FIRST_MEDICINE, new FileName(DEFAULT_FILENAME));
        labelCommand.execute(model, commandHistory);
        File printedFile = new File("./PDF/" + "to_print.pdf");
        String actualMessage = readFromPdf(printedFile);
        String information = getMedicineInformationToString(medicineToLabel);

        String expectedMessage = information.replaceAll("\r", "").replaceAll("\n", "");

        assertEquals(expectedMessage, actualMessage);

        printedFile.delete();
    }

    @Test
    public void execute_labelMedicineAtIndexTwoDefaultFileNameUnfilteredList_success()
            throws IOException, CommandException {

        Medicine medicineToLabel = model.getFilteredMedicineList().get(1);

        LabelCommand labelCommand = new LabelCommand(INDEX_SECOND_MEDICINE, new FileName("secondMedicine"));
        labelCommand.execute(model, commandHistory);
        File printedFile = new File("./PDF/" + "secondMedicine.pdf");
        String actualMessage = readFromPdf(printedFile);
        String information = getMedicineInformationToString(medicineToLabel);

        String expectedMessage = information.replaceAll("\r", "").replaceAll("\n", "");

        assertEquals(expectedMessage, actualMessage);

        printedFile.delete();
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);
        LabelCommand labelCommand = new LabelCommand(outOfBoundIndex, fileName);

        assertCommandFailure(labelCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    /**
     * Helper function to read content from {@code file} file and output it as a String.
     */
    private String readFromPdf(File file) throws IOException {

        String pdfFileInText;
        try (PDDocument document = PDDocument.load(file)) {

            document.getClass();

            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            PDFTextStripper tStripper = new PDFTextStripper();

            pdfFileInText = tStripper.getText(document);
        }

        return pdfFileInText.replaceAll("\r", "").replaceAll("\n", "");
    }

    private String getMedicineInformationToString(Medicine medicineToPrint) {
        String medicineName = medicineToPrint.getName().toString();
        String medicineExpiry = medicineToPrint.getNextExpiry().toString();
        String medicineCompany = medicineToPrint.getCompany().toString();
        String medicineTags = medicineToPrint.getTags().toString();

        String information = (medicineName + "\n" + medicineCompany + "\n"
                + medicineExpiry + "\n" + medicineTags + "\n");
        information = information.replaceAll("\r", "").replaceAll("\n", "");
        return information;
    }
}
