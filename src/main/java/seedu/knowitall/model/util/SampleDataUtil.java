package seedu.knowitall.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.ReadOnlyCardFolder;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.model.card.Option;
import seedu.knowitall.model.card.Question;
import seedu.knowitall.model.card.Score;
import seedu.knowitall.model.hint.Hint;

/**
 * Contains utility methods for populating {@code CardFolder} with sample data.
 */
public class SampleDataUtil {
    public static Card[] getSampleCards() {
        return new Card[] {
            new Card(new Question("What is the best Software Engineering module?"), new Answer("CS2103T"),
                    new Score(5, 10),
                    Collections.emptySet(), getHintSet("Best taken with CS2101")),
            new Card(new Question("What is the best flashcard application?"), new Answer("Know-It-All"),
                    new Score(10, 60),
                    getOptionSet("Anki", "Physical Flashcards"), getHintSet("The answer is obvious")),
            new Card(new Question("_____ benefits memorisation."), new Answer("Repetition"),
                    new Score(0, 24),
                    Collections.emptySet(), Collections.emptySet()),
        };
    }

    public static ReadOnlyCardFolder getSampleCardFolder() {
        CardFolder sampleAb = new CardFolder(getSampleFolderName());
        sampleAb.setFolderScores(getSampleFolderScore());
        for (Card sampleCard : getSampleCards()) {
            sampleAb.addCard(sampleCard);
        }
        return sampleAb;
    }

    public static String getSampleFolderName() {
        return "Sample Folder";
    }

    public static String getSampleFolderFileName() {
        return "Sample Folder.json";
    }

    public static List<Double> getSampleFolderScore() {
        return new ArrayList<>(Arrays.asList(0.5, 0.6, 0.7));
    }

    /**
     * Returns a hint set containing the list of strings given.
     */
    public static Set<Hint> getHintSet(String... strings) {
        return Arrays.stream(strings)
                .map(Hint::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an option set containing the list of strings given.
     */
    public static Set<Option> getOptionSet(String... strings) {
        return Arrays.stream(strings)
                .map(Option::new)
                .collect(Collectors.toSet());
    }

}
