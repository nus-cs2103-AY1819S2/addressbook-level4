package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.restaurant.categories.Categories;

/**
 * A custom text field component that supports autocomplete for categories.
 */
public class CategoriesAutoCompleteTextField extends TextField {

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private ContextMenu suggestions;

    public CategoriesAutoCompleteTextField() {
        super();
        suggestions = new ContextMenu();
        textProperty().addListener((unused1, unused2, unused3) -> showAutoComplete());
    }

    /**
     * Hides the autocomplete context menu.
     */
    public void hideAutoComplete() {
        logger.fine("hiding autocomplete suggestions");
        suggestions.hide();
    }

    /**
     * Shows the autocomplete context menu if conditions are satisfied.
     */
    private void showAutoComplete() {
        TextField current = CategoriesAutoCompleteTextField.this;

        Optional<PrefixInputPair> recentPrefixInputOptional = getRecentlyEnteredPrefix(getText());
        if (recentPrefixInputOptional.isPresent()) {
            PrefixInputPair pair = recentPrefixInputOptional.get();
            List<String> retrievedSuggestions = retrieveRelevantSuggestions(pair);
            List<MenuItem> menuItemsToBeAdded = convertSuggestionsToMenuItems(retrievedSuggestions);
            suggestions.getItems().clear();
            suggestions.getItems().addAll(menuItemsToBeAdded);
            logger.fine("showing autocomplete suggestions");
            suggestions.show(CategoriesAutoCompleteTextField.this, Side.BOTTOM,
                    current.getInputMethodRequests().getTextLocation(0).getX(), 0);
        } else {
            hideAutoComplete();
        }
    }

    /**
     * Retrieves the suggestions based on what the user has already keyed in for the category prefix.
     */
    private List<String> retrieveRelevantSuggestions(PrefixInputPair pair) {
        requireNonNull(pair);
        logger.fine("prefix detected: " + pair.getPrefix());
        return Categories.getCategoriesSuggestions(pair.getPrefix()).stream()
                .filter(entry -> StringUtil.containsSubstringIgnoreCase(entry, pair.getInput()))
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of suggestions to menu items.
     * Defines the actions on each of the menu items as well.
     */
    private List<MenuItem> convertSuggestionsToMenuItems(List<String> retrievedSuggestions) {
        requireNonNull(retrievedSuggestions);
        return retrievedSuggestions.stream()
                .map(result -> {
                    MenuItem resultMenuItem = new MenuItem(result);
                    resultMenuItem.setOnAction((event) -> {
                        setText(trimInput(getText()) + result);
                        CategoriesAutoCompleteTextField.this.positionCaret(getText().length());
                        hideAutoComplete();
                    });
                    return resultMenuItem;
                }).collect(Collectors.toList());
    }

    /**
     * Trims the input to remove half entered inputs after the last prefix.
     */
    private String trimInput(String input) {
        requireNonNull(input);
        assert(getRecentlyEnteredPrefix(input).isPresent());
        String[] inputTokens = input.split(" ");
        String lastWord = inputTokens[inputTokens.length - 1];
        String trimmedLastWord = lastWord.split("/")[0] + "/";
        inputTokens[inputTokens.length - 1] = trimmedLastWord;
        String result = "";
        for (String token : inputTokens) {
            result = result.concat(token);
            result = result.concat(" ");
        }
        return result.trim();
    }

    /**
     * Get the most recently entered prefix from the input, if any.
     */
    private Optional<PrefixInputPair> getRecentlyEnteredPrefix(String input) {
        requireNonNull(input);
        String[] inputTokens = input.split(" ");
        if (inputTokens.length < 1) {
            return Optional.empty();
        } else {
            String lastWord = inputTokens[inputTokens.length - 1];
            if (lastWord.matches("[a-z]*/[A-Za-z]*")) {
                String[] lastWordTokens = lastWord.split("/");
                String prefix = lastWordTokens[0] + "/";
                String prefixInput = lastWordTokens.length <= 1 ? "" : lastWordTokens[1];
                PrefixInputPair pair = new PrefixInputPair(new Prefix(prefix), prefixInput);
                return Optional.of(pair);
            }
            return Optional.empty();
        }
    }

    /**
     * Represents a prefix and input pair of the last entered argument by the user.
     */
    private static class PrefixInputPair {
        private Prefix prefix;
        private String input;

        PrefixInputPair(Prefix prefix, String input) {
            this.prefix = prefix;
            this.input = input;
        }

        Prefix getPrefix() {
            return prefix;
        }

        String getInput() {
            return input;
        }
    }
}
