package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEXTBOOK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BookShelf;
import seedu.address.model.book.Book;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalBooks {

    public static final Book BOOKD = new BookBuilder().withName("bookD")
            .withAuthor("d")
            .withRating("5")
            .withTags("fantasy").build();
    public static final Book BOOKE = new BookBuilder().withName("bookE")
            .withAuthor("e")
            .withRating("4")
            .withTags("textbook").build();

    // Manually added
    public static final Book BOOKA = new BookBuilder().withName("bookA").withAuthor("a")
            .withRating("1").build();
    public static final Book BOOKB = new BookBuilder().withName("bookB").withAuthor("b")
            .withRating("2").build();

    // Manually added - Book's details found in {@code CommandTestUtil}
    public static final Book ALI = new BookBuilder().withName(VALID_BOOKNAME_ALICE).withAuthor(VALID_AUTHOR_ALICE)
            .withRating(VALID_RATING_ALICE).withTags(VALID_TAG_FANTASY).build();
    public static final Book CS = new BookBuilder().withName(VALID_BOOKNAME_CS).withAuthor(VALID_AUTHOR_CS)
            .withRating(VALID_RATING_CS).withTags(VALID_TAG_TEXTBOOK, VALID_TAG_FANTASY)
            .build();

    public static final String KEYWORD_MATCHING_BOOKA = "bookA"; // A keyword that matches BOOKA

    private TypicalBooks() {} // prevents instantiation

    /**
     * Returns an {@code BookShelf} with all the typical books.
     */
    public static BookShelf getTypicalAddressBook() {
        BookShelf ab = new BookShelf();
        for (Book book : getTypicalBooks()) {
            ab.addBook(book);
        }
        return ab;
    }

    public static List<Book> getTypicalBooks() {
        return new ArrayList<>(Arrays.asList(ALI, CS));
    }
}
