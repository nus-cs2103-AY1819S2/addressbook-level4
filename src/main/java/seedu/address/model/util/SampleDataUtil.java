package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.CardFolder;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.card.Address;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Email;
import seedu.address.model.card.Question;
import seedu.address.model.hint.Hint;

/**
 * Contains utility methods for populating {@code CardFolder} with sample data.
 */
public class SampleDataUtil {
    public static Card[] getSampleCards() {
        return new Card[] {
            new Card(new Question("Alex Yeoh"), new Answer("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getHintSet("friends")),
            new Card(new Question("Bernice Yu"), new Answer("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getHintSet("colleagues", "friends")),
            new Card(new Question("Charlotte Oliveiro"), new Answer("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getHintSet("neighbours")),
            new Card(new Question("David Li"), new Answer("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getHintSet("family")),
            new Card(new Question("Irfan Ibrahim"), new Answer("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getHintSet("classmates")),
            new Card(new Question("Roy Balakrishnan"), new Answer("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getHintSet("colleagues"))
        };
    }

    public static ReadOnlyCardFolder getSampleCardFolder() {
        CardFolder sampleAb = new CardFolder();
        for (Card sampleCard : getSampleCards()) {
            sampleAb.addCard(sampleCard);
        }
        return sampleAb;
    }

    /**
     * Returns a hint set containing the list of strings given.
     */
    public static Set<Hint> getHintSet(String... strings) {
        return Arrays.stream(strings)
                .map(Hint::new)
                .collect(Collectors.toSet());
    }

}
