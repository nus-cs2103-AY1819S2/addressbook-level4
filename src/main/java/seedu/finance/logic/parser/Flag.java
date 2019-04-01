package seedu.finance.logic.parser;

/**
 * A flag that gives more information to the command.
 * E.g. -name in 'sort -name'.
 */
public class Flag {
    private final String flag;

    public Flag(String flag) {
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
        if (!(obj instanceof Flag)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        Flag otherFlag = (Flag) obj;
        return otherFlag.getFlag().equals(getFlag());
    }
}
