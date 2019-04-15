package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEXTBOOK;
import static seedu.address.testutil.TypicalBooks.ALI;
import static seedu.address.testutil.TypicalBooks.CS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.BookBuilder;

public class BookTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Book book = new BookBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        book.getTags().remove(0);
    }

    @Test
    public void isSameBook() {
        // same object -> returns true
        assertTrue(ALI.isSameBook(ALI));

        // null -> returns false
        assertFalse(ALI.isSameBook(null));

        // different author -> returns false
        Book editedAlice = new BookBuilder(CS).withAuthor(VALID_AUTHOR_CS).build();
        assertFalse(ALI.isSameBook(editedAlice));

        // different name -> returns false
        editedAlice = new BookBuilder(ALI).withBookName(VALID_BOOKNAME_CS).build();
        assertFalse(ALI.isSameBook(editedAlice));

        // same name, same author, different attributes -> returns true
        editedAlice = new BookBuilder(ALI).withAuthor(VALID_AUTHOR_ALICE)
                .withTags(VALID_TAG_TEXTBOOK).build();
        assertTrue(ALI.isSameBook(editedAlice));

        // same name, same phone, same email, same attributes -> returns true
        editedAlice = new BookBuilder(ALI).withAuthor(VALID_AUTHOR_ALICE).withTags(VALID_TAG_FANTASY).build();
        assertTrue(ALI.isSameBook(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Book aliceCopy = new BookBuilder(ALI).build();
        assertTrue(ALI.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALI.equals(ALI));

        // null -> returns false
        assertFalse(ALI.equals(null));

        // different type -> returns false
        assertFalse(ALI.equals(5));

        // different book -> returns false
        assertFalse(ALI.equals(CS));

        // different name -> returns false
        Book editedAlice = new BookBuilder(ALI).withBookName(VALID_BOOKNAME_CS).build();
        assertFalse(ALI.equals(editedAlice));

        // different author -> returns false
        editedAlice = new BookBuilder(ALI).withAuthor(VALID_AUTHOR_CS).build();
        assertFalse(ALI.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new BookBuilder(ALI).withTags(VALID_TAG_TEXTBOOK).build();
        assertFalse(ALI.equals(editedAlice));
    }
}
