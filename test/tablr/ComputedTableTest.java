package tablr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tablr.sql.SQLManager;

import static org.junit.jupiter.api.Assertions.*;

class ComputedTableTest {

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

        for (int i = 1; i < 11; i++) {
            mng.addRow(1);
            mng.setCellValue(1,1,i,String.valueOf(i));
        }
        mng.setTableName(2, "computed");

        mng.setQuery(2, "SELECT s.column AS c FROM stored AS s " +
                "WHERE s.column > 5");
    }

    @Test
    void test() {
        System.out.println(mng.getCellValue(2, 1, 1));
    }
}