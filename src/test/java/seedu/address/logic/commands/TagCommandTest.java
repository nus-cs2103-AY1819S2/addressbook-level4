package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.TAG_VALID_LECTURE;
import static seedu.address.testutil.TypicalPdfs.getTypicalPdfBook;

import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

public class TagCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_invalidIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        new TagCommand(Index.fromZeroBased(-1), new HashSet<>(), true);
    }

    @Test
    public void constructor_nullIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(NullPointerException.class);
        new TagCommand(null, new HashSet<>(), true);
    }

    @Test
    public void constructor_nullTags_throwsIndexOutOfBoundsException() {
        thrown.expect(NullPointerException.class);
        new TagCommand(Index.fromOneBased(1), null, true);
    }

    @Test
    public void execute_valid_successful() {

        HashSet<Tag> validTags = new HashSet<>();
        validTags.add(new Tag(TAG_VALID_LECTURE));

        // add valid tag should execute successfully
        try {
            TagCommand standardCommand = new TagCommand(Index.fromOneBased(1), validTags, true);
            standardCommand.execute(this.model, commandHistory);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws CommandException {
        HashSet<Tag> validTags = new HashSet<>();
        validTags.add(new Tag(TAG_VALID_LECTURE));

        thrown.expect(CommandException.class);
        TagCommand standardCommand = new TagCommand(Index.fromOneBased(20), validTags, true);
        standardCommand.execute(this.model, commandHistory);
    }

    @Test
    public void execute_invalidIndex_throwsInvalidException() throws CommandException {
        HashSet<Tag> validTags = new HashSet<>();
        validTags.add(new Tag(TAG_VALID_LECTURE));

        thrown.expect(IndexOutOfBoundsException.class);
        TagCommand standardCommand = new TagCommand(Index.fromOneBased(0), validTags, true);
        standardCommand.execute(this.model, commandHistory);
    }

    @Test
    public void equals() {
        final TagCommand standardCommand = new TagCommand(Index.fromOneBased(1), new HashSet<>(), true);

        // same value -> returns true
        TagCommand standardCommandCopy = new TagCommand(Index.fromOneBased(1), new HashSet<>(), true);
        assertTrue(standardCommand.equals(standardCommandCopy));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new TagCommand(Index.fromOneBased(2), new HashSet<>(), true)));
    }


}
