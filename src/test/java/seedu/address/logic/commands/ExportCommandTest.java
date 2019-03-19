package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import seedu.address.commons.util.FileName;
import seedu.address.commons.util.csv.CsvWrapper;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

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
}
