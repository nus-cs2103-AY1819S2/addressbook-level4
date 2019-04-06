package seedu.finance.commons.events;

/**
 * An event requesting a panel swap with BrowserPanel.
 */
public class SwapBrowserPanelEvent extends BaseEvent {

    /**
     * Represents the two types of panels to swap to.
     */
    public enum PanelType {
        BROWSER, SUMMARY
    }

    private PanelType panelType;

    public SwapBrowserPanelEvent(PanelType type) {
        panelType = type;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public PanelType getPanelType() {
        return panelType;
    }
}

