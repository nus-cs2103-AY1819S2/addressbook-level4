package quickdocs.model.consultation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import quickdocs.testutil.Assert;

public class SymptomTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Symptom(null));
    }

    @Test
    public void invalidSymptoms() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Symptom(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Symptom(" "));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Symptom(",&/*"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Symptom("Coughing&"));
    }

    @Test
    public void equals() {
        Symptom symptom1 = new Symptom("Constant coughing");
        assertTrue(symptom1.equals(symptom1));

        Symptom symptom2 = new Symptom("Constant coughing");
        assertTrue(symptom1.equals(symptom2));

        Symptom symptom3 = new Symptom("Runny nose");
        assertFalse(symptom1.equals(symptom3));

        assertFalse(symptom1.equals("1"));
    }
}
