package seedu.address.logic.commands;

//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PDF;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;

public class EncryptCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPdf_throwsNullPointerException() {
        /*thrown.expect(NullPointerException.class);
        new AddCommand(null);*/
    }
}
