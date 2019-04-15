package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.paint.Color;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.cell.Status;

public class MapTest extends GuiUnitTest {
    private Model model;
    private Map playerMap;

    @Before
    public void setUp() {
        model = new ModelManager();
        playerMap = new PlayerMap(model.getHumanMapObservable(), model.getHumanMapGrid());
    }

    @Test
    public void getColourTest() {
        assertEquals(playerMap.getColor(Status.HIDDEN), Color.CADETBLUE);
        assertEquals(playerMap.getColor(Status.SHIP), Color.BLACK);
        assertEquals(playerMap.getColor(Status.SHIPHIT), Color.ORANGE);
        assertEquals(playerMap.getColor(Status.EMPTY), Color.LIGHTBLUE);
        assertEquals(playerMap.getColor(Status.DESTROYED), Color.RED);
        assertEquals(playerMap.getColor(Status.EMPTYHIT), Color.DARKBLUE);
    }
}
