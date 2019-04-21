package ui.commandBus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation for declaring a method as a method which can be activated by an event on an eventBus pattern.
 *
 * @author Thomas Bamelis
 *
 * @version 0.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
}
