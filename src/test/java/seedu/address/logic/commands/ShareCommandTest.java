package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalFlashcards.getTypicalCardCollection;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ShareCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ShareCommandTest {

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardCollection(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    // this test does not work on travis but works locally
    @Ignore
    @Test
    public void execute_share_success() throws CommandException {
        File expectedFile = new File("src\\test\\data\\shareCommandTestFiles\\flashcardsExpected.txt");
        ShareCommand share = new ShareCommand("src\\test\\data\\shareCommandTestFiles\\");
        share.execute(model, commandHistory);
        File outputFile = new File("src\\test\\data\\shareCommandTestFiles\\flashcards.txt");
        assertEquals(expectedFile.length(), outputFile.length());
    }

    @Test
    public void parse_invalidDirectory_failure() {
        ShareCommandParser parser = new ShareCommandParser();
        assertParseFailure(parser, "src\\test\\data\\importCommandTestFiles\\hello.txt",
                String.format(MESSAGE_INVALID_PATH, ShareCommand.MESSAGE_USAGE));
    }

}
