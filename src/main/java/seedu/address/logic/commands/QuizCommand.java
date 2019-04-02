package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.collections.FXCollections;
import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;

/**
 * Enters quiz mode
 */
public class QuizCommand extends Command {

    public static final String COMMAND_WORD = "quiz";

    public static final String MESSAGE_QUIZ_START = "Quiz mode started. Good luck :)";
    public static final String MESSAGE_QUIZ_FAILURE_EMPTY = "Cannot start quiz mode on empty list";
    public static final String MESSAGE_QUIZ_FAILURE_IN_QUIZ = "Already in quiz mode";

    private List<Flashcard> getShuffledFlashCards(Model model) {
        Random random = new Random();

        List<Flashcard> filteredFlashcardList = model.getFilteredFlashcardList();
        ArrayList<Flashcard> quizCards = new ArrayList<>(filteredFlashcardList);
        quizCards.sort((Flashcard card1, Flashcard card2) -> {
            double successRate1 = card1.getStatistics().getSuccessRate();
            double successRate2 = card2.getStatistics().getSuccessRate();
            if (successRate1 < successRate2) {
                return -1;
            } else if (successRate1 > successRate2) {
                return 1;
            } else {
                return random.nextInt(2) == 1 ? -1 : 1;
            }
        });
        return quizCards;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getQuizMode() != QuizState.NOT_QUIZ_MODE) {
            throw new CommandException(MESSAGE_QUIZ_FAILURE_IN_QUIZ);
        }

        List<Flashcard> quizCards = getShuffledFlashCards(model);

        if (quizCards.isEmpty()) {
            throw new CommandException(MESSAGE_QUIZ_FAILURE_EMPTY);
        }

        model.resetQuizStat();
        model.setQuizFlashcards(FXCollections.observableArrayList(quizCards));
        model.setQuizMode(QuizState.QUIZ_MODE_FRONT);
        model.showNextQuizCard();
        return new CommandResult(MESSAGE_QUIZ_START);
    }
}
