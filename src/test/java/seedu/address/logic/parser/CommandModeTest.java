package seedu.address.logic.parser;

import org.junit.Assert;
import org.junit.Test;

public class CommandModeTest {

    @Test
    public void checkMode_healthWorker() {
        Assert.assertEquals(ArgumentTokenizer.checkMode("1"), CommandMode.HEALTH_WORKER);
        Assert.assertEquals(ArgumentTokenizer.checkMode("healthworker"), CommandMode.HEALTH_WORKER);
        Assert.assertEquals(ArgumentTokenizer.checkMode("h"), CommandMode.HEALTH_WORKER);
    }

    @Test
    public void checkMode_request() {
        Assert.assertEquals(ArgumentTokenizer.checkMode("2"), CommandMode.REQUEST);
        Assert.assertEquals(ArgumentTokenizer.checkMode("request"), CommandMode.REQUEST);
        Assert.assertEquals(ArgumentTokenizer.checkMode("r"), CommandMode.REQUEST);
    }

    @Test
    public void checkMode_invalid() {
        Assert.assertEquals(ArgumentTokenizer.checkMode("0"), CommandMode.INVALID);
        Assert.assertEquals(ArgumentTokenizer.checkMode("a"), CommandMode.INVALID);
        Assert.assertEquals(ArgumentTokenizer.checkMode(""), CommandMode.INVALID);
    }
}
