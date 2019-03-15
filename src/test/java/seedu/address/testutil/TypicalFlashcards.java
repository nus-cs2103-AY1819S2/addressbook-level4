package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BACKFACE_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BACKFACE_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INDONESIAN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.CardCollection;
import seedu.address.model.flashcard.Flashcard;

/**
 * A utility class containing a list of {@code Flashcard} objects to be used in tests.
 */
public class TypicalFlashcards {

    public static final Flashcard HELLO = new FlashcardBuilder().withFrontFace("Hello")
        .withBackFace("Halo").withTags("indonesian").withStatistics(9, 10).build();
    public static final Flashcard HOLA = new FlashcardBuilder().withFrontFace("Hola")
        .withBackFace("Haha").withTags("spanish").withStatistics(4, 7).build();
    public static final Flashcard EAT = new FlashcardBuilder().withFrontFace("Eat")
        .withBackFace("吃").withTags("chinese").build();
    public static final Flashcard NEWTON = new FlashcardBuilder().withFrontFace("Newton's 3rd Law")
        .withBackFace("idk").withTags("alevel", "physics").withStatistics(0, 10).build();
    public static final Flashcard EMAIL = new FlashcardBuilder().withFrontFace("Robin's email")
        .withBackFace("robincyu96@gmail.com").withStatistics(100, 100).build();

    // Manually added - Flashcard's details found in {@code CommandTestUtil}
    public static final Flashcard GOOD = new FlashcardBuilder().withFrontFace(VALID_FRONTFACE_GOOD)
        .withBackFace(VALID_BACKFACE_GOOD).withTags(VALID_TAG_INDONESIAN).build();
    public static final Flashcard HITBAG = new FlashcardBuilder().withFrontFace(VALID_FRONTFACE_HITBAG)
        .withBackFace(VALID_BACKFACE_HITBAG).withTags(VALID_TAG_CHINESE).build();

    public static final String KEYWORD_MATCHING_GOOD = "Good"; // A keyword that matches Good
    public static final String KEYWORD_MATCHING_HELLO = "Hello"; // A keyword that matches Hello

    private TypicalFlashcards() {
    } // prevents instantiation

    /**
     * Returns an {@code CardCollection} with all the typical flashcards.
     */
    public static CardCollection getTypicalCardCollection() {
        CardCollection ab = new CardCollection();
        for (Flashcard flashcard : getTypicalFlashcards()) {
            ab.addFlashcard(flashcard);
        }
        return ab;
    }

    public static List<Flashcard> getTypicalFlashcards() {
        return new ArrayList<>(Arrays.asList(HELLO, HOLA, EAT, NEWTON, EMAIL));
    }
}
