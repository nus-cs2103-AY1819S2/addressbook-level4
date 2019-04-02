package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.SkillsTag;

/**
 * Endorses a person in the recruiter platform.
 */
public class EndorseCommand extends Command {
    public static final String COMMAND_WORD = "endorse";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Endorses a candidate in the recruiting list. "
            + "Parameters: "
            + " endorse " + "(index) " + "n/(your name) "
            + "EXAMPLE: endorse 2 n/Warren Buffett";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Endorsed Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "You have already endorsed this person";

    private final Index index;
    private final String endorseName;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public EndorseCommand(Index index, String endorseName) {
        requireNonNull(index);
        this.index = index;
        this.endorseName = endorseName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getAddressBook().getPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Set<SkillsTag> personTags = new HashSet<>(personToEdit.getTags());
        if (personToEdit.isTagExist(endorseName)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        personTags.add(new SkillsTag(endorseName, "endorse"));

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getEducation(), personToEdit.getGpa(), personToEdit.getAddress(), personTags);


        model.setPerson(personToEdit, editedPerson);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName()));
    }


}
