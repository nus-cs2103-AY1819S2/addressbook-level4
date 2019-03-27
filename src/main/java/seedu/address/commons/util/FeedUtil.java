package seedu.address.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import seedu.address.model.EntryBook;
import seedu.address.model.entry.Address;
import seedu.address.model.entry.Description;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.Link;
import seedu.address.model.entry.Title;
import seedu.address.util.Network;

/**
 * Converts between RSS/Syndication feeds and EntryBook.
 */
public class FeedUtil {
    public static final String DEFAULT_DESCRIPTION_TEXT = "imported from %s";

    /** Reads in URL of a feed and serializes it into an {@code EntryBook}. */
    public static EntryBook importFrom(String feedUrl) throws IOException, FeedException {
        InputStream inputStream = Network.fetchAsStream(feedUrl);
        SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(inputStream));

        List<Entry> importedEntries = syndFeed.getEntries().stream()
                .map(syndEntry -> syndEntryToEntryBookEntry(syndEntry, feedUrl))
                .collect(Collectors.toList());
        // todo: handle dupes
        EntryBook entryBook = new EntryBook();
        entryBook.setPersons(importedEntries);
        return entryBook;
    }

    /** Converts a single SyndEntry into an EntryBook Entry. */
    private static Entry syndEntryToEntryBookEntry(SyndEntry syndEntry, String feedUrl) {
        return new Entry(
                new Title(syndEntry.getTitle().trim()),
                extractDescription(syndEntry, feedUrl),
                new Link(syndEntry.getLink()),
                new Address("unused"),
                Collections.emptySet()
        );
    }

    /** Extracts a useful description from a SyndEntry. */
    private static Description extractDescription(SyndEntry syndEntry, String feedUrl) {
        String description = syndEntry.getDescription().getValue().replace('\n', ' ').trim();
        if (description.isEmpty()) {
            description = String.format(DEFAULT_DESCRIPTION_TEXT, feedUrl);
        }
        return new Description(description);
    }
}
