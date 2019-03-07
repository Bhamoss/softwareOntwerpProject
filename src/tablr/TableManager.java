package tablr;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has been made to improve cohesion and sacrifice Coupling,
 * mainly because TableHandler would be much more than a ontroller = bloated controller
 * This is pure fabrication.
 *
 * However, coupling has also been much REDUCED because the handler do not communicate with each
 * other anymore.
 */
public class TableManager {


    private List<Table> tables;

    // Easy to coordinate with the handlers
    private Table currentTable;
}
