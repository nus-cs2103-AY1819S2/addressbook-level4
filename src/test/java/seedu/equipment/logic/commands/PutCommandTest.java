package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.equipment.commons.core.GuiSettings;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.Model;
import seedu.equipment.model.ReadOnlyEquipmentManager;
import seedu.equipment.model.ReadOnlyUserPrefs;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.WorkListId;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.SerialNumber;
import seedu.equipment.model.tag.Tag;
import seedu.equipment.testutil.EquipmentBuilder;
import seedu.equipment.testutil.WorkListBuilder;

public class PutCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullWorkListIdNullSerialNumber_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new PutCommand(null, null);
    }

    @Test
    public void equals() {
        SerialNumber sr1 = new SerialNumber("A090909");
        SerialNumber sr2 = new SerialNumber("A080808");
        WorkListId id1 = new WorkListId("1");
        WorkListId id2 = new WorkListId("2");

        PutCommand putCommand1 = new PutCommand(id1, sr1);
        PutCommand putCommand2 = new PutCommand(id2, sr2);

        // same object -> returns true
        assertTrue(putCommand1.equals(putCommand1));

        // same values -> returns true
        PutCommand putCommand1Copy = new PutCommand(id1, sr1);
        assertTrue(putCommand1.equals(putCommand1Copy));

        // different types -> returns false
        assertFalse(putCommand1.equals(1));

        // null -> returns false
        assertFalse(putCommand1.equals(null));

        // different equipment -> returns false
        assertFalse(putCommand1.equals(putCommand2));
    }

}
