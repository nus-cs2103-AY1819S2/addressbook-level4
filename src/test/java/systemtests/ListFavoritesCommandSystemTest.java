package systemtests;

import org.junit.Test;
import seedu.address.model.Model;
import seedu.address.testutil.TypicalPersons;

import static org.junit.Assert.assertEquals;

public class ListFavoritesCommandSystemTest extends AddressBookSystemTest{

    @Test
    public void listFav() {
        Model model = getModel();

        assertCommandSuccess(model.getFavoritesList().size(), 0);

        model.addFavorites(TypicalPersons.ALICE);

        assertCommandSuccess(model.getFavoritesList().size(), 1);

        model.addFavorites(TypicalPersons.AMY);
        model.addFavorites(TypicalPersons.BENSON);
        model.addFavorites(TypicalPersons.BOB);

        assertCommandSuccess(model.getFavoritesList().size(), 4);

        model.removeFavorite(TypicalPersons.AMY);

        assertCommandSuccess(model.getFavoritesList().size(), 3);
    }

    private void assertCommandSuccess(int numFavorites, int expectedNumFavorites) {
        assertEquals(numFavorites, expectedNumFavorites);
    }
}
