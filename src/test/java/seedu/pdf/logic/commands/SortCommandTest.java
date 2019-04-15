package seedu.pdf.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SortCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_invalidNullComparator_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SortCommand(null);
    }
}
