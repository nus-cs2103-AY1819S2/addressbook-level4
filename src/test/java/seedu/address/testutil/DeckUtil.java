package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddDeckCommand;
import seedu.address.model.deck.Deck;

/**
 * A utility class for Deck.
 */
public class DeckUtil {

    /**
     * Returns a new deck command string for adding the {@code deck}.
     */
    public static String getAddDeckCommand(Deck deck) {
        return AddDeckCommand.COMMAND_WORD + " " + getDeckDetails(deck);
    }

    /**
     * Returns the part of command string for the given {@code deck}'s details.
     */
    public static String getDeckDetails(Deck deck) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + deck.getName().fullName + " ");
        return sb.toString();
    }

}
