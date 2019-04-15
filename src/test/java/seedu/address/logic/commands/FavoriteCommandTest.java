package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NameMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

import java.util.Arrays;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithFavorites;

public class FavoriteCommandTest {
    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithFavorites(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addFavorites(TypicalPersons.BENSON);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new FavoriteCommand(new NameMatchesPredicate(Arrays.asList("Benson Meier")),
                        new NameContainsKeywordsPredicate(Arrays.asList("Benson Meier"))), model, commandHistory,
                String.format(FavoriteCommand.MESSAGE_SUCCESS, TypicalPersons.BENSON), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        assertCommandFailureFavorite(new FavoriteCommand(new NameMatchesPredicate(Arrays.asList("Alice Pauline")),
                        new NameContainsKeywordsPredicate(Arrays.asList("Alice Pauline"))), model, commandHistory,
                FavoriteCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
