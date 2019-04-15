package quickdocs.model.patient;

/**
 * Represent the gender of the patient
 */
public class Gender {
    public static final String REGEX_GENDER = "[MF]";
    public static final String GENDER_CONSTRAINTS = "Only 2 gender, M or F allowed";

    private String gender;

    // empty constructor for json reconstruction
    public Gender() {
    }

    public Gender (String gender) {
        if (!gender.matches(REGEX_GENDER)) {
            throw new IllegalArgumentException(GENDER_CONSTRAINTS);
        }
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return gender;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && gender.equals(((Gender) other).getGender())); // state check
    }
}
