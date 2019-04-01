package seedu.equipment.model.equipment;

import java.util.List;
import java.util.function.Predicate;

import seedu.equipment.commons.util.StringUtil;

/**
 * Tests that a {@code Equipment}'s {@code Name} matches any of the keywords given.
 */
public class EquipmentContainsKeywordsPredicate implements Predicate<Equipment> {
    private final List<String> nameKeywords;
    private final List<String> addressKeywords;
    private final List<String> dateKeywords;
    private final List<String> phoneKeywords;
    private final List<String> tagKeywords;
    private final List<String> serialNumberKeywords;


    public EquipmentContainsKeywordsPredicate(List<String> nameKeywords, List<String> addressKeywords,
                                              List<String> dateKeywords, List<String> phoneKeywords,
                                              List<String> tagKeywords, List<String> serialNumberKeywords) {
        this.nameKeywords = nameKeywords;
        this.addressKeywords = addressKeywords;
        this.dateKeywords = dateKeywords;
        this.phoneKeywords = phoneKeywords;
        this.tagKeywords = tagKeywords;
        this.serialNumberKeywords = serialNumberKeywords;
    }

    @Override
    public boolean test(Equipment equipment) {
//        return keywords.stream()
//                .anyMatch(keyword ->
//                        StringUtil.containsWordIgnoreCase(equipment.getSerialNumber().serialNumber, keyword)
//                                || StringUtil.containsWordIgnoreCase(equipment.getDate().value, keyword)
//                                || StringUtil.containsWordIgnoreCase(equipment.getTags().toString(), keyword)
//                                || StringUtil.containsWordIgnoreCase(equipment.getPhone().value, keyword)
//
//                                || StringUtil.containsWordIgnoreCase(equipment.getAddress().value, keyword)
//                                || StringUtil.containsWordIgnoreCase(equipment.getName().name, keyword)
//                                || StringUtil.containsWordIgnoreCase(equipment.getTags().toString(), keyword)
//                                || StringUtil.containsPartialWordIgnoreCase(equipment.getSerialNumber().serialNumber,
//                                                                                        keyword)
//                                || StringUtil.containsPartialWordIgnoreCase(equipment.getAddress().value, keyword)
//                                || StringUtil.containsPartialWordIgnoreCase(equipment.getName().name, keyword)
//                                || StringUtil.containsPartialWordIgnoreCase(equipment.getTags().toString(), keyword)
//                                || StringUtil.containsPartialWordIgnoreCase(equipment.getPhone().value, keyword));
        return testAllMatch(equipment.getName().name, nameKeywords)
                && testAllMatch(equipment.getAddress().value, addressKeywords)
                && testAllMatch(equipment.getDate().value, dateKeywords)
                && testAllMatch(equipment.getPhone().value, phoneKeywords)
                && testAllMatch(equipment.getTags().toString(), tagKeywords)
                && testAllMatch(equipment.getSerialNumber().serialNumber, serialNumberKeywords)
                && testAnyMatch(equipment.getName().name, nameKeywords)
                && testAnyMatch(equipment.getAddress().value, addressKeywords)
                && testAnyMatch(equipment.getDate().value, dateKeywords)
                && testAnyMatch(equipment.getPhone().value, phoneKeywords)
                && testAnyMatch(equipment.getTags().toString(), tagKeywords)
                && testAnyMatch(equipment.getSerialNumber().serialNumber, serialNumberKeywords);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EquipmentContainsKeywordsPredicate // instanceof handles nulls
                && nameKeywords.equals(((EquipmentContainsKeywordsPredicate) other).nameKeywords)
                && addressKeywords.equals(((EquipmentContainsKeywordsPredicate) other).addressKeywords)
                && dateKeywords.equals(((EquipmentContainsKeywordsPredicate) other).dateKeywords)
                && phoneKeywords.equals(((EquipmentContainsKeywordsPredicate) other).phoneKeywords)
                && tagKeywords.equals(((EquipmentContainsKeywordsPredicate) other).tagKeywords)
                && serialNumberKeywords.equals(((EquipmentContainsKeywordsPredicate) other).serialNumberKeywords)
        ); // state check
    }
    /** Returns true if all keyword is found in the sentence, case insensitive. */
    private boolean testAllMatch(String sentence, List<String> keywords) {
        String processedSentence = sentence.toLowerCase();
        return keywords.stream().allMatch(keyword -> processedSentence.contains(keyword.toLowerCase()));
    }

    /** Returns true if any keyword is found in the sentence, case insensitive. */
    private boolean testAnyMatch(String sentence, List<String> keywords) {
        if (keywords.isEmpty()) {
            return true;
        }
        String processedSentence = sentence.toLowerCase();
        return keywords.stream().anyMatch(keyword -> processedSentence.contains(keyword.toLowerCase()));
    }
}
