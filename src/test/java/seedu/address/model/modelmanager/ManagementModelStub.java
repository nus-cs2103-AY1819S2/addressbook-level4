package seedu.address.model.modelmanager;

import java.util.List;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.user.CardSrsData;
import seedu.address.model.user.User;

/**
 * A default management model stub which has all of the methods failing.
 * Extend this stub and use {@code @Override} to replace specific methods for unit testing.
 */
public class ManagementModelStub implements ManagementModel {
    private static final String MESSAGE_ASSERT_ERROR = "This method should not be called.";

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public Lesson getLesson(int index) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public boolean hasLessonWithName(String name) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public String openLesson(int index) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public Lesson getOpenedLesson() {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public List<Card> getOpenedLessonCards() {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public List<String> getOpenedLessonCoreHeaders() {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public List<String> getOpenedLessonOptionalHeaders() {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public void setOpenedLessonTestValues(int questionIndex, int answerIndex) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public void addCardToOpenedLesson(Card card) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public boolean openLessonHasCard(Card card) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public void deleteCardFromOpenedLesson(int index) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public String closeLesson() {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public boolean isThereOpenedLesson() {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public String changeTheme() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getTheme() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public LessonList getLessonList() {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public void setLessonList(LessonList lessonList) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public List<Lesson> getLessons() {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public void addLesson(Lesson lesson) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public void setLesson(int index, Lesson updatedLesson) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public void deleteLesson(int index) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public User getUser() {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public CardSrsData getCardSrsData(int hashCode) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public void addCardSrsData(CardSrsData card) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public void setCardSrsData(CardSrsData card) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }

    @Override
    public void deleteCardSrsData(CardSrsData card) {
        throw new AssertionError(MESSAGE_ASSERT_ERROR);
    }
}
