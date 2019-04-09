package quickdocs.logic.commands;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;
import quickdocs.model.medicine.Medicine;

public class AlarmCommandTest {

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void init() {
        model = new ModelManager();
        commandHistory = new CommandHistory();
        model.addDirectory("TCM", new String[] {"root"});
        model.addMedicine("testMed", new String[] {"root", "TCM"}, BigDecimal.valueOf(37.0));
    }

    @Test
    public void setAlarmForValidDirectory_success() {
        try {
            CommandResult commandResult =
                    new AlarmCommand(new String[] {"root", "TCM"}, 20).execute(model, commandHistory);
            Assert.assertEquals(String.format(AlarmCommand.MESSAGE_SUCCESS, 20, "- TCM"),
                    commandResult.getFeedbackToUser());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void setAlarmForValidMedicine_success() {
        try {
            CommandResult commandResult =
                    new AlarmCommand(new String[] {"root", "TCM", "testMed"}, 20).execute(model, commandHistory);
            Assert.assertEquals(String.format(AlarmCommand.MESSAGE_SUCCESS, 20,
                    String.format(Medicine.TO_STRING, "testMed", 0, 37.0)), commandResult.getFeedbackToUser());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void setAlarmForNonExistingPath_throwCommandException() {
        try {
            CommandResult commandResult =
                    new AlarmCommand(new String[] {"root", "TCM", "medTest"}, 40).execute(model, commandHistory);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals("No medicine/directory found at the given path", ex.getMessage());
        } catch (Exception ex) {
            Assert.fail();
        }
    }
}
