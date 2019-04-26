package ui.widget;


import ui.commandBus.CommandBus;
import ui.commands.UpdateCommand;

import java.awt.*;
import java.lang.String;
import java.util.function.Function;

public class LabelWidget extends Widget{

    protected String text;


    /**
     * Offset between the text of the label and the border.
     */
    private static final int OFFSET = 5;

    protected UpdateCommand getCommand;


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
        // For safety (if no get handler is initialized before painting)
        this.text = "";
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public void setGetHandler(UpdateCommand command, CommandBus bus) {
        if (getCommand != null)
            unsubscribe(bus);
        this.getCommand = command;
        bus.subscribe(command);
    }

    public void unsubscribe(CommandBus bus) {
        if (getCommand != null)
            bus.unsubscribe(getCommand);
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
