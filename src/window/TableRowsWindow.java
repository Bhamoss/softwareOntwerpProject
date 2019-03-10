package window;

import tablr.TableDesignHandler;
import tablr.TableRowsHandler;
import window.widget.ButtonWidget;
import window.widget.EditorWidget;
import window.widget.Widget;

import java.util.LinkedList;
import java.util.List;

public class TableRowsWindow {

    public TableRowsWindow(UIWindowHandler uiWindowHandler){
        this.uiWindowHandler = uiWindowHandler;

    }

    private final UIWindowHandler uiWindowHandler;

    public UIWindowHandler getUiWindowHandler() {
        return uiWindowHandler;
    }

    public LinkedList<Widget> getLayout(TableRowsHandler tableRowsHandler){
        LinkedList<Widget> layout = new LinkedList<>();
        int y = 0;


        return layout;
    }

}
