package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashMap;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
<<<<<<< HEAD:src/main/java/seedu/address/logic/commands/StartCommand.java
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.session.Session;
import seedu.address.model.session.SrsCardsManager;
import seedu.address.model.user.CardSrsData;
import seedu.address.quiz.Quiz;
import seedu.address.quiz.QuizCard;
import seedu.address.quiz.QuizModel;
=======
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.quiz.Quiz;
import seedu.address.model.modelmanager.quiz.QuizCard;
import seedu.address.model.modelmanager.quiz.QuizModel;
>>>>>>> 9796a678da6bc293ec34cf45dac1be7d8be3ce1b:src/main/java/seedu/address/logic/commands/quiz/QuizStartCommand.java


/**
 * TODO: implement the actual start command
 */
public class QuizStartCommand implements Command {
    public static final String COMMAND_WORD = "start";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_COUNT + "COUNT] "
            + PREFIX_MODE + "MODE...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "02-03-LEARN "
            + PREFIX_COUNT + "15 "
            + PREFIX_MODE + "LEARN";
    public static final String MESSAGE_SUCCESS = "Starting new quiz";
    public static final String MESSAGE_QUESTION_ANSWER = "Question: %1$s\nAnswer: %2$s";

<<<<<<< HEAD:src/main/java/seedu/address/logic/commands/StartCommand.java
    private List<QuizCard> quizCards;
    private Session session;
=======
    //    public QuizStartCommand() {
    //        // TODO start session
    //    }
>>>>>>> 9796a678da6bc293ec34cf45dac1be7d8be3ce1b:src/main/java/seedu/address/logic/commands/quiz/QuizStartCommand.java

    public StartCommand(Session session) {
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

        return new CommandResult(String.format(MESSAGE_QUESTION_ANSWER, card.getQuestion(), card.getAnswer()));
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
        Lesson lesson = model.getLesson(0);
        HashMap<Integer, CardSrsData> cardData = null; //TODO: implement after model updates
        SrsCardsManager generateManager = new SrsCardsManager(lesson, cardData);
        Session session = new Session(this.session.getName(), this.session.getCount(), this.session.getMode(),
                generateManager.sort());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
