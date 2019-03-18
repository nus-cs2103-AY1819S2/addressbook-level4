package seedu.address.logic.parser;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
<<<<<<< HEAD
 * E.g. 't/' in 'add James t/ friend'.
=======
 * E.g. 'n/' in 'addLesson n/Capitals of the world c/Country c/Capital o/Hint'.
>>>>>>> 9796a678da6bc293ec34cf45dac1be7d8be3ce1b
 */
public class Prefix {
    private final String prefix;

    public Prefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String toString() {
        return getPrefix();
    }

    @Override
    public int hashCode() {
        return prefix == null ? 0 : prefix.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Prefix)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Prefix otherPrefix = (Prefix) obj;
        return otherPrefix.getPrefix().equals(getPrefix());
    }
}
