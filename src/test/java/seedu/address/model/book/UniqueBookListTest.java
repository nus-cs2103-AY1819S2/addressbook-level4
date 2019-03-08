package seedu.address.model.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEXTBOOK;
import static seedu.address.testutil.TypicalBooks.ALI;
import static seedu.address.testutil.TypicalBooks.CS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.testutil.BookBuilder;

public class UniqueBookListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueBookList uniqueBookList = new UniqueBookList();

    @Test
    public void contains_nullBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.contains(null);
    }

    @Test
    public void contains_bookNotInList_returnsFalse() {
        assertFalse(uniqueBookList.contains(ALI));
    }

    @Test
    public void contains_bookInList_returnsTrue() {
        uniqueBookList.add(ALI);
        assertTrue(uniqueBookList.contains(ALI));
    }

    @Test
    public void contains_bookWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBookList.add(ALI);
        Book editedAlice = new BookBuilder(ALI).withAuthor(VALID_AUTHOR_ALICE).withTags(VALID_TAG_FANTASY)
                .build();
        assertTrue(uniqueBookList.contains(editedAlice));
    }

    @Test
    public void add_nullBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.add(null);
    }

    @Test
    public void add_duplicateBook_throwsDuplicateBookException() {
        uniqueBookList.add(ALI);
        thrown.expect(DuplicateBookException.class);
        uniqueBookList.add(ALI);
    }

    @Test
    public void setBook_nullTargetBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.setBook(null, ALI);
    }

    @Test
    public void setBook_nullEditedBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.setBook(ALI, null);
    }

    @Test
    public void setBook_targetBookNotInList_throwsBookNotFoundException() {
        thrown.expect(BookNotFoundException.class);
        uniqueBookList.setBook(ALI, ALI);
    }

    @Test
    public void setBook_editedBookIsSameBook_success() {
        uniqueBookList.add(ALI);
        uniqueBookList.setBook(ALI, ALI);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(ALI);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setBook_editedBookHasSameIdentity_success() {
        uniqueBookList.add(ALI);
        Book editedAlice = new BookBuilder(ALI).withAuthor(VALID_AUTHOR_CS).withTags(VALID_TAG_TEXTBOOK)
                .build();
        uniqueBookList.setBook(ALI, editedAlice);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(editedAlice);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setBook_editedBookHasDifferentIdentity_success() {
        uniqueBookList.add(ALI);
        uniqueBookList.setBook(ALI, CS);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(CS);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setBook_editedBookHasNonUniqueIdentity_throwsDuplicateBookException() {
        uniqueBookList.add(ALI);
        uniqueBookList.add(CS);
        thrown.expect(DuplicateBookException.class);
        uniqueBookList.setBook(ALI, CS);
    }

    @Test
    public void remove_nullBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.remove(null);
    }

    @Test
    public void remove_bookDoesNotExist_throwsBookNotFoundException() {
        thrown.expect(BookNotFoundException.class);
        uniqueBookList.remove(ALI);
    }

    @Test
    public void remove_existingBook_removesBook() {
        uniqueBookList.add(ALI);
        uniqueBookList.remove(ALI);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setBooks_nullUniqueBookList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.setBooks((UniqueBookList) null);
    }

    @Test
    public void setBooks_uniqueBookList_replacesOwnListWithProvidedUniqueBookList() {
        uniqueBookList.add(ALI);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(CS);
        uniqueBookList.setBooks(expectedUniqueBookList);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setBooks_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.setBooks((List<Book>) null);
    }

    @Test
    public void setBooks_list_replacesOwnListWithProvidedList() {
        uniqueBookList.add(ALI);
        List<Book> bookList = Collections.singletonList(CS);
        uniqueBookList.setBooks(bookList);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(CS);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setBooks_listWithDuplicateBooks_throwsDuplicateBookException() {
        List<Book> listWithDuplicateBooks = Arrays.asList(ALI, ALI);
        thrown.expect(DuplicateBookException.class);
        uniqueBookList.setBooks(listWithDuplicateBooks);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueBookList.asUnmodifiableObservableList().remove(0);
    }
}
