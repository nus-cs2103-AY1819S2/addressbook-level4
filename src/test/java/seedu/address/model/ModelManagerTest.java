package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_ALICE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;
import static seedu.address.testutil.TypicalBooks.ALI;
import static seedu.address.testutil.TypicalBooks.CS;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookNameContainsKeywordsPredicate;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BookShelfBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new BookShelf(), new BookShelf(modelManager.getBookShelf()));
        assertEquals(null, modelManager.getSelectedBook());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setBookShelfFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setBookShelfFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setBookShelfFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setBookShelfFilePath(null);
    }

    @Test
    public void setBookShelfFilePath_validPath_setsBookShelfFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setBookShelfFilePath(path);
        assertEquals(path, modelManager.getBookShelfFilePath());
    }

    @Test
    public void hasBook_nullBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasBook(null);
    }

    @Test
    public void hasBook_bookNotInBookShelf_returnsFalse() {
        assertFalse(modelManager.hasBook(ALI));
    }

    @Test
    public void hasBook_bookInBookShelf_returnsTrue() {
        modelManager.addBook(ALI);
        assertTrue(modelManager.hasBook(ALI));
    }

    @Test
    public void deleteBook_bookIsSelectedAndFirstBookInFilteredBookList_selectionCleared() {
        modelManager.addBook(ALI);
        modelManager.setSelectedBook(ALI);
        modelManager.deleteBook(ALI);
        assertEquals(null, modelManager.getSelectedBook());
    }

    @Test
    public void deleteBook_bookIsSelectedAndSecondBookInFilteredBookList_firstBookSelected() {
        modelManager.addBook(ALI);
        modelManager.addBook(CS);
        assertEquals(Arrays.asList(ALI, CS), modelManager.getFilteredBookList());
        modelManager.setSelectedBook(CS);
        modelManager.deleteBook(CS);
        assertEquals(ALI, modelManager.getSelectedBook());
    }

    @Test
    public void setBook_bookIsSelected_selectedBookUpdated() {
        modelManager.addBook(ALI);
        modelManager.setSelectedBook(ALI);
        Book updatedAlice = new BookBuilder(ALI).withRating(VALID_RATING_ALICE).build();
        modelManager.setBook(ALI, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedBook());
    }

    @Test
    public void getFilteredBookList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredBookList().remove(0);
    }

    @Test
    public void setSelectedBook_bookNotInFilteredBookList_throwsBookNotFoundException() {
        thrown.expect(BookNotFoundException.class);
        modelManager.setSelectedBook(ALI);
    }

    @Test
    public void setSelectedBook_bookInFilteredBookList_setsSelectedBook() {
        modelManager.addBook(ALI);
        assertEquals(Collections.singletonList(ALI), modelManager.getFilteredBookList());
        modelManager.setSelectedBook(ALI);
        assertEquals(ALI, modelManager.getSelectedBook());
    }

    @Test
    public void equals() {
        BookShelf bookShelf = new BookShelfBuilder().withBook(ALI).withBook(CS).build();
        BookShelf differentBookShelf = new BookShelf();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(bookShelf, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(bookShelf, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different bookShelf -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentBookShelf, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALI.getBookName().fullName.split("\\s+");
        modelManager.updateFilteredBookList(new BookNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(bookShelf, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setBookShelfFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(bookShelf, differentUserPrefs)));
    }
}
