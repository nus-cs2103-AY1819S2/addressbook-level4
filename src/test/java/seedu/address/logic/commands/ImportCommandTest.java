package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_DIR_SUCCESS;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_USAGE;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ImportCommandTest {

    private Album album = Album.getInstance();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CurrentEdit currentEdit = new CurrentEditManager();
    private CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void init() {
        album.clearAlbum();
    }

    @Test
    public void execute_successful_singleImport() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, false, false);
        assertCommandSuccess(new ImportCommand(false), model, commandHistory, expectedCommandResult,
                expectedModel, currentEdit);
    }

    @Test
    public void execute_successful_directoryImport() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_DIR_SUCCESS, false, false);
        assertCommandSuccess(new ImportCommand(true), model, commandHistory, expectedCommandResult,
                expectedModel, currentEdit);
    }
}