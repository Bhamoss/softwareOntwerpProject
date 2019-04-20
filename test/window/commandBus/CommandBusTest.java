package window.commandBus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tablr.TablesHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CommandBus.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class CommandBusTest {

    CommandBus bus = null;
    TestCommand testCommand = null;
    SubTestCommand subTestCommand = null;
    TestWidget testWidget = null;
    TestWindowCompositor testWindowCompositor = null;

    @BeforeEach
    void setUp() {
        bus = new CommandBus();
        testCommand = new TestCommand();
        subTestCommand = new SubTestCommand();
        testWidget = new TestWidget();
        testWindowCompositor = new TestWindowCompositor();
    }

    @AfterEach
    void tearDown() {
        // checking if getMethods returns overriden methods
        Method[] k =SubTablesHandler.class.getMethods();
        Method[] kt =TablesHandler.class.getMethods();
        List<Method> l = new ArrayList<>();
        for (Method method :
                k) {
            if (method.getName().equals("getTableIds")) l.add(method);
        }
        List<Method> lt = new ArrayList<>();
        for (Method method :
                kt) {
            if (method.getName().equals("getTableIds")) lt.add(method);
        }
        int i = 0;
    }

    // Only basic test are done here, because tests for validity of the subscription methods are tested in subscriptionTest

    @Test
    @DisplayName("subscribe() IllegalArgument Exception no subscription methods.")
    void subscribeIllegalNoSubscriberMethods() {
        assertThrows(IllegalArgumentException.class, () -> bus.subscribe(new TablesHandler()));
        try
        {
            bus.subscribe(new TablesHandler());
        }
        catch(IllegalArgumentException e)
        {
            assertEquals("The object does not have subscription methods.", e.getMessage());
        }
    }


    @Test
    @DisplayName("subscribe() IllegalArgument Exception.")
    void subscribeIllegal() {
        assertThrows(IllegalArgumentException.class, () -> bus.subscribe(new SubTablesHandler()));
        try
        {
            bus.subscribe(new SubTablesHandler());
        }
        catch(IllegalArgumentException e)
        {
            assertEquals("The subscriber is invalid.", e.getMessage());
        }
    }

    @Test
    @DisplayName("subscribe() IllegalArgumentException already subscribed.")
    void subscribeDoubleSubWidget() {
        bus.subscribe(testWidget);
        assertThrows(IllegalArgumentException.class, () -> bus.subscribe(testWidget));
        try {
            bus.subscribe(testWidget);
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("The subscriber is already subscribed.", e.getMessage());
        }
    }

    @Test
    @DisplayName("subscribe() success for widget.")
    void subscribeWidget() {
        bus.subscribe(testWidget);
    }


    @Test
    @DisplayName("subscribe() success for WIndowCompositor.")
    void subscribeWindowCompositor() {
        bus.subscribe(testWindowCompositor);
    }

    @Test
    @DisplayName("subscribe() success for cache.")
    void subscribeUsingCache() {
        long start = System.nanoTime();
        bus.subscribe(testWidget);
        long mid = System.nanoTime();
        bus.subscribe(new TestWidget());
        long end = System.nanoTime();
        long speedup = Math.round(((double) mid - start)/((double) end - mid)*100) - 100;
        System.out.println("Subscribe time speedup in %:");
        System.out.println(speedup);
    }

    @Test
    @DisplayName("unsubscribe() before class subscribe.")
    void unsubscribeBeforeClassSubscribe() {
        assertThrows(IllegalArgumentException.class, () -> bus.unsubscribe(testWidget));
        try {
            bus.unsubscribe(testWidget);
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("This class has not been subscribed.", e.getMessage());
        }
    }

    /*
    @Test
    @DisplayName("unsubscribe() before subscribe, no command list.")
    void unsubscribeBeforeSubscribeNoCommand() {
        TestWidget t = new TestWidget();
        bus.subscribe(t);
        bus.unsubscribe(t);
        assertThrows(IllegalArgumentException.class, () -> bus.unsubscribe(testWidget));
        try {
            bus.unsubscribe(testWidget);
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("The subscriber is not subscribed (no command).", e.getMessage());
        }
    }
    */

    @Test
    @DisplayName("unsubscribe() before subscribe.")
    void unsubscribeBeforeSubscribe() {
        bus.subscribe(new TestWidget());
        assertThrows(IllegalArgumentException.class, () -> bus.unsubscribe(testWidget));
        try {
            bus.unsubscribe(testWidget);
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("The subscriber is not subscribed.", e.getMessage());
        }
    }


    @Test
    @DisplayName("unsubscribe() after subscribe.")
    void unsubscribeAfterSubscribe() {
        bus.subscribe(testWidget);
        bus.unsubscribe(testWidget);
        assertThrows(IllegalArgumentException.class, () -> bus.unsubscribe(testWidget));
    }


    @Test
    @DisplayName("lots of subscribe and unsubscribe.")
    void subscribeAndUnsubscribe() {
        int subs = 10000;
        List<TestWidget> ws = new LinkedList<>();
        long start = System.nanoTime();
        for (int i = 0; i < subs; i++) {
            ws.add(new TestWidget());
            bus.subscribe(ws.get(i));
        }
        long subTime = System.nanoTime();
        for (int i = 0; i < subs; i++) {
            bus.unsubscribe(ws.get(i));
        }
        long unsubTime = System.nanoTime();
        System.out.println("Subscribe time "+Integer.toString(subs)+" widgets in ms:");
        System.out.println(Math.round((subTime - start)/1000000.0));
        System.out.println("Unsubscribe time "+Integer.toString(subs)+" widgets in ms:");
        System.out.println(Math.round((unsubTime - subTime)/1000000.0));
    }

    @Test
    @DisplayName("post() change")
    void postChange() {
        bus.subscribe(testWidget);
        bus.post(testCommand);
        assertEquals(testCommand.t, testWidget.testvar);
    }


    @Test
    @DisplayName("post() no change")
    void postNoChange() {
        testWidget.setTestvarTen();
        bus.post(subTestCommand);
        assertEquals(10 , testWidget.testvar);
    }
}