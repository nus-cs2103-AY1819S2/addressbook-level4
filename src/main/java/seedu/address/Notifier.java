/* @@author Carrein */

package seedu.address;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Basic listener class used to convey updates to UI elements.
 */
public class Notifier {
    private static PropertyChangeSupport support = new PropertyChangeSupport(Notifier.class);

    public static void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public static void removePropertyChangeListener(PropertyChangeListener l) {
        support.removePropertyChangeListener(l);
    }

    public static void firePropertyChangeListener(String propertyName, Object oldValue, Object newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }
}
