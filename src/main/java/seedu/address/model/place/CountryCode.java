package seedu.address.model.place;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;

/**
 * Represents a Place's country code in TravelBuddy.
 * Guarantees: immutable; is valid as declared in {@link #isValidCountryCode(String)}
 */
public class CountryCode {

    public static final String MESSAGE_CONSTRAINTS = "Country codes should only contain a three-letter alphabets";
    public final String code;

    /**
     * Constructs a {@code CountryCode}.
     *
     * @param countryCode A valid country code.
     */
    public CountryCode(String countryCode) {
        requireNonNull(countryCode);
        checkArgument(isValidCountryCode(countryCode), MESSAGE_CONSTRAINTS);
        this.code = countryCode.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid country code.
     */
    public static boolean isValidCountryCode(String test) {
        if (!(test instanceof String)) {
            throw new java.lang.NullPointerException();
        }
        String[] allCountries = Locale.getISOCountries();

        for (String countryCode : allCountries) {
            Locale obj = new Locale("", countryCode);

            if (obj.getISO3Country().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CountryCode // instanceof handles nulls
            && code.equals(((CountryCode) other).code)); // state check
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

}
