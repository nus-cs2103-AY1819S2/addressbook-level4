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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof User)) {
            return false;
        }

        User otherUser = (User) other;
        return otherUser.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return cardmap.hashCode();
    }
}
