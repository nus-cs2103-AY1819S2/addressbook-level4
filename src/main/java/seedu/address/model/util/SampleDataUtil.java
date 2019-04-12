package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewTitle;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code BookShelf} with sample data.
 */
public class SampleDataUtil {
    public static Book[] getSampleBooks() {
        return new Book[] {
            new Book(new BookName("Madame Bovary"), new Author("Gustave Flaubert"), new Rating("8"),
                getTagSet("classic")),
            new Book(new BookName("Pride and Prejudice"), new Author("Jane Austen"), new Rating("6"),
                getTagSet("novel", "romantic")),
            new Book(new BookName("Green Eggs and Ham"), new Author("Dr Seuss"), new Rating("7"),
                getTagSet("children")),
            new Book(new BookName("The Adventures of Tintin"), new Author("Georges Remi"), new Rating("5"),
                getTagSet("adventure")),
            new Book(new BookName("Gone Girl"), new Author("Gillian Flynn"), new Rating("4"),
                getTagSet("thriller")),
            new Book(new BookName("One Hundred Years of Solitude"), new Author("Garcia Marquez"), new Rating("9"),
                getTagSet("fantasy")),
            new Book(new BookName("The Second Sex"), new Author("Beauvoir"), new Rating("9"),
                getTagSet()),
            new Book(new BookName("The Master and Margaret"), new Author("Mikhail Bulgakov"), new Rating("8"),
                getTagSet()),
            new Book(new BookName("Sense and Sensibility"), new Author("Jane Austen"), new Rating("7"),
                getTagSet("novel", "romantic")),
        };
    }

    public static Review[] getSampleReviews() {
        return new Review[] {
            new Review(new ReviewTitle("A Childhood Favorite"), new BookName("Green Eggs and Ham"),
                    "This is my favorite book when I was a kid. Read it again with my younger brother. "),
            new Review(new ReviewTitle("Magical Realism"), new BookName("One Hundred Years of Solitude"),
                    "One of the best books I've ever read. "),
            new Review(new ReviewTitle("Desperate Housewife"), new BookName("Madame Bovary"),
                    "Poor Emma Bovary. She will never escape the tyranny of her desires, never avoid the "
                            + "anguish into which her romantic conceits deliver her, never claim the oblivion she "
                            + "sought from what is perhaps the most excruciating slow suicide ever written."),
            new Review(new ReviewTitle("Belief Systems"), new BookName("Pride and Prejudice"),
                    "Most of the characters believe that nothing matters more than social class. Characters "
                            + "threaten, criticize, ostracize, flatter, marry, venerate and despise other characters, "
                            + "all for the sake of maintaining or elevating their status in society."),
            new Review(new ReviewTitle("Book of a Lifetime"), new BookName("Madame Bovary"),
                    "What leaves me reeling with each re-reading is the use of language."
                            + " There can be no doubt as to the reason for Flaubert's brain popping at"
                            + " the top of the stairs when he was 58. He broke it scouring for perfect sentences,"
                            + " words, le mot juste.")
        };
    }

    public static ReadOnlyBookShelf getSampleBookShelf() {
        BookShelf sampleBs = new BookShelf();

        for (Book sampleBook : getSampleBooks()) {
            sampleBs.addBook(sampleBook);
        }

        for (Review sampleReview : getSampleReviews()) {
            sampleBs.addReview(sampleReview);
        }
        return sampleBs;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
