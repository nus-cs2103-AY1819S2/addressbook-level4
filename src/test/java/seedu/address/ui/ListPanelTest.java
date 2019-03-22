package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalCards.getTypicalCards;
import static seedu.address.testutil.TypicalDecks.getTypicalDecks;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplayEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCardObject;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.CardDisplayHandle;
import guitests.guihandles.DeckDisplayHandle;
import guitests.guihandles.ListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.ListItem;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

public class ListPanelTest extends GuiUnitTest {
    private static final ObservableList<Card> TYPICAL_CARDS =
            FXCollections.observableList(getTypicalCards());
    private static final ObservableList<Deck> TYPICAL_DECKS =
            FXCollections.observableList(getTypicalDecks());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT= 2500;
    private static final long DECK_CREATION_AND_DELETION_TIMEOUT= 2500;

    private final SimpleObjectProperty<ListItem> selectedItem = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<ListItem> selectedCard = new SimpleObjectProperty<>();

    private ListPanelHandle listPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_CARDS);

        for (int i = 0; i < TYPICAL_CARDS.size(); i++) {
            listPanelHandle.navigateToDeck(TYPICAL_DECKS.get(i));
            Card expectedCard = TYPICAL_CARDS.get(i);
            CardDisplayHandle actualCard = listPanelHandle.getDeckDiplayHandle(i);

            assertCardDisplaysCardObject(expectedCard, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedCardChanged_selectionChanges() {
        initUi(TYPICAL_CARDS);
        Card secondCard = TYPICAL_CARDS.get(INDEX_SECOND_CARD.getZeroBased());
        guiRobot.interact(() -> selectedCard.set(secondCard));
        guiRobot.pauseForHuman();

        CardDisplayHandle expectedPerson = listPanelHandle.getDeckDiplayHandle(INDEX_SECOND_CARD.getZeroBased());
        CardDisplayHandle selectedPerson = listPanelHandle.getHandleToSelectedDeck();
        assertCardDisplayEquals(expectedPerson, selectedPerson);
    }


    /**
     * Verifies that creating and deleting large number of cards in {@code ListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Card> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of card cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code ListPanel}.
     */
    private ObservableList<Card> createBackingList(int personCount) {
        ObservableList<Card> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < personCount; i++) {
            String question = i + " question";
            String answer = "ans";
            Card card = new Card(question, answer, Collections.emptySet());
            backingList.add(card);
        }
        return backingList;
    }

    /**
     * Initializes {@code listPanelHandle} with a {@code ListPanel} backed by {@code list}.
     * Also shows the {@code Stage} that displays only {@code ListPanel}.
     */
    private void initUi(ObservableList<? extends ListItem> list) {
        ListPanel listPanel =
                new ListPanel(list, selectedItem, selectedItem::set);
        uiPartRule.setUiPart(listPanel);

        listPanelHandle = new ListPanelHandle(getChildNode(listPanel.getRoot(),
                ListPanelHandle.DECK_LIST_VIEW_ID));
    }
}
