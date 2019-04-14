package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;
import static seedu.address.testutil.TypicalDecks.DECK_A;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.BackCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GenerateQuestionCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.OpenDeckCommand;
import seedu.address.logic.commands.ShowAnswerCommand;
import seedu.address.model.StudyView;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.DeckBuilder;

public class StudyViewParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Deck deck = new DeckBuilder(DECK_A).build();
    private StudyView studyView;
    private StudyViewParser studyViewParser;

    @Before
    public void setUp() {
        Card subtraction = new CardBuilder(SUBTRACTION).build();
        deck.addCard(subtraction);
        studyView = new StudyView(deck);
        studyViewParser = new StudyViewParser(studyView);
    }


    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(studyViewParser.parse(ExitCommand.COMMAND_WORD, "") instanceof ExitCommand);
        assertTrue(studyViewParser.parse(ExitCommand.COMMAND_WORD, " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(studyViewParser.parse(HelpCommand.COMMAND_WORD, "") instanceof HelpCommand);
        assertTrue(studyViewParser.parse(HelpCommand.COMMAND_WORD, " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(studyViewParser.parse(HistoryCommand.COMMAND_WORD, "") instanceof HistoryCommand);
        assertTrue(studyViewParser.parse(HistoryCommand.COMMAND_WORD, " 3") instanceof HistoryCommand);
    }

    @Test
    public void parseCommand_open() throws Exception {
        assertTrue(studyViewParser.parse(OpenDeckCommand.COMMAND_WORD, "") instanceof OpenDeckCommand);
        assertTrue(studyViewParser.parse(OpenDeckCommand.COMMAND_WORD, " 3") instanceof OpenDeckCommand);
    }

    @Test
    public void parseCommand_back() throws Exception {
        assertTrue(studyViewParser.parse(BackCommand.COMMAND_WORD, "") instanceof BackCommand);
        assertTrue(studyViewParser.parse(BackCommand.COMMAND_WORD, " 3") instanceof BackCommand);
    }

    @Test
    public void parseCommand_showAnswer() throws Exception {
        studyView.setCurrentStudyState(StudyView.StudyState.QUESTION);
        assertTrue(studyViewParser.parse("sample", "answer") instanceof ShowAnswerCommand);
        assertTrue(studyViewParser.parse("sample", "") instanceof ShowAnswerCommand);
    }

    @Test
    public void parseCommand_generateQuestion() throws Exception {
        studyView.setCurrentStudyState(StudyView.StudyState.ANSWER);
        assertTrue(studyViewParser.parse("1", "1") instanceof GenerateQuestionCommand);
        assertTrue(studyViewParser.parse("1", "") instanceof GenerateQuestionCommand);
    }
}
