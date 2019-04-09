package quickdocs.logic.commands;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;
import quickdocs.model.medicine.Directory;
import quickdocs.model.medicine.MedicineManager;

public class AddDirectoryCommandTest {

    private static final String[] typicalDirectoryName = new String[] {"TCM", "Herbs", "Cheap", "Outdated"};
    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void init() {
        this.model = new ModelManager();
        this.commandHistory = new CommandHistory();
    }

    @Test
    public void addValidDirectoryToValidPath_success() {
        try {
            CommandResult commandResult =
                    new AddDirectoryCommand(new String[] {"root"}, typicalDirectoryName[0])
                            .execute(model, commandHistory);
            Assert.assertEquals(
                    commandResult.getFeedbackToUser(),
                    String.format("New directory with name \"%1$s\" added to %2$s\n", typicalDirectoryName[0], "root"));
            Assert.assertTrue(model.findDirectory(new String[] {"root", typicalDirectoryName[0]}).isPresent());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void addValidDirectoryToInvalidPath_throwCommandException() {
        try {
            CommandResult commandResult =
                    new AddDirectoryCommand(new String[] {"rooot"}, typicalDirectoryName[1])
                            .execute(model, commandHistory);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals(MedicineManager.ERROR_MESSAGE_NO_DIRECTORY_FOUND , ex.getMessage());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void addValidDirectoryToNonExistingDirectory_returnsCommandException() {
        try {
            CommandResult commandResult =
                    new AddDirectoryCommand(new String[] {"root", typicalDirectoryName[0]}, typicalDirectoryName[1])
                        .execute(model, commandHistory);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals(MedicineManager.ERROR_MESSAGE_NO_DIRECTORY_FOUND, ex.getMessage());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void addDuplicateDirectory_returnsCommandException() {
        try {
            addValidDirectoryToValidPath_success();
            CommandResult commandResult =
                    new AddDirectoryCommand(new String[] {"root"}, typicalDirectoryName[0])
                            .execute(model, commandHistory);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals(Directory.ERROR_MESSAGE_MEDICINE_ALREADY_EXISTS_UNDER_SAME_DIRECTORY, ex.getMessage());
        } catch (Exception ex) {
            Assert.fail();
        }
    }
}
