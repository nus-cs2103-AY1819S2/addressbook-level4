package seedu.finance.logic.commands;
//@@author Jackimaru96

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.finance.logic.commands.ThemeCommand.MESSAGE_THEME_SUCCESS;

import org.junit.Test;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;

public class ThemeCommandTest {

    public static final String BLUE_STRING_1 = "blue";
    public static final String BLUE_STRING_2 = "BLUE";
    public static final String LIGHT_STRING_1 = "light";
    public static final String LIGHT_STRING_2 = "LIGHT";

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    private ThemeCommand blueThemeCommand1 = new ThemeCommand(BLUE_STRING_1);
    private ThemeCommand blueThemeCommand2 = new ThemeCommand(BLUE_STRING_2);
    private ThemeCommand lightThemeCommand1 = new ThemeCommand(LIGHT_STRING_1);
    private ThemeCommand lightThemeCommand2 = new ThemeCommand(LIGHT_STRING_2);


    @Test
    public void execute_theme_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_THEME_SUCCESS,
                "Blue"), false, false, true, "Blue");
        assertCommandSuccess(new ThemeCommand(BLUE_STRING_1), model, commandHistory,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void validTheme() {
        String darkTheme = "Dark";
        String lightTheme = "Light";
        String blueTheme = "Blue";
        String pinkTheme = "Pink";
        String yellowTheme = "Yellow";

        assertTrue(blueThemeCommand1.isValidTheme(darkTheme));
        assertTrue(blueThemeCommand1.isValidTheme(lightTheme));
        assertTrue(blueThemeCommand1.isValidTheme(blueTheme));
        assertTrue(blueThemeCommand1.isValidTheme(pinkTheme));

        assertFalse(blueThemeCommand1.isValidTheme(yellowTheme));
    }

    @Test
    public void equals() {
        // Same theme, different caps -> true
        assertTrue(blueThemeCommand1.equals(blueThemeCommand2));
        assertTrue(lightThemeCommand1.equals(lightThemeCommand2));

        // Different theme -> false
        assertFalse(blueThemeCommand1.equals(lightThemeCommand1));

        // Different command - > false
        ExitCommand exit = new ExitCommand();
        assertFalse(exit.equals(blueThemeCommand1));
    }
}
