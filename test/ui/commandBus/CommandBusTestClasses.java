package ui.commandBus;

import tablr.TablesHandler;
import ui.WindowCompositor;
import ui.commands.pushCommands.PushCommand;
import ui.commands.UpdateCommand;
import ui.commands.pushCommands.postCommands.PostCommand;

import java.util.ArrayList;

/**
 * Class used in CommandBusTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class TestUpCommand extends UpdateCommand
{

    @Override
    public void update() {

    }

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


}



/**
 * Class used in CommandBusTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class TestCommand extends PushCommand
{

    @Override
    public void execute() {

    }

    @Override
    public Boolean getReturn() {
        return true;
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
    public TestWindowCompositor() {
        super(new CommandBus());
    }

    @Subscribe
    public void valid(PushCommand c)
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
    public void val(PostCommand u)
    {

    }

    @Override
    public ArrayList<Integer> getTableIds() {
        return super.getTableIds();
    }
}
