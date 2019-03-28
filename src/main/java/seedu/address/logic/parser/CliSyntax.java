package seedu.address.logic.parser;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DIRECTORY = new Prefix("d/");
    public static final Prefix PREFIX_FILE = new Prefix("f/");
    public static final Prefix PREFIX_TAG_NAME = new Prefix("t/");
    public static final Prefix PREFIX_TAG_NEW = new Prefix("-a");
    public static final Prefix PREFIX_TAG_REMOVE = new Prefix("-r");
    public static final Prefix PREFIX_DEADLINE_NEW = new Prefix("date/");
    public static final Prefix PREFIX_DEADLINE_COMPLETE = new Prefix("done");
    public static final Prefix PREFIX_DEADLINE_REMOVE = new Prefix("remove");
    public static final int PREFIX_COUNT = 8;

    /**
     * Returns all prefixes.
     */
    public static Prefix[] getAllPrefixes() {
        return Stream.of(PREFIX_NAME, PREFIX_DIRECTORY,
                PREFIX_FILE, PREFIX_TAG_NEW, PREFIX_TAG_REMOVE, PREFIX_DEADLINE_NEW,
                PREFIX_DEADLINE_COMPLETE, PREFIX_DEADLINE_REMOVE)
                .collect(Collectors.toList())
                .toArray(new Prefix[PREFIX_COUNT]);
    }

    /**
     * Returns all the invalid prefixes for the command given {@code validPrefix}.
     */
    public static Prefix[] getInvalidPrefixesForCommand(Prefix validPrefix) {
        return Stream.of(getAllPrefixes())
                .filter(x -> x != validPrefix)
                .collect(Collectors.toList())
                .toArray(new Prefix[PREFIX_COUNT]);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
