package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATH;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_IMPORT_PARTIAL_SUCCESS_INFO;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_IMPORT_SUCCESS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalFlashcards.getTypicalCardCollection;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ImportCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains unit / integration tests for ImportCommand.
 */
public class ImportCommandTest {

    private Model model;
    private CommandHistory commandHistory;
    private final String validFile = "flashcards.txt";
    private final String corruptedFile = "flashcardsCorrupted.txt";
    private final String duplicateCardFile = "flashcardsDuplicate.txt";

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardCollection(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @Ignore
    @Test
    public void execute_importCommand_allCardsSuccessfullyImported() throws CommandException {
        File validFileToTest = new File("src\\test\\data\\importCommandTestFiles\\".concat(validFile));

        // all flashcards successfully imported
        ImportCommand command = new ImportCommand(validFileToTest);
        assertEquals(new CommandResult("ALL" + MESSAGE_IMPORT_SUCCESS),
                command.execute(model, commandHistory));
    }

    @Ignore
    @Test
    public void execute_importCommand_oneCardCorrupted() throws CommandException {
        File corruptedFileToTest = new File("src\\test\\data\\importCommandTestFiles\\".concat(corruptedFile));

        // 4 cards successfully imported, 1 card unsuccessful
        ImportCommand command = new ImportCommand(corruptedFileToTest);
        assertEquals(new CommandResult(String.format("4 out of 5" + MESSAGE_IMPORT_SUCCESS + " %s",
                corruptedFileToTest.getAbsolutePath() + "\n" + MESSAGE_IMPORT_PARTIAL_SUCCESS_INFO)),
                command.execute(model, commandHistory));
    }

    @Ignore
    @Test
    public void execute_importCommand_oneCardAlreadyExists() throws CommandException {
        File corruptedFileToTest = new File("src\\test\\data\\importCommandTestFiles\\".concat(duplicateCardFile));

        // 4 cards successfully imported, 1 card unsuccessful
        ImportCommand command = new ImportCommand(corruptedFileToTest);
        assertEquals(new CommandResult(String.format("4 out of 5" + MESSAGE_IMPORT_SUCCESS + " %s",
                corruptedFileToTest.getAbsolutePath() + "\n" + MESSAGE_IMPORT_PARTIAL_SUCCESS_INFO)),
                command.execute(model, commandHistory));
    }

    @Test
    public void parse_invalidPath_failure() {
        ImportCommandParser parser = new ImportCommandParser();
        assertParseFailure(parser, "src\\test\\data\\importCommandTestFiles\\hello.txt",
                String.format(MESSAGE_INVALID_PATH, ImportCommand.MESSAGE_USAGE));

    }
}
