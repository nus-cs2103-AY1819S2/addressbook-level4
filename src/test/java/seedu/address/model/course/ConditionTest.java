package seedu.address.model.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.ConditionBuilder;
import seedu.address.testutil.TypicalCondition;

public class ConditionTest {

//    @Test
//    public void constructor_null_throwsNullPointerException() {
//        String[] nullArray = null;
//        Assert.assertThrows(NullPointerException.class, () -> new Condition(1, null,
//                "ValidRegex"));
//        Assert.assertThrows(NullPointerException.class, () -> new Condition(1, "Valid Name",
//                nullArray));
//        Assert.assertThrows(NullPointerException.class, () -> new Condition(1, "Valid Name",
//                "REGEX", null));
//        Assert.assertThrows(NullPointerException.class, () -> new Condition(null,
//                "ValidRegex", "SomeOtherValidRegex"));
//        Assert.assertThrows(NullPointerException.class, () -> new Condition("Valid Name",
//                "Something", null));
//    }
//
//    @Test
//    public void constructor_invalidMinToSatisfy_throwsInvalidArgumentException() {
//        Assert.assertThrows(IllegalArgumentException.class, () ->
//                new Condition(0, "ValidName", "Regex1", "Regex2"));
//    }
//
//    @Test
//    public void constructor_invalidRegex_throwsInvalidArgumentException() {
//        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(1, "ValidName",
//                "SomeValidRegex", "Regex{Containing,Comma]"));
//        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(1, "ValidName",
//                "regexValid", "regexThatDoesNotContainComma", "[UnclosedRegex)"));
//        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition("Name can contain commas,",
//                "[3-but not}", "regexes"));
//        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition("ValidName",
//                "regexValid", "regexThatDoesNotContainComma", "AnotherUnclosed(Regex"));
//    }
//
//    @Test
//    public void constructor_invalidRegexesSize_throwsInvalidArgumentException() {
//        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(1, "ValidName",
//                new String[0]));
//        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition("ValidName",
//                new String[0]));
//    }
//
//    @Test
//    public void equal() {
//        Condition ulr = TypicalCondition.ULR;
//        assertEquals(TypicalCondition.ULR, ulr);
//        //not equals null
//        assertNotEquals(null, ulr);
//        //not equals other type
//        assertNotEquals(ulr, 0);
//
//        //not equals when different name
//        Condition modifiedUlr = new ConditionBuilder().withConditionName("ULR").build();
//        assertNotEquals(modifiedUlr, ulr);
//
//        //not equals when different min
//        modifiedUlr = new ConditionBuilder().withMin(2).build();
//        assertNotEquals(modifiedUlr, ulr);
//
//        //alternate constructor for Condition
//        modifiedUlr = new Condition("UNIVERSITY LEVEL REQUIREMENT", ConditionBuilder.DEFAULT_REGEXES);
//        assertEquals(modifiedUlr, ulr);
//
//        //different regex order
//        String[] regexes = {"GEQ1000", "GES[0-9]{4}[A-Z]?", "GET[0-9]{4}[A-Z]?", "GEH[0-9]{4}[A-Z]?",
//            "GER1000"};
//        modifiedUlr = new Condition(5, ConditionBuilder.DEFAULT_CONDITION_NAME, regexes);
//        assertNotEquals(modifiedUlr, ulr);
//
//    }
}
