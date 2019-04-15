package seedu.address.ui;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class EnemyMapTest extends GuiUnitTest {
    private Model model;
    private EnemyMap enemyMap;

    @Before
    public void setUp() {
        model = new ModelManager();
        enemyMap = new EnemyMap(model.getEnemyMapObservable(), model.getEnemyMapGrid());
    }

    // TODO: write UI tests
    @Test
    public void stubTests() {
        model.updateUi();
        enemyMap.getMapView(model.getEnemyMapGrid());
        assertTrue(true);
    }
}
