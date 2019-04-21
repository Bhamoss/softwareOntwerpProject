package ui.widget;

import java.util.LinkedList;
import java.util.function.Consumer;

public class SelectorColumnWidget extends ColumnWidget {

    //private LinkedList<CheckBoxWidget> checkBoxWidgets;
    private LinkedList<Integer> ids;

    public SelectorColumnWidget(int x, int y, int width, int height, String name, boolean resizable, boolean visible, Consumer<Integer> onResize) {
        super(x,y,width,height,name,resizable,visible,onResize);
        //checkBoxWidgets = new LinkedList<>();
        ids = new LinkedList<>();
    }

    @Override
    public void addRow(int id) {
        CheckBoxWidget w = new CheckBoxWidget((Boolean toggle)->
            unCheckAll()
        );
        super.addWidget(w);
        ids.add(id);
    }


    @Override
    public void addWidget(Widget w) {
        if (widgets.size() == 0) {
            super.addWidget(w);
        }
    }

//    private void toggleRow(int id, boolean toggle){
//        unCheckAll();
//        for (int i = 0; i < ids.size(); i++){
//            CheckBoxWidget w = (CheckBoxWidget)widgets.get(i);
//            if (ids.get(i) == id){
//                w.setChecked(toggle);
//                break;
//            }
//        }
//    }

    private void unCheckAll() {
        for (int i = 1; i < widgets.size(); i++) {
            CheckBoxWidget w = (CheckBoxWidget)widgets.get(i);
            w.forceUncheck();
        }
    }



    public int getSelectedId() {
        for (int i = 1; i < widgets.size(); i++) {
            CheckBoxWidget w = (CheckBoxWidget)widgets.get(i);
            if (w.isChecked()) {
                return ids.get(i);
            }
        }
        return -1;
    }

    @Override
    public CheckBoxWidget getLastAdded() {
        return (CheckBoxWidget) widgets.getLast();
    }

}
