package seedu.address.model.player;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.model.battleship.AircraftCarrierBattleship;
import seedu.address.model.battleship.CruiserBattleship;
import seedu.address.model.battleship.DestroyerBattleship;


public class FleetTest {

    private final Fleet testFleet = new Fleet();

    @Test
    public void constructor_default() {
        Fleet f = new Fleet();
        assertEquals(8, f.getSize());
    }

    @Test
    public void constructor_customArg() {
        Fleet f = new Fleet(10, 10, 10);
        assertEquals(30, f.getSize());
    }

    @Test(
            expected = IllegalArgumentException.class
    )
    public void constructor_invalidInput_throwsIllegalArgumentException() {
        new Fleet(0, 0, 0);
    }

    @Test
    public void testGetSize() {
        assertEquals(8, testFleet.getSize());
    }

    @Test
    public void testGetFleetContents() {
        assertEquals(new Fleet(5, 2, 1).getDeployedFleet(),
                testFleet.getDeployedFleet());
    }

    @Test
    public void testGetDestroyerBattleship() {
        Fleet destroyerOnlyFleet = new Fleet(5, 0, 0);
        assertEquals(destroyerOnlyFleet.getDeployedFleet(),
                testFleet.getListOfDestroyerBattleship());
    }

    @Test
    public void testGetCruiserBattleship() {
        Fleet cruiserOnlyFleet = new Fleet(0, 2, 0);
        assertEquals(cruiserOnlyFleet.getDeployedFleet(),
                testFleet.getListOfCruiserBattleship());
    }

    @Test
    public void testGetAircraftCarrierBattleship() {
        Fleet aircraftCarrierOnlyFleet = new Fleet(0, 0, 1);
        assertEquals(aircraftCarrierOnlyFleet.getDeployedFleet(),
                testFleet.getListOfAircraftCarrierBattleship());
    }

    @Test
    public void testConstructorFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Fleet(0, 0, 0); });
    }

    @Test
    public void testDeployBattleship() {
        Fleet testFleet = new Fleet(1, 1, 1);
        assertThrows(Exception.class, () -> {
            testFleet.deployBattleship(new DestroyerBattleship());
            testFleet.deployBattleship(new DestroyerBattleship()); });

        assertThrows(Exception.class, () -> {
            testFleet.deployBattleship(new CruiserBattleship());
            testFleet.deployBattleship(new CruiserBattleship()); });

        assertThrows(Exception.class, () -> {
            testFleet.deployBattleship(new AircraftCarrierBattleship());
            testFleet.deployBattleship(new AircraftCarrierBattleship()); });
    }

    @Test
    public void testDeployDestroyers() {
        Fleet destroyerOnlyFleet = new Fleet(5, 0, 0);

        // deploying more than available destroyers
        assertThrows(Exception.class, () -> destroyerOnlyFleet.deployDestroyerBattleships(10));

        assertThrows(Exception.class, () -> {
            destroyerOnlyFleet.deployDestroyerBattleships(5);
            destroyerOnlyFleet.deployDestroyerBattleships(1); });

    }

    @Test
    public void testDeployCruisers() {
        Fleet cruiserOnlyFleet = new Fleet(0, 2, 0);

        // deploying more than available destroyers
        assertThrows(Exception.class, () -> cruiserOnlyFleet.deployCruiserBattleships(10));

        assertThrows(Exception.class, () -> {
            cruiserOnlyFleet.deployCruiserBattleships(2);
            cruiserOnlyFleet.deployCruiserBattleships(1); });

    }

    @Test
    public void testDeployAircraftCarriers() {
        Fleet aircraftCarrierOnlyFleet = new Fleet(0, 0, 1);

        // deploying more than available destroyers
        assertThrows(Exception.class, () ->
                aircraftCarrierOnlyFleet.deployAircraftCarrierBattleships(10));

        assertThrows(Exception.class, () -> {
            aircraftCarrierOnlyFleet.deployAircraftCarrierBattleships(1);
            aircraftCarrierOnlyFleet.deployAircraftCarrierBattleships(1); });
    }

    @Test
    public void testGetAttributes() {
        Fleet testFleet = new Fleet(1, 1, 1);
        assertEquals(testFleet.getNumDestroyer(), 1);
        assertEquals(testFleet.getNumCruiser(), 1);
        assertEquals(testFleet.getNumAircraftCarrier(), 1);
    }
}
