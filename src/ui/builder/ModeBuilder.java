package ui.builder;

import ui.widget.ComponentWidget;

public abstract class ModeBuilder {

    public abstract Boolean canRebuild(ComponentWidget componentWidget);

    public abstract ComponentWidget rebuild(ComponentWidget componentWidget);
}
