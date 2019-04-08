package seedu.address.storage.portmanager;

import seedu.address.model.deck.Deck;
import seedu.address.model.deck.exceptions.DeckImportException;

/**
 * The API of PortManager
 */

public interface Porter {

    String exportDeck(Deck deck);

    Deck importDeck(String stringPath) throws DeckImportException;
}
