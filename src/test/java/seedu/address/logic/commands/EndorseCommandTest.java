package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.SkillsTag;
/**
 * Contains integration tests and unit tests for EndorseCommand.
 */
public class EndorseCommandTest {

    private static final int CLEAR_PROCESS = 1;
    private static final int ENDORSE_PROCESS = 0;
    private static final String VALID_ENDORSE_NAME = "John Smith";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_endorsePerson_success() {

        Person samplePerson = model.getFilteredPersonList().get(0);
        SkillsTag sampleEndorseTag = new SkillsTag(VALID_ENDORSE_NAME, "endorse");
        Set<SkillsTag> samplePersonTags = new HashSet<>(samplePerson.getTags());
        EndorseCommand endorseCommand = new EndorseCommand(ENDORSE_PROCESS, INDEX_FIRST_PERSON, VALID_ENDORSE_NAME);

        samplePersonTags.add(sampleEndorseTag);
        Person editedPerson = new Person(samplePerson.getName(),
                samplePerson.getPhone(), samplePerson.getEmail(),
                samplePerson.getEducation(), samplePerson.getGpa(),
                samplePerson.getDegree(), samplePerson.getAddress(), samplePersonTags);
        String expectedMessage = String.format(EndorseCommand.MESSAGE_ENDORSE_PERSON_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(endorseCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_clearEndorsePerson_success() throws CommandException {

        Person samplePerson = model.getFilteredPersonList().get(0);
        SkillsTag sampleEndorseTag = new SkillsTag(VALID_ENDORSE_NAME, "endorse");
        Set<SkillsTag> samplePersonTags = new HashSet<>(samplePerson.getTags());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        EndorseCommand endorseCommand = new EndorseCommand(ENDORSE_PROCESS, INDEX_FIRST_PERSON, VALID_ENDORSE_NAME);
        endorseCommand.execute(model, new CommandHistory());

        endorseCommand.execute(expectedModel, new CommandHistory());
        EndorseCommand clearEndorseCommand = new EndorseCommand(CLEAR_PROCESS, INDEX_FIRST_PERSON, VALID_ENDORSE_NAME);

        samplePersonTags.remove(sampleEndorseTag);
        Person editedPerson = new Person(samplePerson.getName(),
                samplePerson.getPhone(), samplePerson.getEmail(),
                samplePerson.getEducation(), samplePerson.getGpa(),
                samplePerson.getDegree(), samplePerson.getAddress(), samplePersonTags);
        String expectedMessage = String.format(EndorseCommand.MESSAGE_REMOVE_ENDORSE_SUCESS, editedPerson.getName());


        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(clearEndorseCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_endorseDuplicate_failure() throws CommandException {

        EndorseCommand endorseCommand = new EndorseCommand(ENDORSE_PROCESS, INDEX_FIRST_PERSON, VALID_ENDORSE_NAME);
        endorseCommand.execute(model, new CommandHistory());
        String expectedMessage = String.format(EndorseCommand.MESSAGE_DUPLICATE_PERSON);

        assertCommandFailure(endorseCommand, model, commandHistory, expectedMessage);

    }

    @Test
    public void execute_clearEmptyEndorsement_failure() {
        EndorseCommand clearCommand = new EndorseCommand(CLEAR_PROCESS, INDEX_FIRST_PERSON, VALID_ENDORSE_NAME);
        String expectedMessage = String.format(EndorseCommand.MESSAGE_MISSING_ENDORSEMENT);

        assertCommandFailure(clearCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_endorseIndexOutBounds_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EndorseCommand endorseCommand = new EndorseCommand(ENDORSE_PROCESS, outOfBoundIndex, VALID_ENDORSE_NAME);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

        assertCommandFailure(endorseCommand, model, commandHistory, expectedMessage);
    }

}
