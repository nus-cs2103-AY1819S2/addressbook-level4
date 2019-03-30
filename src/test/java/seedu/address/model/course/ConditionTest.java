package seedu.address.model.course;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ConditionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        String[] nullArray = null;
        Assert.assertThrows(NullPointerException.class, () -> new Condition(1, null,
                "ValidRegex"));
        Assert.assertThrows(NullPointerException.class, () -> new Condition(1, "Valid Name",
                nullArray));
        Assert.assertThrows(NullPointerException.class, () -> new Condition(1, "Valid Name",
                "REGEX", null));
        Assert.assertThrows(NullPointerException.class, () -> new Condition(null,
                "ValidRegex", "SomeOtherValidRegex"));
        Assert.assertThrows(NullPointerException.class, () -> new Condition("Valid Name",
                "Something", null));
    }

    @Test
    public void constructor_invalidMinToSatisfy_throwsInvalidArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Condition(0, "ValidName", "Regex1", "Regex2"));
    }

    @Test
    public void constructor_invalidRegex_throwsInvalidArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(1, "ValidName",
                "SomeValidRegex", "Regex{Containing,Comma]"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(1, "ValidName",
                "regexValid", "regexThatDoesNotContainComma", "[UnclosedRegex)"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition("Name can contain commas,",
                "[3-but not}", "regexes"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition("ValidName",
                "regexValid", "regexThatDoesNotContainComma", "AnotherUnclosed(Regex"));
    }

    @Test
    public void constructor_invalidRegexesSize_throwsInvalidArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(1, "ValidName",
                new String[0]));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition("ValidName",
                new String[0]));
    }


}
