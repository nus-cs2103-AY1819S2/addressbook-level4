package seedu.address.logic.parser;

import static java.lang.String.format;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.quiz.QuizStartCommand;
//import seedu.address.model.modelmanager.quiz.Quiz;
//import seedu.address.model.session.Session;

public class StartCommandParserTest {
    private StartCommandParser parser = new StartCommandParser();
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", format(MESSAGE_INVALID_COMMAND_FORMAT,
                QuizStartCommand.MESSAGE_USAGE));
    }
    /*@Test
    public void parseArguments() {
        QuizStartCommand expected = new QuizStartCommand(new Session("02-03-LEARN", 15,
                Quiz.Mode.LEARN));
        assertParseSuccess(parser, "02-03-LEARN 15 LEARN", expected);
        assertParseSuccess(parser, " \n 02-03-LEARN \n \t 15  \t LEARN", expected);
    }*/
}
