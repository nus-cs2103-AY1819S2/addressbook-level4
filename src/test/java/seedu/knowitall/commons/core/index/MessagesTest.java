package seedu.knowitall.commons.core.index;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static seedu.knowitall.commons.core.Messages.MESSAGE_CARDS_LISTED_OVERVIEW;
import static seedu.knowitall.commons.core.Messages.MESSAGE_CSV_MANAGER_NOT_INITIALIZED;
import static seedu.knowitall.commons.core.Messages.MESSAGE_EMPTY_CSV_FILE;
import static seedu.knowitall.commons.core.Messages.MESSAGE_ILLEGAL_OPTION_CANNOT_BE_SAME_AS_ANSWER;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INCORRECT_CSV_FILE_HEADER;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_ANSWER_COMMAND;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_INSIDE_FOLDER;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_ON_EMPTY_FOLDER;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_FOLDER_DISPLAYED_INDEX;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_NEXT_COMMAND;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_REVEAL_COMMAND;
import static seedu.knowitall.commons.core.Messages.MESSAGE_MAX_COMMAND_LENGTH_EXCEEDED;
import static seedu.knowitall.commons.core.Messages.MESSAGE_NO_NEGATIVE_INDEX;
import static seedu.knowitall.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

public class MessagesTest {
    /**
     * Tests if messages are present and of string type.
     */
    @Test
    public void testMessagesPresent() {
        assertThat(MESSAGE_UNKNOWN_COMMAND, instanceOf(String.class));
        assertThat(MESSAGE_MAX_COMMAND_LENGTH_EXCEEDED, instanceOf(String.class));
        assertThat(MESSAGE_INVALID_COMMAND_FORMAT, instanceOf(String.class));
        assertThat(MESSAGE_INVALID_CARD_DISPLAYED_INDEX, instanceOf(String.class));
        assertThat(MESSAGE_INVALID_FOLDER_DISPLAYED_INDEX, instanceOf(String.class));
        assertThat(MESSAGE_CARDS_LISTED_OVERVIEW, instanceOf(String.class));
        assertThat(MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN, instanceOf(String.class));
        assertThat(MESSAGE_INVALID_ANSWER_COMMAND, instanceOf(String.class));
        assertThat(MESSAGE_INVALID_REVEAL_COMMAND, instanceOf(String.class));
        assertThat(MESSAGE_NO_NEGATIVE_INDEX, instanceOf(String.class));
        assertThat(MESSAGE_INVALID_COMMAND_ON_EMPTY_FOLDER, instanceOf(String.class));
        assertThat(MESSAGE_INVALID_NEXT_COMMAND, instanceOf(String.class));
        assertThat(MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER, instanceOf(String.class));
        assertThat(MESSAGE_INVALID_COMMAND_INSIDE_FOLDER, instanceOf(String.class));
        assertThat(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, instanceOf(String.class));
        assertThat(MESSAGE_ILLEGAL_OPTION_CANNOT_BE_SAME_AS_ANSWER, instanceOf(String.class));
        assertThat(MESSAGE_CSV_MANAGER_NOT_INITIALIZED, instanceOf(String.class));
        assertThat(MESSAGE_INCORRECT_CSV_FILE_HEADER, instanceOf(String.class));
        assertThat(MESSAGE_EMPTY_CSV_FILE, instanceOf(String.class));
    }
}
