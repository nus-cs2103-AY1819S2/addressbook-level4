package seedu.address.model.patient;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;

/**
 * Indicates a person's sex: Either Male or Female.
 */
public class Sex {
    public static final String MESSAGE_CONSTRAINTS =
            "Indicating patient's sex is compulsory, denoted by " + PREFIX_SEX + ", and should be male or female";

    public static final String MALE = "M";
    public static final String FEMALE = "F";

    private String sex;

    public Sex(String sex) {
        this.sex = sex.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid Sex.
     * Test is case-insensitive.
     *
     * @param test the string to be tested.
     */
    public static boolean isValidSex(String test) {
        String temp = test.toUpperCase();
        return temp.equals(Sex.MALE) || temp.equals(Sex.FEMALE);
    }

    public String getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "(" + sex + ")";
    }
}
