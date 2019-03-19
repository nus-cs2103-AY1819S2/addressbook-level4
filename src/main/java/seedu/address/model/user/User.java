package seedu.address.model.user;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a user data and allows importing and exporting
 */
public class User {
    private HashMap<Integer, CardSrsData> cardmap;

    public User() {
        cardmap = new HashMap<>();
    }

    public void addCard(CardSrsData singleCard) {
        cardmap.put(singleCard.getHashCode(), singleCard);
    }

    public void deleteCard(CardSrsData singleCard) {
        cardmap.remove(singleCard.getHashCode());
    }

    public void setCard(CardSrsData singleCard) {
        addCard(singleCard);
    }

    public CardSrsData getCard(int hashCode) {
        return cardmap.get(hashCode);
    }

    public Map<Integer, CardSrsData> getCards() {
        return cardmap;
    }
}
