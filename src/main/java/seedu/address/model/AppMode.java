package seedu.address.model;

/**
 * Represents the operation mode of the application.
 */
public class AppMode {
    private Modes appMode;

    public AppMode () {
        this.init();
    }

    /**
     * Specifies Modes ENUM values for AppMode class.
     */
    public enum Modes {
        MEMBER, ACTIVITY
    }

    /**
     * Initialize appMode to MEMBER.
     */
    public void init () {
        this.appMode = Modes.MEMBER;
    }

    /**
     * Returns true if a appMode is MEMBER.
     */
    public boolean isMember () {
        return this.getAppMode().equals(Modes.MEMBER);
    }

    /**
     * Returns true if a appMode is ACTIVITY
     */
    public boolean isActivity () {
        return this.getAppMode().equals(Modes.ACTIVITY);
    }

    /**
     * Returns value of appMode
     */
    public Modes getAppMode () {
        return appMode;
    }

    /**
     * Sets value of appMode to (@code mode)
     */
    public void setAppMode (Modes mode) {
        appMode = mode;
    }

    /**
     *  Returns true if (@modeStr) is valid string value of Modes ENUM
     *  else return false
     */
    public static boolean modeStringIsValidValue (String modeStr) {
        for (AppMode.Modes mode : AppMode.Modes.values()) {
            if (mode.toString().equalsIgnoreCase(modeStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Returns ENUM value Modes if (@modeStr) is valid ENUM value
     *  else return null
     */
    public static Modes convertStringToModes (String modeStr) {
        for (AppMode.Modes mode : AppMode.Modes.values()) {
            if (mode.toString().equalsIgnoreCase(modeStr)) {
                return mode;
            }
        }
        return null;
    }
}
