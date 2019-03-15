import org.junit.jupiter.api.*;
import window.CanvasWindow;
import window.UIWindowHandler;


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
        // close window here
    }

    /*
     ************************************************
     *           Use Case: Create Table
     ************************************************
     */

    @Test
    @DisplayName("Create Table MSS")
    void createTableMSS()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/CreateTable/CreateTable.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
    }

    /*
     ************************************************
     *           Use Case: Edit Table Name
     ************************************************
     */

    @Test
    @DisplayName("Edit Table Name MSS")
    void editTableNameMSS()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/EditTableNameMSS/EditTableNameMSS.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
    }

    @Test
    @DisplayName("Edit Table Name 5a")
    void editTableName5a()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/EditTableName5a/EditTableName5a.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
    }

    @Test
    @DisplayName("Edit Table Name 6a")
    void editTableName6a()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/EditTableName6a/EditTableName6a.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
    }

    /*
     ************************************************
     *           Use Case: Delete Table
     ************************************************
     */

    @Test
    @DisplayName("Delete Table MSS")
    void deleteTableMSS()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/DeleteTable/DeleteTable.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
    }

    /*
     ************************************************
     *           Use Case: Open Table
     ************************************************
     */

    @Test
    @DisplayName("Open Table Empty")
    void openTableEmpty()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/OpenTableEmpty/OpenTableEmpty.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
    }

    @Test
    @DisplayName("Open Table Not Empty")
    void openTableNotEmpty()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/SetupNotEmpty/SetupNotEmpty.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);

        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("resources/OpenTableNotEmpty/OpenTableNotEmpty.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
    }

    /*
     ************************************************
     *           Use Case: Add Column
     ************************************************
     */

    @Test
    @DisplayName("Add Column")
    void addColumn()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("resources/SetupDesignMode/SetupDesignMode.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);

        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("resources/AddColumn/AddColumn.txt").getFile());
        CanvasWindow.replayRecording(file.getAbsolutePath(),ui);
    }


}
