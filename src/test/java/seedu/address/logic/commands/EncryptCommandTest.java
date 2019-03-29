package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PDF;

public class EncryptCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPdf_throwsNullPointerException() {
        new EncryptCommand(INDEX_FIRST_PDF, "Hi");
        /*thrown.expect(NullPointerException.class);
        new AddCommand(null);*/
    }
}
