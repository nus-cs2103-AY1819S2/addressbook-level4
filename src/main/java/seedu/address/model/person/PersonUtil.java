package seedu.address.model.person;

import seedu.address.model.tag.SkillsTag;

import java.util.Set;

/**
 * Space for general methods relating to the Person class
 */
public class PersonUtil {

    /**
     * counts the number of tags of a certain type denoted by the prefix
     *  Accepted prefixes: "s" = skills; "p" = positions; "e" = endorsements
     */
    public static int getTagTypeNumber(Set<SkillsTag> individualTags, String prefix) {
        int count = 0;
        for (SkillsTag tag : individualTags) {
            String tagString = tag.toString();
            //first element of string is "["
            if (tagString.substring(1, 2).equals(prefix)) {
                count += 1;
            }
        }
        return count;
    }
}
