package seedu.address.model.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.testutil.Assert;
import seedu.address.testutil.ConditionBuilder;
import seedu.address.testutil.TypicalCondition;
import seedu.address.testutil.TypicalModuleTaken;

public class ConditionTest {

    public static final Condition GER = new ConditionBuilder().withPattern("GER1000[A-Z]?").withMin(2).build();
    public static final List<ModuleInfoCode> MODULE_INFO_CODES = TypicalModuleTaken
            .getTypicalModulesTaken()
            .stream()
            .map(ModuleTaken::getModuleInfoCode)
            .collect(Collectors.toList());

    @Test
    public void constructor_null_throwsNullPointerException() {
        String[] nullArray = null;
        Assert.assertThrows(NullPointerException.class, () -> new Condition(null));
        Assert.assertThrows(NullPointerException.class, () -> new Condition(1, null));
    }

    @Test
    public void constructor_invalidRegex_throwsInvalidArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(1, "(([]})"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition("(InvalidRegex}"));
    }

    @Test
    public void constructor_invalidMinToSatisfy_throwsInvalidArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(-1, "valid regex"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(0, "another valid regex"));
    }

    @Test
    public void equal() {
        Condition geh = TypicalCondition.GEH;
        assertEquals(geh, geh);
        //not equals null
        assertNotEquals(null, geh);
        //not equals other type
        assertNotEquals(geh, 0);

        //not equals when different min
        Condition modifiedGeh = new ConditionBuilder().withMin(3).build();
        assertNotEquals(modifiedGeh, geh);
        //alternate constructor for Condition
        modifiedGeh = new ConditionBuilder().withMin(1).build();
        Condition modifiedGeh2 = new Condition(ConditionBuilder.DEFAULT_PATTERN.toString());
        assertEquals(modifiedGeh, modifiedGeh2);
    }



    @Test
    public void isSatisfied_emptyList_returnFalse() {
        assertFalse(GER.isSatisfied(new ArrayList<>()));
    }

    @Test
    public void isSatisfied_lessThanMinToSatisfy_returnFalse() {
        assertFalse(GER.isSatisfied(MODULE_INFO_CODES));
    }

    @Test
    public void isSatisfied_duplicateModuleInfoCodeThatMatches_returnFalse() {
        List<ModuleInfoCode> testList = new ArrayList<>(MODULE_INFO_CODES);
        testList.add(new ModuleInfoCode("GER1000"));
        assertFalse(GER.isSatisfied(testList));
    }

    @Test
    public void isSatisfied_atLeastMinToSatisfyDitinctModuleInfoCodeThatMatches_returnTrue() {
        List<ModuleInfoCode> testList = new ArrayList<>(MODULE_INFO_CODES);
        testList.add(new ModuleInfoCode("GER1000T"));
        assertTrue(GER.isSatisfied(testList));
    }

    @Test
    public void getNumCompleted_emptyList_returnsZero() {
        assertEquals(0, GER.getNumCompleted(new ArrayList<>()));
    }

    @Test
    public void getNumCompleted_onlyOneModuleInfoCodeMatches_returnsOne() {
        assertEquals(1, GER.getNumCompleted(MODULE_INFO_CODES));
    }

    @Test
    public void getNumCompleted_twoDistinctModuleInfoCodeMatches_returnsTwo() {
        List<ModuleInfoCode> testList = new ArrayList<>(MODULE_INFO_CODES);
        testList.add(new ModuleInfoCode("GER1000T"));
        assertEquals(2, GER.getNumCompleted(testList));
    }

    @Test
    public void getNumCompleted_duplicateModuleInfoCodeThatMatches_returnsOne() {
        List<ModuleInfoCode> testList = new ArrayList<>(MODULE_INFO_CODES);
        testList.add(new ModuleInfoCode("GER1000"));
        assertEquals(1, GER.getNumCompleted(testList));
    }

    @Test
    public void canSatisfy_moduleInfoCodeMatches_returnsTrue() {
        assertTrue(GER.canSatisfy(new ModuleInfoCode("GER1000")));
    }


    @Test
    public void canSatisfy_moduleInfoCodeDoesNotMatches_returnsFalse() {
        assertFalse(GER.canSatisfy(new ModuleInfoCode("GES1000")));
    }

}
