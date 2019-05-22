package ui.commandBus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tablr.TablesHandler;
import ui.UIHandler;
import ui.UIStarter;
import ui.WindowCompositor;
import ui.commands.undoableCommands.*;
import ui.updaters.CellValueUpdater;

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
    TestUpCommand testWidget = null;
    TestWindowCompositor testWindowCompositor = null;

    @BeforeEach
    void setUp() {
        bus = new CommandBus();
        testCommand = new TestCommand();
        subTestCommand = new SubTestCommand();
        testWidget = new TestUpCommand();
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
        bus.subscribe(new TestUpCommand());
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
        bus.subscribe(new TestUpCommand());
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
        List<TestUpCommand> ws = new LinkedList<>();
        long start = System.nanoTime();
        for (int i = 0; i < subs; i++) {
            ws.add(new TestUpCommand());
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


    /*
     *      UNDO / REDO
     */

    @Test
    @DisplayName("Some undos and redos")
    void undosAndRedos(){
        UIStarter starter = new UIStarter(true);
        UIHandler handler = starter.getUIHandler();
        WindowCompositor comp = starter.getCompositor();
        CommandBus bus = starter.getCommandBus();

        // adding and removing a table
        AddTableCommand add = new AddTableCommand(handler, bus, comp);
        add.execute();
        assertEquals(1, handler.getTableIds().size());
        bus.undo();
        assertEquals(0, handler.getTableIds().size());
        bus.redo();
        assertEquals(1, handler.getTableIds().size());

        // setting and unsetting the name
        SetTableNameCommand setName = new SetTableNameCommand((() -> {return "newName";}), 1, handler, bus);
        setName.execute();
        assertEquals("newName", handler.getTableName(1));
        bus.undo();
        assertEquals("Table1", handler.getTableName(1));
        bus.redo();
        assertEquals("newName", handler.getTableName(1));

        // adding another table
        add.execute();

        // removing and adding the first one
        RemoveTableCommand r = new RemoveTableCommand(()->{return 1;}, handler, comp, bus);
        r.execute();
        assertEquals(1, handler.getTableIds().size());
        bus.undo();
        assertEquals(2, handler.getTableIds().size());
        assertEquals("newName", handler.getTableName(1));


        // adding two collumns and 2 rows
        AddColumnCommand col =  new AddColumnCommand(1, handler, comp, bus);
        col.execute();
        col.execute();
        AddRowCommand row = new AddRowCommand(1, handler, comp, bus);
        row.execute();
        row.execute();



        // setting the value of (1,1) to swop
        SetCellValueCommand se = new SetCellValueCommand(1,1,1,()->{return "swop";}, handler, bus,comp);
        se.execute();
        assertEquals("swop", handler.getCellValue(1,1,1));
        bus.undo();
        assertEquals("", handler.getCellValue(1,1,1));
        bus.redo();
        assertEquals("swop", handler.getCellValue(1,1,1));

        // remove table and redo
        r.execute();
        assertEquals(1, handler.getTableIds().size());
        bus.undo();
        assertEquals("swop", handler.getCellValue(1,1,1));
        bus.redo();

        // redo to much
        assertThrows(IllegalStateException.class, () -> bus.redo());


        // overwrite
        bus.undo();
        bus.undo();
        bus.undo();
        assertEquals(1, handler.getNbRows(1));
        bus.redo();
        assertEquals(2, handler.getNbRows(1));
        bus.undo();

        // so there should only be one row left
        se.execute();
        assertThrows(IllegalStateException.class, () -> bus.redo());

        // should not throw error
        bus.undo();
        bus.redo();

        // flush bus and try undo redo on empty history
        bus.flushHistory();
        assertThrows(IllegalStateException.class, () -> bus.undo());
        assertThrows(IllegalStateException.class, () -> bus.redo());

        // undo to much
        add.execute();
        bus.undo();
        assertThrows(IllegalStateException.class, () -> bus.undo());

    }
}