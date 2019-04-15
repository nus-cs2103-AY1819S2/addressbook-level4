package seedu.address.model.project;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class UserStoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new UserStory(null, null,
                null, null));
    }

    @Test
    public void constructor_invalidUserStory_throwsIllegalArgumentException() {
        String invalidUser = "";
        String invalidFunction = "";
        String invalidImportance = "";
        String validReason = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new UserStory(
                new UserStoryImportance(invalidImportance), new UserStoryUser(invalidUser),
                new UserStoryFunction(invalidFunction), new UserStoryReason(validReason)));

    }


    @Test
    public void isValidUserStory() {
        // null fields
        Assert.assertThrows(NullPointerException.class, () -> UserStory.isValidUserStory(null));

        //invalid importance greater than 3
        Assert.assertThrows(IllegalArgumentException.class, () -> new UserStory(new UserStoryImportance("5"),
                new UserStoryUser("user"), new UserStoryFunction("function"), new UserStoryReason("reason")));

        //invalid importance lesser than 1
        Assert.assertThrows(IllegalArgumentException.class, () -> new UserStory(new UserStoryImportance("0"),
                new UserStoryUser("user"), new UserStoryFunction("function"), new UserStoryReason("reason")));

        //invalid user empty field
        Assert.assertThrows(IllegalArgumentException.class, () -> new UserStory(new UserStoryImportance("2"),
                new UserStoryUser(" "), new UserStoryFunction("function"), new UserStoryReason("reason")));

        // valid fields
        assertTrue(UserStory.isValidUserStory(new UserStory(new UserStoryImportance("2"),
                new UserStoryUser("user"), new UserStoryFunction("function"), new UserStoryReason("reason"))));

        //without reason -> still valid
        assertTrue(UserStory.isValidUserStory(new UserStory(new UserStoryImportance("2"),
                new UserStoryUser("user"), new UserStoryFunction("function"), new UserStoryReason(""))));

        //valid field minimum
        assertTrue(UserStory.isValidUserStory(new UserStory(new UserStoryImportance("2"),
                new UserStoryUser("a"), new UserStoryFunction("a"), new UserStoryReason(""))));

    }
}
