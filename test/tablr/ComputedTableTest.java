package tablr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tablr.sql.SQLManager;

import static org.junit.jupiter.api.Assertions.*;

class ComputedTableTest {

    private Table computedTable;
    private Table storedTable;
    private TableManager mng;
    private SQLManager sqlMng;

    @BeforeEach
    void setUp() {
        mng = new TableManager();
        sqlMng = new SQLManager(mng);
        mng.addTable();
        mng.addTable();

        mng.setTableName(1, "stored");

        mng.addColumn(1);
        mng.setColumnName(1, 1, "column");
        mng.setColumnType(1,1, "Integer");

        mng.addRow(1);
        mng.setCellValue(1,1,1,"1");

        mng.setTableName(2, "computed");

        mng.setQuery(2, "SELECT stored.column AS storedColumn FROM stored AS stored " +
                "WHERE stored.column = 1");
    }

    @Test
    void test() {

    }
}