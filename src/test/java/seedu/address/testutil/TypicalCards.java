package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.CardFolder;
import seedu.address.model.card.Card;

/**
 * A utility class containing a list of {@code Card} objects to be used in tests.
 */
public class TypicalCards {

    public static final Card ALICE = new CardBuilder().withQuestion("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withAnswer("94351253").withScore("0/0").withTags("friends").build();
    public static final Card BENSON = new CardBuilder().withQuestion("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withAnswer("98765432").withScore("0/0")
            .withTags("owesMoney", "friends").build();
    public static final Card CARL = new CardBuilder().withQuestion("Carl Kurz").withAnswer("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withScore("0/0").build();
    public static final Card DANIEL = new CardBuilder().withQuestion("Daniel Meier").withAnswer("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withScore("0/0").withTags("friends")
            .build();
    public static final Card ELLE = new CardBuilder().withQuestion("Elle Meyer").withAnswer("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withScore("0/0").build();
    public static final Card FIONA = new CardBuilder().withQuestion("Fiona Kunz").withAnswer("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withScore("0/0").build();
    public static final Card GEORGE = new CardBuilder().withQuestion("George Best").withAnswer("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withScore("0/0").build();

    // Manually added
    public static final Card HOON = new CardBuilder().withQuestion("Hoon Meier").withAnswer("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withScore("0/0").build();
    public static final Card IDA = new CardBuilder().withQuestion("Ida Mueller").withAnswer("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withScore("0/0").build();

    // Manually added - Card's details found in {@code CommandTestUtil}
    public static final Card AMY = new CardBuilder().withQuestion(VALID_QUESTION_AMY).withAnswer(VALID_ANSWER_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withScore(VALID_SCORE_AMY).build();
    public static final Card BOB = new CardBuilder().withQuestion(VALID_QUESTION_BOB).withAnswer(VALID_ANSWER_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withScore(VALID_SCORE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCards() {} // prevents instantiation

    /**
     * Returns an {@code CardFolder} with all the typical cards.
     */
    public static CardFolder getTypicalCardFolder() {
        CardFolder ab = new CardFolder();
        for (Card card : getTypicalCards()) {
            ab.addCard(card);
        }
        return ab;
    }

    public static List<Card> getTypicalCards() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
