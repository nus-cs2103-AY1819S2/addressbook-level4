package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;

/**
 * Adds a person to the address book.
 */
public class NewDeckCommand extends Command {

    public static final String COMMAND_WORD = "newdeck";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a new deck inside TopDeck. "
            + "Parameters: "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "MyDeck";

    public static final String MESSAGE_SUCCESS = "New deck added: %1$s";
    public static final String DEFAULT_DECK = "MyDeck";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + PREFIX_NAME + " " + DEFAULT_DECK;


    private final Deck toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public NewDeckCommand(Deck deck) {
        requireNonNull(deck);
        toAdd = deck;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.addDeck(toAdd);
        model.commitTopDeck();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NewDeckCommand // instanceof handles nulls
                && toAdd.equals(((NewDeckCommand) other).toAdd));
    }
}

