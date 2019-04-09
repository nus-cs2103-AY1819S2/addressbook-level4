package quickdocs.logic.commands;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;

public class ViewStorageCommandTest {

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void init() {
        this.model = new ModelManager();
        this.commandHistory = new CommandHistory();
        model.addDirectory("TCM", new String[]{"root"});
        model.addDirectory("ECM", new String[]{"root"});
        model.addDirectory("Herbs", new String[]{"root", "TCM"});
        model.addMedicine("test", new String[]{"root"}, BigDecimal.valueOf(33));
        model.addMedicine("data", 59, new String[]{"root"}, BigDecimal.valueOf(97));
    }

    @Test
    public void viewRoot() {
        try {
            CommandResult commandResult =
                    new ViewStorageCommand(new String[] {"root"}).execute(model, commandHistory);
            StringBuilder sb = new StringBuilder();
            sb.append("Directory found at root\n");
            sb.append("List of sub-directories: \n");
            sb.append("- ECM\n");
            sb.append("- TCM\n");
            sb.append("List of Medicine under this directory: \n");
            sb.append("Medicine: data, Quantity: 59, Price: 97\n");
            sb.append("Medicine: test, Quantity: 0, Price: 33\n");
            Assert.assertEquals(sb.toString(), commandResult.getFeedbackToUser());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void viewEmptyDirectory() {
        try {
            CommandResult commandResult = new ViewStorageCommand(new String[] {"root", "TCM", "Herbs"})
                    .execute(model, commandHistory);
            Assert.assertEquals("Directory found at root\\TCM\\Herbs\nEmpty directory\n",
                    commandResult.getFeedbackToUser());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void viewValidMedicine() {
        try {
            CommandResult commandResult =
                    new ViewStorageCommand(new String[] {"root", "data"}).execute(model, commandHistory);
            Assert.assertEquals("Medicine found at root\\data\nMedicine: data, Quantity: 59, Price: 97\n",
                    commandResult.getFeedbackToUser());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void viewNonExistingDirectory() {
        try {
            CommandResult commandResult =
                    new ViewStorageCommand(new String[]{"root", "root2"}).execute(model, commandHistory);
            Assert.assertEquals("No directory/medicine found at the given path\n",
                    commandResult.getFeedbackToUser());
        } catch (Exception ex) {
            Assert.fail();
        }
    }
}
