package seedu.address.model.tag;

import java.util.HashMap;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Stores all the mapping from the tagName to its color
 */
public class TagColorsMapping {
	public static final String COLOR_CONSTRAINTS = "Color is not supported";
	private static final String[] TAG_COLOR_STYLES = { "red", "yellow", "blue", "green", "grey", "magenta", "pink" };

	private final HashMap<String, String> colorMapping;

	public TagColorsMapping(HashMap<String, String> colorMapping) {
		this.colorMapping = colorMapping;
	}

	/**
	 * Add a mapping for a tagName to a tagColor
	 * @param tagName the name to be tagged
	 * @param tagColor the color that we want
	 */
	public void addTagColor(String tagName, String tagColor) {
		checkArgument(isValidColor(tagName), COLOR_CONSTRAINTS);
		colorMapping.put(tagName, tagColor);
	}

	/**
	 * Check that the given color is valid.
	 */
	private boolean isValidColor(String test) {
		for (String color: TAG_COLOR_STYLES) {
			if(color.equals(test)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retrieves the color for the specific tag. If it does not have a mapping,
	 * a default will be generated using the hashcode.
	 *
	 * @param tagName the name of the tag
	 * @return the color of the tag
	 */
	public String getTagColor(String tagName) {
		return colorMapping.get(tagName);
	}
}
