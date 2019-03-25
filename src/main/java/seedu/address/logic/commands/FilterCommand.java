package seedu.address.logic.commands;

import java.util.Collection;
import java.util.function.Predicate;

import seedu.address.model.Model;

/**
 * Abstract class that represents a class with functionality to filter by a given predicate.
 */
public abstract class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String EMPTY_PARAMETERS = "Filter command must contain at least 1 parameter to filter by";

    /**
     * Method that filters a given list using the given predicate p.
     */
    protected abstract void filter(Model model, Collection<Predicate> predicates);
}
