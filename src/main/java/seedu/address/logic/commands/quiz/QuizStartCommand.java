package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.exceptions.CommandException.MESSAGE_EXPECTED_MGT_MODEL;
import static seedu.address.logic.parser.Syntax.PREFIX_START_COUNT;
import static seedu.address.logic.parser.Syntax.PREFIX_START_MODE;
import static seedu.address.logic.parser.Syntax.PREFIX_START_NAME;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.management.ManagementModel;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizUiDisplayFormatter;
import seedu.address.model.session.Session;
import seedu.address.model.session.SrsCardsManager;
import seedu.address.model.user.CardSrsData;

//import seedu.address.model.modelmanager.management.ManagementModelManager;
//import seedu.address.storage.CsvLessonsStorage;

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

        if (card.getQuizMode() == Quiz.Mode.PREVIEW) {
            model.setDisplayFormatter(new QuizUiDisplayFormatter("Question", card.getQuestion(),
                "Answer", card.getAnswer(), Quiz.Mode.PREVIEW));
        } else {
            model.setDisplayFormatter(new QuizUiDisplayFormatter("Question", card.getQuestion(),
                "Answer", Quiz.Mode.REVIEW));
        }

        return new CommandResult("", true, false, false);
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

        //CsvLessonsStorage storage = new CsvLessonsStorage(INVALID_CORE_CHAR_FIELD_DATA_FOLDER);
        //ManagementModel mgtModel = new ManagementModelManager(null, storage.readLessons().get());
        //Lesson lesson = mgtModel.getLesson(0);
        //HashMap<Integer, CardSrsData> cardData = null;
        //TODO: implement these hard code after updates
        Card card1 = new Card(List.of("Japan", "Tokyo"), List.of("T"));
        Card card2 = new Card(List.of("Belgium", "Brussels"), List.of("B"));
        Lesson lesson = new Lesson("Capitals", List.of("Country", "Capitals"), List.of("Hint"),
                0, 1, List.of(card1, card2));
        Instant current = Instant.now();
        CardSrsData cardData1 = new CardSrsData(card1.hashCode(), 1, 1, current);
        CardSrsData cardData2 = new CardSrsData(card2.hashCode(), 1, 1,
                current.plus(Duration.ofHours(1)));
        HashMap<Integer, CardSrsData> cardData = new HashMap<>();
        cardData.put(card1.hashCode(), cardData1);
        cardData.put(card2.hashCode(), cardData2);
        SrsCardsManager generateManager = new SrsCardsManager(lesson, cardData);
        this.session = new Session(this.session.getName(), this.session.getCount(), this.session.getMode(),
                generateManager.sort());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
