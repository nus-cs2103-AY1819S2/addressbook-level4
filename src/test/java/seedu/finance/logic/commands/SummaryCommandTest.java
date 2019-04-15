package seedu.finance.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.finance.testutil.Assert;

/**
 * Contains integration tests (interaction with Model) and unit test for SummaryCommand
 */
public class SummaryCommandTest {

    @Test
    public void constructorValidParameters() {
        SummaryCommand summaryCommand = new SummaryCommand(6, "d");
        assertTrue(summaryCommand.equals(new SummaryCommand(6, "d")));

        summaryCommand = new SummaryCommand();
        assertTrue(summaryCommand.equals(new SummaryCommand(7, "d")));

        summaryCommand = new SummaryCommand(8, "m");
        assertTrue(summaryCommand.equals(new SummaryCommand(8, "m")));
    }

    @Test
    public void constructorInvalidParameters() {
        Assert.assertThrows(NullPointerException.class, () -> new SummaryCommand(1, null));
        Assert.assertThrows(IllegalArgumentException.class, () -> new SummaryCommand(1, "asd"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new SummaryCommand(0, "d"));
    }

}
