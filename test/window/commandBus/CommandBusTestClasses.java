package window.commandBus;

import tablr.TablesHandler;
import window.WindowCompositor;
import window.widget.Widget;
import window.commands.UICommand;

import java.util.ArrayList;

/**
 * Class used in CommandBusTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class TestWidget extends Widget
{


    @Subscribe
    public void validMethod(TestCommand i)
    {
        this.testvar = i.t;
    }

    public void setTestvarNull()
    {
        testvar = 0;
    }


    public void setTestvarTen()
    {
        testvar = 10;
    }

    public int testvar = 0;

    TestWidget()
    {
        super(1,1,1,1,true);
    }
}



/**
 * Class used in CommandBusTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class TestCommand extends UICommand
{

    @Override
    public void execute() {

    }

    public int t = 69;
}

/**
 * Class used in CommandBusTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class SubTestCommand extends TestCommand
{
    @Override
    public void execute() {

    }
}


/**
 * Class used in CommandBusTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class TestWindowCompositor extends WindowCompositor
{
    @Subscribe
    public void valid(UICommand c)
    {

    }
}


/**
 * Class used in CommandBusTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class SubTablesHandler extends TablesHandler
{
    @Subscribe
    public void val(UICommand u)
    {

    }

    @Override
    public ArrayList<Integer> getTableIds() {
        return super.getTableIds();
    }
}
