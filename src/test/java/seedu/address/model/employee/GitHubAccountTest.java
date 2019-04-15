package seedu.address.model.employee;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class GitHubAccountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new GitHubAccount(null));
    }

    @Test
    public void constructor_invalidGithub_throwsIllegalArgumentException() {
        String invalidGitHub = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new GitHubAccount(invalidGitHub));
    }

    @Test
    public void isValidGithub() {
        // null github
        Assert.assertThrows(NullPointerException.class, () -> GitHubAccount.isValidAccount(null));

        // invalid githubs
        assertFalse(GitHubAccount.isValidAccount("")); // empty string
        assertFalse(GitHubAccount.isValidAccount(" ")); // spaces only

        // valid githubs
        assertTrue(GitHubAccount.isValidAccount("spongebook"));
        assertTrue(GitHubAccount.isValidAccount("a")); // one character
        assertTrue(GitHubAccount.isValidAccount("sponge123patrick321"));
    }
}
