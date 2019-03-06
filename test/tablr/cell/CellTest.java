package tablr.cell;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @BeforeEach
    void setUp() {
        Cell testCell =  new BooleanCell();
    }

    @AfterEach
    void tearDown() {
    }
}