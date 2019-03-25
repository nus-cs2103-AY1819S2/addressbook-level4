package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Question;
import seedu.address.model.hint.Hint;
import seedu.address.storage.csvmanager.CardFolderExport;
import seedu.address.storage.csvmanager.CsvFile;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INDEX_LESS_THAN_ZERO = "Index is less than zero";
    public static final String HOME_SYMBOL = "..";


    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code args} and checks whether it is equal to {@code HOME_SYMBOL}
     */
    public static boolean parseHomeSymbol(String args) {
        String trimmedArgs = args.trim();
        return (trimmedArgs.equals(HOME_SYMBOL));
    }

    /**
     * Parses a {@code String question} into a {@code Question}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code question} is invalid.
     */
    public static Question parseQuestion(String question) throws ParseException {
        requireNonNull(question);
        String trimmedQuestion = question.trim();
        if (!Question.isValidQuestion(trimmedQuestion)) {
            throw new ParseException(Question.MESSAGE_CONSTRAINTS);
        }
        return new Question(trimmedQuestion);
    }

    /**
     * Parses a {@code String answer} into a {@code Answer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code answer} is invalid.
     */
    public static Answer parseAnswer(String answer) throws ParseException {
        requireNonNull(answer);
        String trimmedAnswer = answer.trim();
        if (!Answer.isValidAnswer(trimmedAnswer)) {
            throw new ParseException(Answer.MESSAGE_CONSTRAINTS);
        }
        return new Answer(trimmedAnswer);
    }

    /**
     * Parses a {@code String hint} into a {@code Hint}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code hint} is invalid.
     */
    public static Hint parseHint(String hint) throws ParseException {
        requireNonNull(hint);
        String trimmedHint = hint.trim();
        if (!Hint.isValidHintName(trimmedHint)) {
            throw new ParseException(Hint.MESSAGE_CONSTRAINTS);
        }
        return new Hint(trimmedHint);
    }

    /**
     * Parses {@code Collection<String> hints} into a {@code Set<Hint>}.
     * Restrict to at most the last hint from {@code Collection<String> hints}, or none if the Collection is empty.
     */
    public static Set<Hint> parseHints(Collection<String> hints) throws ParseException {
        requireNonNull(hints);
        List<String> hintList = new ArrayList<>(hints);
        final Set<Hint> hintSet = new HashSet<>();
        if (!hintList.isEmpty()) {
            hintSet.add(parseHint(hintList.get(hintList.size() - 1)));
        }
        return hintSet;
    }

    /**
     * Parsers {@code Collection<String> folderNames} into a {@Code Set<CardFolderExport>}.
     * Similar folder names will not be included inside the set.
     */
    public static Set<CardFolderExport> parseFolders(Collection<String> folderNames) throws ParseException {
        requireNonNull(folderNames);
        final Set<CardFolderExport> cardFolderExports = new HashSet<>();
        for (String folderName : folderNames) {
            cardFolderExports.add(parseFolder(folderName));
        }
        return cardFolderExports;
    }

    public static Integer stringToInt(String element) throws NumberFormatException {
        return Integer.parseInt(element);
    }


    public static List<Integer> parseFolderIndex(String folderIndexes) throws NumberFormatException, IllegalValueException {
        List<Integer> indexList = new ArrayList<>();
        folderIndexes = folderIndexes.trim();
        indexList = Arrays.stream(folderIndexes.split(" ")).map(ParserUtil::stringToInt).collect(Collectors.toList());

        List<Integer> InvalidIndexList = indexList.stream().filter(i -> i < 0).collect(Collectors.toList());
        if (InvalidIndexList.size() > 0) {
            throw new IllegalValueException(MESSAGE_INDEX_LESS_THAN_ZERO);
        }
        return indexList;
    }

    /**
     * Parses a {@Code String folderName} into a {@code CardFolderExport}.
     * Trims any leading and trailing white space for folder name.
     */
    private static CardFolderExport parseFolder(String folderName) throws ParseException {
        requireNonNull(folderName);
        folderName = folderName.trim();
        if (CardFolderExport.isFolderNameEmpty(folderName)) {
            throw new ParseException(CardFolderExport.MESSAGE_CONSTRAINTS);
        }
        return new CardFolderExport(folderName);
    }

    /**
     * Parses a {@Code String filename} into a {@Code CsvFile}
     */
    public static CsvFile parseFileName(String filename) throws ParseException {
        requireNonNull(filename);
        if (!CsvFile.isValidFileName(filename)) {
            throw new ParseException(CsvFile.MESSAGE_CONSTRAINTS);
        }
        return new CsvFile(filename);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
