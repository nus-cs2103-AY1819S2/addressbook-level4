package quickdocs.model.patient;

import java.util.regex.Pattern;

/**
 * Represents email address of a particular patient
 */
public class Email {

    public static final String REGEX_EMAIL = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static final String EMAIL_CONSTRAINTS =
            "Emails should follow standard email convention: username@domain";

    private String email;

    // empty constructor for json reconstruction
    public Email() {
    }

    public Email(String email) {
        if (!Pattern.compile(REGEX_EMAIL, Pattern.CASE_INSENSITIVE).matcher(email).find()) {
            throw new IllegalArgumentException(EMAIL_CONSTRAINTS);
        }

        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Email // instanceof handles nulls
                && email.equals(((Email) other).getEmail())); // state check
    }

    public static boolean isValidEmail(String string) {
        return Pattern.compile(REGEX_EMAIL, Pattern.CASE_INSENSITIVE).matcher(string).matches();
    }
}
