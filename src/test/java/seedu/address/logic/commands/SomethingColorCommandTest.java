package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccessTwoSoln;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.ClothingTypeValue;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.ColorValue;
import seedu.address.model.apparel.Name;

// @@author PhilipPhil
public class SomethingColorCommandTest {
    private CommandHistory history = new CommandHistory();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();


    @Test
    public void executeEmpty() {
        assertCommandSuccess(new SomethingColorCommand(ColorValue.RED), model, history,
                SomethingColorCommand.MESSAGE_RANDOM_NOT_FOUND, expectedModel);
    }


    @Test
    public void executeWithOneItem() {
        Name name = new Name("Red Shirt");
        Color color = new Color(ColorValue.RED);
        ClothingType clothingType = new ClothingType(ClothingTypeValue.TOP);
        Apparel apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);
        expectedModel.addApparel(apparel);

        String successMessage = SomethingColorCommand.MESSAGE_MESSAGE_SUCCESS + "\n"
                + "Red Shirt Color: RED Type: TOP Status: Clean Usage-count: 0";
        assertCommandSuccess(new SomethingColorCommand(ColorValue.RED), model,
                history, successMessage, expectedModel);

    }


    @Test
    public void executeNotFound() {
        Name name = new Name("Red Shirt");
        Color color = new Color(ColorValue.RED);
        ClothingType clothingType = new ClothingType(ClothingTypeValue.TOP);
        Apparel apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);
        expectedModel.addApparel(apparel);
        name = new Name("Red Belt");
        color = new Color(ColorValue.RED);
        clothingType = new ClothingType(ClothingTypeValue.BELT);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);
        expectedModel.addApparel(apparel);

        assertCommandSuccess(new SomethingColorCommand(ColorValue.BROWN), model, history,
                SomethingColorCommand.MESSAGE_RANDOM_NOT_FOUND, expectedModel);

    }

    @Test
    public void executeTwoSameType() {
        Name name = new Name("White Shirt");
        Color color = new Color(ColorValue.WHITE);
        ClothingType clothingType = new ClothingType(ClothingTypeValue.TOP);
        Apparel apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);
        expectedModel.addApparel(apparel);
        name = new Name("White Pants");
        color = new Color(ColorValue.WHITE);
        clothingType = new ClothingType(ClothingTypeValue.BOTTOM);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);
        expectedModel.addApparel(apparel);

        String successMessage1 = SomethingColorCommand.MESSAGE_MESSAGE_SUCCESS + "\n"
                + "White Shirt Color: WHITE Type: TOP Status: Clean Usage-count: 0";

        String successMessage2 = SomethingColorCommand.MESSAGE_MESSAGE_SUCCESS + "\n"
                + "White Pants Color: WHITE Type: BOTTOM Status: Clean Usage-count: 0";



        assertCommandSuccessTwoSoln(new SomethingColorCommand(ColorValue.WHITE), model,
                history, successMessage1, successMessage2, expectedModel);

    }
}
