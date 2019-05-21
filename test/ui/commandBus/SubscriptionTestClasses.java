package ui.commandBus;

import ui.WindowCompositor;
import ui.commands.pushCommands.PushCommand;
import ui.commands.UpdateCommand;
import ui.commands.pushCommands.postCommands.PostCommand;

/**
 * Class used in SubscriptionTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class SubUpCommand extends UpdateCommand
{

    @Override
    public void update() {

    }

    //MUST STAY THE FIRST METHOD
    public void execute() {

    }

    // MUST STAY SECOND
    @Subscribe
    private void privMethod()
    {

    }

    // MUST STAY THIRD
    @Subscribe
    public void twoMethod(int i, int j)
    {

    }

    // MUST STAY FOURTH
    @Subscribe
    public void intMethod(int i)
    {

    }

    // MUST STAY FIFTH
    @Subscribe
    public void validMethod(SubCommand i)
    {
        this.testvar = i.t;
    }

    public int testvar = 0;

}

/**
 * Class used in SubscriptionTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class SubclassSubUpCommand extends SubUpCommand
{
    public int m = 1;
}

/**
 * Class used in SubscriptionTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class BrotherUpCommand extends UpdateCommand
{

    @Subscribe
    public void notSubWidgetMethod(PushCommand command)
    {

    }


    @Override
    public void update() {

    }
}

/**
 * Class used in SubscriptionTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class SubCommand extends PushCommand
{

    @Override
    public void execute() {

    }

    @Override
    public Boolean getReturn() {
        return null;
    }

    public int t = 69;
}

/**
 * Class used in SubscriptionTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class BrotherCommand extends PostCommand
{

    @Override
    public void execute() {

    }

    @Override
    public Boolean getReturn() {
        return null;
    }
}

/**
 * Class used in SubscriptionTest.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class SubWindowCompositor extends WindowCompositor
{

    public SubWindowCompositor() {
        super(new CommandBus());
    }

    @Subscribe
    public void valid(PushCommand c)
    {

    }
}