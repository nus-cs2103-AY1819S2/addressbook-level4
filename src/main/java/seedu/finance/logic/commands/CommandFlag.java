package seedu.finance.logic.commands;

/**
 * A flag that gives more information to the command.
 * E.g. -name in 'sort -name'.
 */
public class CommandFlag {
    private final String flag;

    public CommandFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public String toString() {
        return getFlag();
    }

    @Override
    public int hashCode() {
        return flag == null ? 0 : flag.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CommandFlag)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        CommandFlag otherCommandFlag = (CommandFlag) obj;
        return otherCommandFlag.getFlag().equals(getFlag());
    }
}
