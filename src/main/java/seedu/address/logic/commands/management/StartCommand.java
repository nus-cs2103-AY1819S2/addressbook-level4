package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_LESSON_VIEW_COMMAND;
import static seedu.address.logic.commands.management.ManagementCommand.requireManagementModel;
import static seedu.address.logic.parser.Syntax.PREFIX_START_COUNT;
import static seedu.address.logic.parser.Syntax.PREFIX_START_MODE;

import java.util.HashMap;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.quiz.QuizCommand;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.session.Session;
import seedu.address.model.session.SrsCardsManager;
import seedu.address.model.srscard.SrsCard;
import seedu.address.model.user.CardSrsData;

/**
 * This implements a {@link QuizCommand} which starts a {@link Quiz}.
 *
 * It requires a {@link QuizModel} to be passed into the {@link #execute(Model, CommandHistory)}
 * command.
 */
public class StartCommand extends QuizCommand {
    public static final String COMMAND_WORD = "start";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Parameters: "
            + "LESSON_INDEX "
            + "[" + PREFIX_START_COUNT + "COUNT] "
            + PREFIX_START_MODE + "MODE...\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_START_COUNT + "10 "
            + PREFIX_START_MODE + "LEARN";
    public static final String MESSAGE_SUCCESS = "Starting new quiz";
    public static final String MESSAGE_LESSON = "\nCurrent lesson: ";
    public static final String MESSAGE_COUNT = "Not enough cards in current lesson.\nSet the count to the maximum"
            + " number for you by default.";
    private Session session;

    /**
     * Constructs a {@link QuizCommand} to start the specified {@link Quiz}
     *
     * @param session to be opened.
     */
    public StartCommand(Session session) {
        requireNonNull(session);
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    /**
     * Executes the command and starts the new {@link Quiz}.
     *
     * @return messages to show whether the quiz starts correctly.
     */
    public CommandResult executeQuiz(QuizModel model, CommandHistory history) throws CommandException {
        QuizModel quizModel = requireQuizModel(model);

        StringBuilder sb = new StringBuilder();
        if (session.getCount() > session.getSrsCards().size()) {
            session.setCount(session.getSrsCards().size());
            sb.append(MESSAGE_COUNT);
            sb.append(MESSAGE_LESSON + session.getName());
        } else {
            sb.append(MESSAGE_SUCCESS);
            sb.append(MESSAGE_LESSON + session.getName());
        }
        List<QuizCard> quizCards = session.generateSession();
        Quiz quiz = new Quiz(quizCards, session.getMode());
        quizModel.init(quiz, session);
        quizModel.getNextCard();

        return new CommandResult(sb.toString(), true, false, false);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ManagementModel mgtModel = requireManagementModel(model);

        if (mgtModel.isThereOpenedLesson()) {
            throw new CommandException(MESSAGE_LESSON_VIEW_COMMAND);
        }
        LessonList lessonList = mgtModel.getLessonList();
        List<Lesson> lessons = lessonList.getLessons();
        if (this.session.getLessonIndex() > lessons.size()) {
            throw new CommandException("Lesson index is out of range. Please try a smaller one.");
        }
        if (this.session.getLessonIndex() <= 0) {
            throw new CommandException("Lesson index is out of range. Please try a larger one.");
        }
        Lesson lesson = lessons.get(this.session.getLessonIndex() - 1);
        HashMap<Integer, CardSrsData> cardData = new HashMap<>();
        for (int i = 0; i < lesson.getCardCount(); i++) {
            int currentHashcode = lesson.getCards().get(i).hashCode();
            if (mgtModel.getCardSrsData(currentHashcode) != null) {
                cardData.put(currentHashcode, mgtModel.getCardSrsData(currentHashcode));
            }
        }
        SrsCardsManager generateManager = new SrsCardsManager(lesson, cardData);
        if (this.session.getMode() == QuizMode.PREVIEW) {
            this.session = new Session(lesson.getName(), this.session.getCount(), this.session.getMode(),
                    generateManager.preview());
        } else if (this.session.getMode() == QuizMode.DIFFICULT) {
            List<SrsCard> srsCards = generateManager.previewDifficult();
            if (srsCards.size() == 0) {
                throw new CommandException("There is no difficult card in this lesson.");
            } else {
                this.session = new Session(lesson.getName(), this.session.getCount(), this.session.getMode(),
                        srsCards);
            }
        } else if (this.session.getMode() == QuizMode.REVIEW) {
            List<SrsCard> srsCards = generateManager.sort();
            if (srsCards.size() == 0) {
                throw new CommandException("There is no card for review since all cards in current lesson"
                        + " do not reach the due date.");
            } else {
                this.session = new Session(lesson.getName(), this.session.getCount(), this.session.getMode(),
                        srsCards);
            }
        } else if (this.session.getMode() == QuizMode.LEARN) {
            List<SrsCard> srsCards = generateManager.learn();
            if (srsCards.size() == 0) {
                throw new CommandException("There is no more new card to learn in this lesson.");
            } else {
                this.session = new Session(lesson.getName(), this.session.getCount(), this.session.getMode(),
                        srsCards);
            }
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
