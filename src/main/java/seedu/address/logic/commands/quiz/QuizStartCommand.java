package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.exceptions.CommandException.MESSAGE_EXPECTED_MGT_MODEL;
import static seedu.address.logic.parser.Syntax.PREFIX_START_COUNT;
import static seedu.address.logic.parser.Syntax.PREFIX_START_MODE;
import static seedu.address.logic.parser.Syntax.PREFIX_START_NAME;

import java.util.HashMap;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.management.ManagementModel;
import seedu.address.model.modelmanager.quiz.Quiz;
import seedu.address.model.modelmanager.quiz.QuizCard;
import seedu.address.model.modelmanager.quiz.QuizModel;
import seedu.address.model.session.Session;
import seedu.address.model.session.SrsCardsManager;
import seedu.address.model.user.CardSrsData;

/**
 * TODO: implement the actual start command
 */
public class QuizStartCommand implements Command {
    public static final String COMMAND_WORD = "start";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Parameters: "
            + PREFIX_START_NAME + "NAME "
            + "[" + PREFIX_START_COUNT + "COUNT] "
            + PREFIX_START_MODE + "MODE...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_START_NAME + "02-03-LEARN "
            + PREFIX_START_COUNT + "15 "
            + PREFIX_START_MODE + "LEARN";
    public static final String MESSAGE_SUCCESS = "Starting new quiz";
    public static final String MESSAGE_QUESTION_ANSWER = "Question: %1$s\nAnswer: %2$s";
    protected List<QuizCard> quizCards;
    private Session session;
    public QuizStartCommand(Session session) {
        requireNonNull(session);
        this.session = session;
    }
    /**
     * Executes the command.
     */
    public CommandResult executeActual(QuizModel model, CommandHistory history) {
        this.quizCards = session.generateSession();
        Quiz quiz = new Quiz(quizCards, session.getMode());

        model.init(quiz);
        QuizCard card = model.getNextCard();

        return new CommandResult(String.format(MESSAGE_QUESTION_ANSWER, card.getQuestion(), card.getAnswer()),
            true, false, false);
    }

    @Override
    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // CommandException will be thrown if and only if LogicManager passes in the incorrect Model
        // In other words, only incorrect code will result in a CommandException being thrown
        if (!(model instanceof ManagementModel)) {
            throw new CommandException(MESSAGE_EXPECTED_MGT_MODEL);
        }

        ManagementModel mgtModel = (ManagementModel) model;

        Lesson lesson = mgtModel.getLesson(0);
        HashMap<Integer, CardSrsData> cardData = null; //TODO: implement after model updates
        SrsCardsManager generateManager = new SrsCardsManager(lesson, cardData);
        this.session = new Session(this.session.getName(), this.session.getCount(), this.session.getMode(),
                generateManager.sort());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
