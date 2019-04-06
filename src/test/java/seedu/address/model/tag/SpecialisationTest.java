package seedu.address.model.tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SpecialisationTest {

    @Test
    public void isValidSpecialisation() {
        // invalid specialisation
        assertFalse(Specialisation.isValidSpecialisation("")); // empty string
        assertFalse(Specialisation.isValidSpecialisation(" ")); // spaces only
        assertFalse(Specialisation.isValidSpecialisation("Hello")); // Non specialisation
        assertFalse(Specialisation.isValidSpecialisation("general_practice")); // Lowercase
        assertFalse(Specialisation.isValidSpecialisation("General_Practice"));
        // Capitalised only first character

        // valid specialisation
        assertTrue(Specialisation.isValidSpecialisation("GENERAL_PRACTICE"));
        assertTrue(Specialisation.isValidSpecialisation("CARDIOLOGY"));
        assertTrue(Specialisation.isValidSpecialisation("ENDOCRINOLOGY"));
        assertTrue(Specialisation.isValidSpecialisation("NEUROLOGY"));
        assertTrue(Specialisation.isValidSpecialisation("ORTHOPAEDIC"));
        assertTrue(Specialisation.isValidSpecialisation("PAEDIATRIC"));
        assertTrue(Specialisation.isValidSpecialisation("GYNAECOLOGY"));
        assertTrue(Specialisation.isValidSpecialisation("UROLOGY"));
        assertTrue(Specialisation.isValidSpecialisation("PATHOLOGY"));
        assertTrue(Specialisation.isValidSpecialisation("HAEMATOLOGY"));
        assertTrue(Specialisation.isValidSpecialisation("PHYSIOTHERAPY"));
        assertTrue(Specialisation.isValidSpecialisation("OCCUPATIONAL_THERAPY"));
        assertTrue(Specialisation.isValidSpecialisation("ANAESTHESIOLOGY"));
    }

    @Test
    public void parseString() {
        //null specialisation
        Assert.assertThrows(NullPointerException.class, () -> Specialisation
                .parseString(null));

        //empty string
        Assert.assertThrows(IllegalArgumentException.class, () -> Specialisation
                .parseString(""));

        //invalid specialisation
        Assert.assertThrows(IllegalArgumentException.class, () -> Specialisation
                .parseString("not_a_specialisation"));
    }

    @Test
    public void getSpecialisation() {
        //null substring
        Assert.assertThrows(NullPointerException.class, () -> Specialisation
                .getSpecialisation(null));

        // empty substring
        assertEquals(Optional.empty(), Specialisation.getSpecialisation(""));

        // full string
        assertEquals(Specialisation.PHYSIOTHERAPY, Specialisation.getSpecialisation("PHYSIOTHERAPY").get());

        // case insensitive full string
        assertEquals(Specialisation.PHYSIOTHERAPY, Specialisation.getSpecialisation("physiotherapy").get());

        // case insensitive substring
        assertEquals(Specialisation.PHYSIOTHERAPY, Specialisation.getSpecialisation("physio").get());
    }
}
