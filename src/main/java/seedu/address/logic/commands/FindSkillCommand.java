package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.skill.EmployeeSkillContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose skills match any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindSkillCommand extends FindCommand {

    public static final String FIND_SKILL_KEYWORD = "skill";

    public static final String MESSAGE_USAGE = FindCommand.COMMAND_WORD + " " + FIND_SKILL_KEYWORD
        + " [ARGUMENTS]" + "\n"
        + ": Find all employees with skills containing [ARGUMENTS]";


    private final EmployeeSkillContainsKeywordsPredicate predicate;

    public FindSkillCommand(EmployeeSkillContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredEmployeeList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_EMPLOYEES_LISTED_OVERVIEW, model.getFilteredEmployeeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindSkillCommand // instanceof handles nulls
            && predicate.equals(((FindSkillCommand) other).predicate)); // state check
    }
}
