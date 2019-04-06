package seedu.address.model.tag;

import java.util.Optional;

/**
 * Class that represents a specialisation for a HealthWorker object.
 */
public enum Specialisation {

    GENERAL_PRACTICE,
    CARDIOLOGY,
    ENDOCRINOLOGY,
    NEUROLOGY,
    ORTHOPAEDIC,
    PAEDIATRIC,
    GYNAECOLOGY,
    UROLOGY,
    PATHOLOGY,
    HAEMATOLOGY,
    PHYSIOTHERAPY,
    OCCUPATIONAL_THERAPY,
    ANAESTHESIOLOGY;

    public static final String MESSAGE_CONSTRAINTS = getMessageConstraints();

    /**
     * Displays a list of available specialisations.
     * @return String describing all available specialisations.
     */
    public static String getSpecialisationList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Specialisation specialisation : Specialisation.values()) {
            stringBuilder.append(specialisation.toString()).append("\n");
        }

        return stringBuilder.toString();
    }

    /**
     * Checks if a given input string matches any of the available
     * Specialisations
     * @param input string to check for valid Specialisation.
     * @return true if the input string has a matching Specialisation, else
     * false.
     */
    public static boolean isValidSpecialisation(String input) {
        try {
            Specialisation.valueOf(input);
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }

        return true;
    }

    /**
     * Returns the corresponding matching Specialisation given an input String
     * @param input string to match Specialisations.
     * @return Specialisation matching the given string.
     */
    public static Specialisation parseString(String input) {
        return Specialisation.valueOf(input);
    }

    /**
     * Returns an Optional containing the matching Specialisation to the substring if any, else an empty Optional
     * @param input the substring to match against the Specialisation names
     * @return Optional containing the matching Specialisation, else an empty Optional
     */
    public static Optional<Specialisation> getSpecialisation (String input) {
        if (input.isEmpty()) {
            return Optional.empty();
        }

        for (Specialisation specialisation : Specialisation.values()) {
            if (specialisation.name().toLowerCase().contains(input.toLowerCase())) {
                return Optional.of(specialisation);
            }
        }

        return Optional.empty();
    }

    /**
     * Formulates the String representing the valid specialisations and
     * constraints.
     * @return String containing the valid specialisations to be added.
     */
    private static String getMessageConstraints() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Valid specialisations: \n");
        stringBuilder.append(getSpecialisationList());

        return stringBuilder.toString();
    }

}
