package ui.widget;


import ui.commands.UICommand;
import ui.commands.UpdateCommand;

import java.awt.*;
import java.lang.String;
import java.util.function.Function;

public class LabelWidget extends Widget{

    protected String text;
    protected UpdateCommand command;
    protected Function<Integer, String> refreshText; //TODO: uicommand van maken


    /**
     * Offset between the text of the label and the border.
     */
    private static final int OFFSET = 5;


    /**
     * Widget displaying a static piece of text
     *
     * @param text the displayed string
     * @param x x-coordinate of text
     * @param y y-coordinate of text
     */
    public LabelWidget(int x, int y, int width, int height, boolean border, String text) {
        super(x,y,width,height,border);
        this.text = text;
    }

    public LabelWidget(int x, int y, int width, int height, boolean border) {
        super(x,y,width,height,border);
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setGetHandler(UpdateCommand command) {
        assert(this.command == null);
        this.command = command;
        command.update();
    }

    /**
     * Paints screen.
     *
     * @param g java.awt.Graphics object, offers the
     *          methods that allow you to paint on the canvas
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Rectangle oldRect = g.getClipBounds();
        Rectangle intersection = g.getClipBounds().intersection(new Rectangle(getX(),getY(),getWidth()+1,getHeight()+1));
        if (!intersection.isEmpty()) {
            g.setClip(intersection);
        }
        g.drawString(text, getX() + OFFSET, getY() + getHeight() - OFFSET);
        g.setClip(oldRect);
    }


}
