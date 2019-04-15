package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_PART_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_PART_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.NAME_PART_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_PART_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_PART_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_PART_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_PART_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_PART_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEXTBOOK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ListBookCommand;
import seedu.address.model.book.BookListFilterPredicate;

public class ListBookCommandParserTest {

    private ListBookCommandParser parser = new ListBookCommandParser();

    @Test
    public void parse_validArgs_returnsFindCommand() {
        String namesStr1 = "";
        String namesStr2 = NAME_PART_DESC_ALICE;
        String namesStr3 = NAME_PART_DESC_ALICE + NAME_PART_DESC_CS;
        String[] namesArr1 = {};
        String[] namesArr2 = {VALID_BOOKNAME_PART_ALICE};
        String[] namesArr3 = {VALID_BOOKNAME_PART_ALICE, VALID_BOOKNAME_PART_CS};

        String authorsStr1 = "";
        String authorsStr2 = AUTHOR_PART_DESC_ALICE;
        String authorsStr3 = AUTHOR_PART_DESC_ALICE + AUTHOR_PART_DESC_CS;
        String[] authorsArr1 = {};
        String[] authorsArr2 = {VALID_AUTHOR_PART_ALICE};
        String[] authorsArr3 = {VALID_AUTHOR_PART_ALICE, VALID_AUTHOR_PART_CS};

        String tagsStr1 = "";
        String tagsStr2 = TAG_DESC_TEXTBOOK;
        String tagsStr3 = TAG_DESC_TEXTBOOK + TAG_DESC_FANTASY;
        String[] tagsArr1 = {};
        String[] tagsArr2 = {VALID_TAG_TEXTBOOK};
        String[] tagsArr3 = {VALID_TAG_TEXTBOOK, VALID_TAG_FANTASY};

        String ratingsStr1 = "";
        String ratingsStr2 = RATING_DESC_ALICE;
        String ratingsStr3 = RATING_DESC_ALICE + RATING_DESC_CS;
        String[] ratingsArr1 = {};
        String[] ratingsArr2 = {VALID_RATING_ALICE};
        String[] ratingsArr3 = {VALID_RATING_ALICE, VALID_RATING_CS};

        String argsStr1 = PREAMBLE_WHITESPACE + namesStr1 + authorsStr1 + tagsStr1 + ratingsStr1;
        String argsStr2 = PREAMBLE_WHITESPACE + namesStr2 + authorsStr2 + tagsStr2 + ratingsStr2;
        String argsStr3 = PREAMBLE_WHITESPACE + namesStr3 + authorsStr3 + tagsStr3 + ratingsStr3;

        BookListFilterPredicate predicate1 = new BookListFilterPredicate(
                Arrays.asList(namesArr1),
                Arrays.asList(authorsArr1),
                Arrays.asList(tagsArr1),
                Arrays.asList(ratingsArr1)
        );



        BookListFilterPredicate predicate2 = new BookListFilterPredicate(
                Arrays.asList(namesArr2),
                Arrays.asList(authorsArr2),
                Arrays.asList(tagsArr2),
                Arrays.asList(ratingsArr2)
        );

        BookListFilterPredicate predicate3 = new BookListFilterPredicate(
                Arrays.asList(namesArr3),
                Arrays.asList(authorsArr3),
                Arrays.asList(tagsArr3),
                Arrays.asList(ratingsArr3)
        );

        assertParseSuccess(parser, argsStr1, new ListBookCommand(predicate1));

        assertParseSuccess(parser, argsStr2, new ListBookCommand(predicate2));

        assertParseSuccess(parser, argsStr3, new ListBookCommand(predicate3));
    }

    @Test
    public void parse_wrongValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListBookCommand.MESSAGE_USAGE);
        String argsStr = PREAMBLE_NON_EMPTY;

        assertParseFailure(parser, argsStr, expectedMessage);
    }

}
