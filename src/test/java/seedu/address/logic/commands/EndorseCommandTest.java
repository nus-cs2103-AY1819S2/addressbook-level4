package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
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

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private static final String VALID_ENDORSE_NAME = "John Smith";
    private static final String INVALID_ENDORSE_NAME = null;
    private static final int ENDORSE_PROCESS = 0;
    private static final int CLEAR_PROCESS = 1;


    @Test
    public void execute_endorsePerson_success(){
        String expectedMessage = String.format("Endorsed correct person with name");
        Person samplePerson = model.getFilteredPersonList().get(0);
        SkillsTag sampleEndorseTag = new SkillsTag(VALID_ENDORSE_NAME, "endorse");
        Set<SkillsTag> samplePersonTags = samplePerson.getTags();
        EndorseCommand endorseCommand = new EndorseCommand(ENDORSE_PROCESS, INDEX_FIRST_PERSON, VALID_ENDORSE_NAME);

        samplePersonTags.add(sampleEndorseTag);
        Person editedPerson = new Person(samplePerson.getName(),
                samplePerson.getPhone(), samplePerson.getEmail(),
                samplePerson.getEducation(), samplePerson.getGpa(),
                samplePerson.getAddress(), samplePersonTags);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(endorseCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
