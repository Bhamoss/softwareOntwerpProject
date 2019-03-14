import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Thomas Bamelis
 *
 * @version 0.0.1
 *
 *
 */
public class UseCases {

    private UIWindowHandler ui;

    @BeforeEach
    void setUp() {
        ui = new UIWindowHandler();
        ui.show();
        ui.loadTablesWindow();
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
    void creatTableMSS()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("CreateTable.txt").getFile());
        ui.load(file.getAbsolutePath());
    }

}
