package ui.commandBus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tablr.TablesHandler;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Subscription.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class SubscriptionTest {

    Subscription basicSub = null;
    SubWidget otherWidget = null;
    SubCommand basicCommand = null;
    SubWidget basicWidget = null;
    BrotherWidget brotherWidget = null;
    BrotherCommand brotherCommand = null;
    SubclassSubWidget subclassSubWidget = null;
    SubWindowCompositor subCom = null;

    Method noSubscribe = null;
    Method twoMethod = null;
    Method intMethod = null;
    Method privMethod = null;
    Method validMethod = null;
    Method brotherMethod = null;
    Method validSubCom = null;

    String f = null;

    @BeforeEach
    void setUp() {
        basicWidget = new SubWidget();
        Method[] methods = basicWidget.getClass().getDeclaredMethods();
        for (Method method:
             methods) {
            if (method.getName().equals("execute"))
            {
                noSubscribe = method;
            }
            else if (method.getName().equals("privMethod"))
            {
                privMethod = method;
            }
            else if (method.getName().equals("twoMethod"))
            {
                twoMethod = method;
            }
            else if (method.getName().equals("intMethod"))
            {
                intMethod = method;
            }
            else
            {
                validMethod = method;
            }
        }
        basicSub = new Subscription(basicWidget, validMethod);
        otherWidget = new SubWidget();
        basicCommand = new SubCommand();
        brotherWidget = new BrotherWidget();
        methods = brotherWidget.getClass().getDeclaredMethods();
        for (Method method:
                methods) {
            if (method.getName().equals("notSubWidgetMethod"))
            {
                brotherMethod = method;
            }
        }
        brotherCommand = new BrotherCommand();
        subclassSubWidget = new SubclassSubWidget();

        methods = SubWindowCompositor.class.getDeclaredMethods();
        for (Method method:
                methods) {
            if (method.getName().equals("valid"))
            {
                validSubCom = method;
            }
        }
        subCom = new SubWindowCompositor();
    }

    @AfterEach
    void tearDown() {
    }



    /*
     * CONSTRUCTOR
     */

    @Test
    @DisplayName("Constructor subscriber null.")
    void constructorSubscriberNull() {
        assertThrows(IllegalArgumentException.class, () -> new Subscription(null, noSubscribe));
    }


    @Test
    @DisplayName("Constructor subscriber invalid class.")
    void constructorSubscriberInvalidClass() {
        assertThrows(IllegalArgumentException.class, () -> new Subscription(new TablesHandler(), noSubscribe));
        try
        {
            new Subscription(new TablesHandler(), noSubscribe);
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("The subscriber is invalid.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Constructor onEvent null.")
    void constructorOnEventNull() {
        assertThrows(IllegalArgumentException.class, () -> new Subscription(basicWidget, null));
    }

    @Test
    @DisplayName("Constructor onEvent no annotation.")
    void constructorOnEventNoAnnotation() {
        assertThrows(IllegalArgumentException.class, () -> new Subscription(basicWidget, noSubscribe));
    }

    @Test
    @DisplayName("Constructor onEvent not public.")
    void constructorOnEventNotPublic() {
        assertThrows(IllegalArgumentException.class, () -> new Subscription(basicWidget, privMethod));
    }


    @Test
    @DisplayName("Constructor onEvent 2 parameters.")
    void constructorOnEventTwoParams() {
        assertThrows(IllegalArgumentException.class, () -> new Subscription(basicWidget, twoMethod));
    }

    @Test
    @DisplayName("Constructor onEvent parameter not UICommand.")
    void constructorOnEventNotUICommand() {
        assertThrows(IllegalArgumentException.class, () -> new Subscription(basicWidget, intMethod));
    }

    @Test
    @DisplayName("Constructor onEvent not a method reachable by the widget.")
    void constructorOnEventNotReachable() {
        assertThrows(IllegalArgumentException.class, () -> new Subscription(basicWidget, brotherMethod));
    }


    @Test
    @DisplayName("Constructor success superclass method.")
    void constructorSuccessParentListen() {
        Subscription s = new Subscription(subclassSubWidget, validMethod);
    }


    @Test
    @DisplayName("Constructor success with Widget.")
    void constructorSuccessWidget() {
        basicSub = new Subscription(basicWidget, validMethod);
    }


    @Test
    @DisplayName("Constructor success with WindowCompositor.")
    void constructorSuccessWindowCompositor() {
        basicSub = new Subscription(subCom, validSubCom);
    }


    /*
     * void trigger(UICommand command) throws IllegalArgumentException
     */


    @Test
    @DisplayName("Trigger null IllegalArgumentException.")
    void triggerNull() {
        assertThrows(IllegalArgumentException.class, () -> basicSub.trigger(null));
    }

    @Test
    @DisplayName("Trigger not a parameter subclass IllegalArgumentException.")
    void triggerNoUICommand() {
        assertThrows(IllegalArgumentException.class, () -> basicSub.trigger(brotherCommand));
    }

    @Test
    @DisplayName("Trigger success.")
    void triggerSuccess() {
        basicSub.trigger(basicCommand);
        assertEquals(basicCommand.t, basicWidget.testvar);
    }

    /*
     * boolean isSubscriber(Widget widget)
     */


    @Test
    @DisplayName("isSubscriber null")
    void isSubscriberNull() {
        assertFalse(basicSub.isSubscriber(null));
    }



    @Test
    @DisplayName("Constructor subscriber wrong class.")
    void constructorWrongSubscriber() {
        assertThrows(IllegalArgumentException.class, () -> new Subscription(new Integer(1302), validMethod));
    }


    @Test
    @DisplayName("isSubscriber not the widget")
    void isSubscriberOtherWidget() {
        assertFalse(basicSub.isSubscriber(otherWidget));
    }

    @Test
    @DisplayName("isSubscriber success")
    void isSubscriberSuccess() {
        assertTrue(basicSub.isSubscriber(basicWidget));
    }
}