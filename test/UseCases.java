import org.junit.jupiter.api.*;
import ui.CanvasWindow;
import ui.UIWindowHandler;


import java.io.File;
import java.lang.reflect.InvocationTargetException;


/**
 * @author Thomas Bamelis
 *
 * @version 0.0.1
 *
 *
 */
public class UseCases {

    private UIWindowHandler ui = new UIWindowHandler();

    @BeforeEach
    void setUp() {
        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ui = new UIWindowHandler();
                ui.show();
                ui.loadTablesWindow();
                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("resources/Setup/Setup.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @BeforeAll
    static void initAll() {

    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }

    /*
     ************************************************
     *           Use Case: Create Table
     ************************************************
     */

    @Test
    @DisplayName("Create Table MSS")
    void createTableMSS() throws InvocationTargetException, InterruptedException {
        java.awt.EventQueue.invokeAndWait(() -> {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("resources/CreateTable/CreateTable.txt").getFile());
            CanvasWindow.replayRecording(file.getAbsolutePath(), ui);
        });
    }

    /*
     ************************************************
     *           Use Case: Edit Table Name
     ************************************************
     */

    @Test
    @DisplayName("Edit Table Name MSS")
    void editTableNameMSS() throws InvocationTargetException, InterruptedException {
        java.awt.EventQueue.invokeAndWait(() -> {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("resources/editTableNameMSS/editTableNameMSS.txt").getFile());
            CanvasWindow.replayRecording(file.getAbsolutePath(), ui);
        });
    }

    @Test
    @DisplayName("Edit Table Name 5a")
    void editTableName5a() throws InvocationTargetException, InterruptedException {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/EditTableName5a/EditTableName5a.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
            });
    }

    @Test
    @DisplayName("Edit Table Name 6a")
    void editTableName6a() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/EditTableName6a/EditTableName6a.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }

    /*
     ************************************************
     *           Use Case: Delete Table
     ************************************************
     */

    @Test
    @DisplayName("Delete Table MSS")
    void deleteTableMSS() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/DeleteTable/DeleteTable.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }

    /*
     ************************************************
     *           Use Case: Open Table
     ************************************************
     */

    @Test
    @DisplayName("Open Table Empty")
    void openTableEmpty() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/OpenTableEmpty/OpenTableEmpty.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }

    @Test
    @DisplayName("Open Table Not Empty")
    void openTableNotEmpty() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/SetupNotEmpty/SetupNotEmpty.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);

        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("resources/OpenTableNotEmpty/OpenTableNotEmpty.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }

    /*
     ************************************************
     *           Use Case: Add Column
     ************************************************
     */

    @Test
    @DisplayName("Add Column")
    void addColumn() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/SetupDesignMode/SetupDesignMode.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);

        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("resources/AddColumn/AddColumn.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }

    /*
     ************************************************
     *      Use Case: Edit Column Characteristics
     ************************************************
     */

    @Test
    @DisplayName("Edit Column Characteristics MSS")
    void editColumnCharacteristicsMSS() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/SetupDesignMode/SetupDesignMode.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);

        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("resources/EditColumnCharacteristicsMSS/EditColumnCharacteristicsMSS.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }

    @Test
    @DisplayName("Edit Column Characteristics 1a")
    void editColumnCharacteristics1a() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/SetupDesignMode/SetupDesignMode.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);

        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("resources/EditColumnCharacteristics1a/EditColumnCharacteristics1a.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }

    @Test
    @DisplayName("Edit Column Characteristics 1b")
    void editColumnCharacteristics1b() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/SetupDesignMode/SetupDesignMode.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);

        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("resources/EditColumnCharacteristics1b/EditColumnCharacteristics1b.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }

    @Test
    @DisplayName("Edit Column Characteristics 1c")
    void editColumnCharacteristics1c() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/SetupDesignMode/SetupDesignMode.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);

        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("resources/EditColumnCharacteristics1c/EditColumnCharacteristics1c.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }

    /*
     ************************************************
     *           Use Case: Delete Column
     ************************************************
     */

    @Test
    @DisplayName("Delete Column")
    void deleteColumn() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/SetupDesignMode/SetupDesignMode.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);

        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("resources/DeleteColumn/DeleteColumn.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }

    /*
     ************************************************
     *           Use Case: Add Row
     ************************************************
     */

    @Test
    @DisplayName("Add Row")
    void addRow() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/SetupRowsMode/SetupRowsMode.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);

        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("resources/AddRow/AddRow.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }

    /*
     ************************************************
     *           Use Case: Edit Row Value
     ************************************************
     */

    @Test
    @DisplayName("Edit Row Value MSS")
    void editRowValueMSS() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/SetupRowsMode/SetupRowsMode.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);

        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("resources/EditRowValueMSS/EditRowValueMSS.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }

    /*
     ************************************************
     *           Use Case: Delete Row
     ************************************************
     */

    @Test
    @DisplayName("Delete Row")
    void deleteRow() throws InvocationTargetException, InterruptedException
    {
        java.awt.EventQueue.invokeAndWait(() -> {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/SetupRowsMode/SetupRowsMode.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);

        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("resources/DeleteRow/DeleteRow.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
        });
    }


}
