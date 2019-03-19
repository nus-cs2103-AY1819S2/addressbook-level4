package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Test;

import com.opencsv.CSVReader;

import seedu.address.commons.util.FileName;
import seedu.address.commons.util.csv.CsvWrapper;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.tag.Tag;

/**
 * Contains unit tests for {@code ExportCommand}.
 */
public class ExportCommandTest {

    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_csvFileDoesNotAlreadyExist_success() throws Exception {
        Path exportDirectoryPath = Paths.get("exported");
        if (Files.isDirectory(exportDirectoryPath) == false) {
            Files.createDirectory(exportDirectoryPath);
        }
        File filePath = File.createTempFile("tmp", ".csv", exportDirectoryPath.toFile());
        String fileName = filePath.toPath().getFileName().toString();
        String fileNameWithoutFileExtension = FilenameUtils.removeExtension(fileName);
        ExportCommand exportCommand = new ExportCommand(new FileName(fileNameWithoutFileExtension));
        filePath.delete();
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, fileNameWithoutFileExtension);

        assertCommandSuccess(exportCommand, model, commandHistory, expectedMessage, model);
        // Clean up temporary csv file for test case created.
        filePath.delete();
    }

    @Test
    public void execute_csvFileDoesNotAlreadyExist_throwsCommandException() throws Exception {
        Path exportDirectoryPath = Paths.get("exported");
        if (Files.isDirectory(exportDirectoryPath) == false) {
            Files.createDirectory(exportDirectoryPath);
        }
        File filePath = File.createTempFile("tmp", ".csv", exportDirectoryPath.toFile());
        String fileName = filePath.toPath().getFileName().toString();
        String fileNameWithoutFileExtension = FilenameUtils.removeExtension(fileName);
        ExportCommand exportCommand = new ExportCommand(new FileName(fileNameWithoutFileExtension));
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, fileNameWithoutFileExtension);

        assertCommandFailure(exportCommand, model, commandHistory, CsvWrapper.FILE_OPS_ERROR_MESSAGE
                + fileName
                + " already exists in \"exported\" directory.");
        // Clean up temporary csv file for test case created.
        filePath.delete();
    }

    @Test
    public void execute_csvFileInformationMatchesData_success() throws Exception {
        Path exportDirectoryPath = Paths.get("exported");
        if (Files.isDirectory(exportDirectoryPath) == false) {
            Files.createDirectory(exportDirectoryPath);
        }
        File filePath = File.createTempFile("tmp", ".csv", exportDirectoryPath.toFile());
        String fileName = filePath.toPath().getFileName().toString();
        String fileNameWithoutFileExtension = FilenameUtils.removeExtension(fileName);
        ExportCommand exportCommand = new ExportCommand(new FileName(fileNameWithoutFileExtension));
        filePath.delete();
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, fileNameWithoutFileExtension);

        assertCommandSuccess(exportCommand, model, commandHistory, expectedMessage, model);
        CSVReader reader = new CSVReader(new FileReader(filePath));
        String[] expectedData;
        String[] actualData;
        expectedData = CsvWrapper.getDefaultHeading();
        if ((actualData = reader.readNext()) == null) {
            filePath.delete();
            Assert.fail("The actual csv file contains less data than the expected amount of data.");
        }
        assertArrayEquals(expectedData, actualData);
        List<Medicine> currentGuiList = model.getFilteredMedicineList();
        Iterator iterator = currentGuiList.listIterator();
        while (iterator.hasNext()) {
            Medicine current = (Medicine) iterator.next();
            if (current.getBatches().size() == 0) {
                continue;
            }
            // Iterate through the batches of the current medicine
            Collection<Batch> batches = current.getBatches().values();
            Iterator batchIterator = batches.iterator();
            while (batchIterator.hasNext()) {
                if ((actualData = reader.readNext()) == null) {
                    filePath.delete();
                    Assert.fail("The actual csv file contains less data than the expected amount of data.");
                }
                Batch currentBatch = (Batch) batchIterator.next();
                final StringBuilder builder = new StringBuilder();
                String delimiter = "|";
                builder.append(current.getName())
                        .append(delimiter)
                        .append(currentBatch.getBatchNumber())
                        .append(delimiter)
                        .append(currentBatch.getQuantity())
                        .append(delimiter)
                        .append(currentBatch.getExpiry())
                        .append(delimiter)
                        .append(current.getCompany())
                        .append(delimiter);
                Iterator buildIterator = current.getTags().iterator();
                while (buildIterator.hasNext()) {
                    Tag currentTag = (Tag) buildIterator.next();
                    String formattedCurrentTagString = currentTag.toStringUpperCase();
                    builder.append(formattedCurrentTagString);
                    builder.append(' ');
                }
                expectedData = builder.toString().split("\\|");
                assertArrayEquals(expectedData, actualData);
            }

        }
        if (reader.readNext() != null) {
            filePath.delete();
            Assert.fail("The actual csv file contains more data than the expected amount of data.");
        }
    }
}
