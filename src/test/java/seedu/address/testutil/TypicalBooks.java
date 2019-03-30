package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWMESSAGE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWMESSAGE_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWTITLE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWTITLE_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEXTBOOK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BookShelf;
import seedu.address.model.book.Book;
import seedu.address.model.book.Review;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalBooks {
    public static final Book BOOKTHIEF = new BookBuilder().withBookName("The Book Thief")
            .withAuthor("Markus Zusak")
            .withRating("7")
            .withTags("popular").build();
    public static final Book HUNGERGAME = new BookBuilder().withBookName("The Hunger Games")
            .withAuthor("Suzanne Collins")
            .withRating("6")
            .withTags("fantasy").build();
    public static final Book KITERUNNER = new BookBuilder()
            .withBookName("The Kite Runner")
            .withAuthor("Khaled Hosseini")
            .withRating("8").build();
    public static final Book LIFEPI = new BookBuilder().withBookName("Life of Pi")
            .withAuthor("Yann Martel")
            .withRating("9")
            .withTags("fantasy")
            .build();
    public static final Book LIFEWAO = new BookBuilder()
            .withBookName("The Brief Wondrous Life of Oscar Wao")
            .withAuthor("Junot Diaz")
            .withRating("4").build();
    public static final Book MIDDLESEX = new BookBuilder()
            .withBookName("Middlesex")
            .withAuthor("Jeffrey Eugenides")
            .withRating("6").build();
    public static final Book FIFTYSHADE = new BookBuilder()
            .withBookName("Fifty Shades of Grey")
            .withAuthor("Erika Leonard")
            .withRating("2")
            .build();
    public static final Book MOCKINGBIRD = new BookBuilder()
            .withBookName("To Kill a Mocking Bird")
            .withAuthor("Harper Lee")
            .withRating("10")
            .build();

    // Manually added
    public static final Book SECRETLIFE = new BookBuilder().withBookName("The Secret Life of Bees")
            .withAuthor("Sue Monk Kidd")
            .withRating("3")
            .build();
    public static final Book TWILIGHT = new BookBuilder()
            .withBookName("Twilight")
            .withAuthor("Stephenie Meyer")
            .withRating("2").build();

    // Manually added - Book's details found in {@code CommandTestUtil}
    public static final Book ALI = new BookBuilder().withBookName(VALID_BOOKNAME_ALICE).withAuthor(VALID_AUTHOR_ALICE)
            .withRating(VALID_RATING_ALICE).withTags(VALID_TAG_FANTASY).build();
    public static final Book CS = new BookBuilder().withBookName(VALID_BOOKNAME_CS).withAuthor(VALID_AUTHOR_CS)
            .withRating(VALID_RATING_CS).withTags(VALID_TAG_TEXTBOOK, VALID_TAG_FANTASY)
            .build();
    public static final Review ALICE_REVIEW = new ReviewBuilder().withReviewTitle(VALID_REVIEWTITLE_ALICE)
            .withBookName(VALID_BOOKNAME_ALICE).withReviewMessage(VALID_REVIEWMESSAGE_ALICE).build();
    public static final Review CS_REVIEW = new ReviewBuilder().withReviewTitle(VALID_REVIEWTITLE_CS)
            .withBookName(VALID_BOOKNAME_CS).withReviewMessage(VALID_REVIEWMESSAGE_CS).build();

    //Typical Reviews
    public static final Review BOOKTHIEF_REVIEW = new ReviewBuilder()
            .withReviewTitle("A Heartbreaking Story")
            .withBookName("The Book Thief")
            .withReviewMessage("I cried a lot when reading this book")
            .build();
    public static final Review HUNGERGAME_REVIEW = new ReviewBuilder()
            .withReviewTitle("Irresistible to Put Down")
            .withBookName("Hunger Game")
            .withReviewMessage("An amazing book series. But the plot is unreasonable sometimes. But there are a "
                    + "number of things doesn't make sense.")
            .build();

    public static final String KEYWORD_MATCHING_LIFE = "life"; // A keyword that matches life
    public static final String KEYWORD_MATCHING_PRIDE = "Pride"; // A keyword that matches Pride
    public static final String KEYWORD_MATCHING_ZUSAK = "Zusak"; // A keyword that matches Zusak
    public static final String KEYWORD_MATCHING_COLLINS = "Collins"; //A keyword that matches Collins
    public static final String KEYWORD_MATCHING_SIX = "6"; // A keyword that matches 6
    public static final String KEYWORD_MATCHING_FANTASY = "fantasy"; // A keyword that matches fantasy


    private TypicalBooks() {} // prevents instantiation

    /**
     * Returns an {@code BookShelf} with all the typical books.
     */
    //TODO: add reviews into typical book shelf
    public static BookShelf getTypicalBookShelf() {
        BookShelf bs = new BookShelf();
        for (Book book : getTypicalBooks()) {
            bs.addBook(book);
        }
        return bs;
    }

    public static List<Book> getTypicalBooks() {
        return new ArrayList<>(Arrays.asList(BOOKTHIEF, HUNGERGAME, KITERUNNER, LIFEPI, LIFEWAO,
                MIDDLESEX, FIFTYSHADE, MOCKINGBIRD));
    }

    public static List<Review> getTypicalReviews() {
        return new ArrayList<>(Arrays.asList(BOOKTHIEF_REVIEW, HUNGERGAME_REVIEW));
    }
}
