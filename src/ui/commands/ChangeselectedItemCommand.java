package ui.commands;

import ui.UIHandler;
import ui.commandBus.CommandBus;
@Deprecated
public class ChangeselectedItemCommand extends UICommandWithReturn<Boolean> {

    public ChangeselectedItemCommand(Integer selectedItem, UIHandler uiHandler, CommandBus commandBus){

        this.selectedItem = selectedItem;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;

    }

    private final Integer selectedItem;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    public CommandBus getCommandBus() {
        return commandBus;
    }

    public Integer getSelectedItem() {
        return selectedItem;
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }
    @Override
    public void execute() {
        getCommandBus().post(this);
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
