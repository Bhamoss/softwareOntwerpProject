package window.commandBus;

import window.WindowCompositor;
import window.widget.Widget;
import window.commands.UICommand;

import java.awt.*;


class SubWidget extends Widget
{

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

    SubWidget()
    {
        super(1,1,1,1,true);
    }
}

class SubclassSubWidget extends SubWidget
{
    public int m = 1;
}

class BrotherWidget extends Widget
{

    @Subscribe
    public void notSubWidgetMethod(UICommand command)
    {

    }

    BrotherWidget()
    {
        super(1,1,1,1,true);
    }
}

class SubCommand extends UICommand
{

    @Override
    public void execute() {

    }

    public int t = 69;
}

class BrotherCommand extends UICommand
{

    @Override
    public void execute() {

    }

}

class SubWindowCompositor extends WindowCompositor
{
    @Subscribe
    public void valid(UICommand c)
    {

    }
}