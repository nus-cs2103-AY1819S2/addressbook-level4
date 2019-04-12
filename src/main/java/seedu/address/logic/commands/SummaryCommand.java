package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Format summary for books in the bookshelf.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": A brief summary of books you've read.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_NO_AUTHOR_PREFERED = "You don't seem to prefer certain author.\n";
    public static final String MESSAGE_NO_RATING_GIVEN = "You haven't rated any book yet.\n";
    public static final String MESSAGE_NO_TAG_PREFERED = "You don't seem to prefer certain genre.\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        String feedback = "";
        feedback += "You've read " + model.getNumberOfBooks() + " books.\n";
        List<String> lovedAuthors = model.getMostReadAuthors();
        if (!lovedAuthors.isEmpty()) {
            feedback += getAutorRelatedSummary(model, lovedAuthors);
        } else {
            feedback += MESSAGE_NO_AUTHOR_PREFERED;
        }

        String highestRating = model.getHighestMark();
        if (highestRating != null) {
            feedback += getRatingRelatedSummary(model, highestRating);
        } else {
            feedback += MESSAGE_NO_RATING_GIVEN;
        }

        List<String> lovedTags = model.getMostReadTags();
        if (!lovedTags.isEmpty()) {
            feedback += getTagRelatedSummary(model, lovedTags);
        } else {
            feedback += MESSAGE_NO_TAG_PREFERED;
        }

        return new CommandResult(feedback, false, false);
    }

    private static String getAutorRelatedSummary(Model model, List<String> lovedAuthors) {
        String feedback = "You prefer books by ";
        int authorComma = lovedAuthors.size() - 1;
        for (String authorName : lovedAuthors) {
            feedback += authorName + ", as you've read: ";
            List<String> bookByThisAuthor = model.getBooksByAuthor(authorName);
            int bookComma = bookByThisAuthor.size() - 1;
            for (String bookName : bookByThisAuthor) {
                feedback += bookName;
                if (bookComma > 0) {
                    feedback += ", ";
                    bookComma--;
                }
            }
            if (authorComma > 0) {
                feedback += ", ";
                authorComma--;
            }
            feedback += "\n";
        }
        return feedback;
    }

    private static String getRatingRelatedSummary(Model model, String highestRating) {
        String feedback = "Book(s) receive a rating of " + highestRating + " from you: ";
        List<String> bookHighestRated = model.getBooksWithHighestMark(highestRating);
        int bookComma = bookHighestRated.size() - 1;
        for (String bookName : bookHighestRated) {
            feedback += bookName;
            if (bookComma > 0) {
                feedback += ", ";
                bookComma--;
            }
        }
        feedback += "\n";
        return feedback;
    }

    private static String getTagRelatedSummary(Model model, List<String> lovedTags) {
        String feedback = "You prefer books that you labeled as ";
        int tagComma = lovedTags.size() - 1;
        for (String tagContent : lovedTags) {
            feedback += tagContent + "(including ";
            List<String> bookWithTag = model.getBooksWithTag(tagContent);
            int bookComma = bookWithTag.size() - 1;
            for (String bookName : bookWithTag) {
                feedback += bookName;
                if (bookComma > 0) {
                    feedback += ", ";
                    bookComma--;
                }
            }
            feedback += ")";
            if (tagComma > 0) {
                feedback += ",";
            }
            feedback += "\n";
        }
        return feedback;
    }

}
