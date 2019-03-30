package seedu.knowitall.testutil;

import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_HINT;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_QUESTION;

import java.util.Set;

import seedu.knowitall.logic.commands.AddCommand;
import seedu.knowitall.logic.commands.EditCommand;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.model.card.Option;
import seedu.knowitall.model.hint.Hint;

/**
 * A utility class for Card.
 */
public class CardUtil {

    /**
     * Returns an add command string for adding the {@code card}.
     */
    public static String getAddCommand(Card card) {
        return AddCommand.COMMAND_WORD + " " + getCardDetails(card);
    }

    /**
     * Returns the part of command string for the given {@code card}'s details.
     */
    public static String getCardDetails(Card card) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_QUESTION + card.getQuestion().fullQuestion + " ");
        sb.append(PREFIX_ANSWER + card.getAnswer().fullAnswer + " ");
        card.getHints().stream().forEach(s -> sb.append(PREFIX_HINT + s.hintName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCardDescriptor}'s details.
     */
    public static String getEditCardDescriptorDetails(EditCommand.EditCardDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getQuestion().ifPresent(question -> sb.append(PREFIX_QUESTION)
                .append(question.fullQuestion).append(" "));
        descriptor.getAnswer().ifPresent(answer -> sb.append(PREFIX_ANSWER).append(answer.fullAnswer).append(" "));
        if (descriptor.getHints().isPresent()) {
            Set<Hint> hints = descriptor.getHints().get();
            if (hints.isEmpty()) {
                sb.append(PREFIX_HINT + " ");
            } else {
                hints.forEach(s -> sb.append(PREFIX_HINT).append(s.hintName).append(" "));
            }
        }
        if (descriptor.getOptions().isPresent()) {
            Set<Option> options = descriptor.getOptions().get();
            if (options.isEmpty()) {
                sb.append(PREFIX_OPTION + " ");
            } else {
                options.forEach(s -> sb.append(PREFIX_OPTION).append(s.optionValue).append(" "));
            }
        }
        return sb.toString();
    }
}
