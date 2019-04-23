package ui.widget;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.MouseEvent;


import static org.junit.jupiter.api.Assertions.*;

class SubWindowWidgetTest {

    SubWindowWidget swEmpty, swFilled;
    ButtonWidget btn;
    LabelWidget label;

    @BeforeEach
    void setUp() {
        swEmpty = new SubWindowWidget(0,0,200,200,true, "emptySW");

        swFilled = new SubWindowWidget(300,300,300,300,true, "filledSW");
        //btn = new ButtonWidget(0,0, 100,100, true, "Button", x->{btn.setText("Changed"); return false;});
        label = new LabelWidget(250, 250, 100,100,true,"Label");
        swFilled.addWidget(btn);
        swFilled.addWidget(label);
    }

    @Test
    void onTitle() {
        assertTrue(swEmpty.onTitle(0,SubWindowWidget.getTitleHeight()));
        assertTrue(swEmpty.onTitle(150, SubWindowWidget.getTitleHeight()));
        assertTrue(swEmpty.onTitle(100, 10));
        assertFalse(swEmpty.onTitle(0, SubWindowWidget.getTitleHeight() + 1));

        assertTrue(swFilled.onTitle(300,SubWindowWidget.getTitleHeight() + 300));
        assertTrue(swFilled.onTitle(525, SubWindowWidget.getTitleHeight()+300));
        assertTrue(swFilled.onTitle(400, 310));
        assertFalse(swFilled.onTitle(300, SubWindowWidget.getTitleHeight() + 301));
    }

    @Test
    void onCloseBtn() {
        assertTrue(swEmpty.onCloseBtn(150,SubWindowWidget.getTitleHeight()));
        assertTrue(swEmpty.onCloseBtn(200, SubWindowWidget.getTitleHeight()));
        assertTrue(swEmpty.onCloseBtn(175, 10));
        assertFalse(swEmpty.onCloseBtn(150, SubWindowWidget.getTitleHeight() + 1));

        assertTrue(swFilled.onCloseBtn(525,SubWindowWidget.getTitleHeight() + 300));
        assertTrue(swFilled.onCloseBtn(600, SubWindowWidget.getTitleHeight()+300));
        assertTrue(swFilled.onCloseBtn(550, 310));
        assertFalse(swFilled.onCloseBtn(525, SubWindowWidget.getTitleHeight() + 301));
    }

    @Test
    void getTotalHeight() {
        assertEquals(0, swEmpty.getTotalHeight());
        assertEquals(350 + SubWindowWidget.getMarginTop() - SubWindowWidget.getMarginBottom(),
                            swFilled.getTotalHeight()); // label is het meest naar onderen: y = 250, height = 100
    }

    @Test
    void getTotalWidth() {
        assertEquals(0, swEmpty.getTotalWidth());
        assertEquals(350, swFilled.getTotalWidth()); // label is het meest rechtse: x = 250, width = 100
    }

    @Test
    void addWidget() {
        // addwidget werd in setup al gebruikt, hier wordt getest of de widgets effectief op de juiste plaats staan
        assertEquals(swFilled.getY() + SubWindowWidget.getMarginTop(), btn.getY() );
        assertEquals(swFilled.getX() + SubWindowWidget.getMarginLeft(), btn.getX());

        assertEquals(250 + swFilled.getY() + SubWindowWidget.getMarginTop(), label.getY() );
        assertEquals(250 + swFilled.getX() + SubWindowWidget.getMarginLeft(), label.getX() );

    }

    @Test
    void handleMouseEventCloseButton() {
        assertTrue(swEmpty.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 175, 10, 1));
        assertFalse(swEmpty.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 100, 100, 1));
    }

    @Test
    void setPosition() {
        // setX and setY worden hier indirect ook getest, setPosition called die twee in principe gewoon

        // TEST ALS TITLE AND CLOSEBTN MEEVERPLAATSEN
        swEmpty.setPosition(900, 100);
        assertEquals(900, swEmpty.getX());
        assertEquals(100, swEmpty.getY());
        // title mee verplaatst
        assertTrue(swEmpty.onTitle(900,SubWindowWidget.getTitleHeight() + 100));
        assertTrue(swEmpty.onTitle(1050, SubWindowWidget.getTitleHeight() + 100));
        assertTrue(swEmpty.onTitle(1000, 110));
        assertFalse(swEmpty.onTitle(900, SubWindowWidget.getTitleHeight() + 101));
        // btn mee verplaatst
        assertTrue(swEmpty.onCloseBtn(1050,SubWindowWidget.getTitleHeight()+100));
        assertTrue(swEmpty.onCloseBtn(1100, SubWindowWidget.getTitleHeight()+100));
        assertTrue(swEmpty.onCloseBtn(1075, 110));
        assertFalse(swEmpty.onCloseBtn(1050, SubWindowWidget.getTitleHeight() + 101));


        // TEST ALS DE WIDGETS INSIDE MEEVERPLAATSEN
        swFilled.setPosition(400,400);
        // btn mee verplaatst
        assertEquals(400 + SubWindowWidget.getMarginTop(), btn.getY() );
        assertEquals(400 + SubWindowWidget.getMarginLeft(), btn.getX());
        // label mee verplaatst
        assertEquals(250 + 400 + SubWindowWidget.getMarginTop(), label.getY() );
        assertEquals(250 + 400 + SubWindowWidget.getMarginLeft(), label.getX() );
    }

    @Test
    void resizeWidth() {
        // Set width of swEmpty, check if title and closeBtn change
        swEmpty.resizeWidth(400);
        // check width sw
        assertEquals(400, swEmpty.getWidth());
        // check width title
        assertTrue(swEmpty.onTitle(0,SubWindowWidget.getTitleHeight()));
        assertTrue(swEmpty.onTitle(300, SubWindowWidget.getTitleHeight()));
        assertTrue(swEmpty.onTitle(200, 10));
        assertFalse(swEmpty.onTitle(0, SubWindowWidget.getTitleHeight() + 1));
        // check width closeBtn
        assertTrue(swEmpty.onCloseBtn(300,SubWindowWidget.getTitleHeight()));
        assertTrue(swEmpty.onCloseBtn(400, SubWindowWidget.getTitleHeight()));
        assertTrue(swEmpty.onCloseBtn(350, 10));
        assertFalse(swEmpty.onCloseBtn(300, SubWindowWidget.getTitleHeight() + 1));

        // Set width of swEmpty, check if title and closeBtn change
        swEmpty.resizeWidth(199);
        // check width sw --> niets verandert normaal
        assertEquals(400, swEmpty.getWidth());
        // check width title --> niets verandert normaal
        assertTrue(swEmpty.onTitle(0,SubWindowWidget.getTitleHeight()));
        assertTrue(swEmpty.onTitle(300, SubWindowWidget.getTitleHeight()));
        assertTrue(swEmpty.onTitle(200, 10));
        assertFalse(swEmpty.onTitle(0, SubWindowWidget.getTitleHeight() + 1));
        // check width closeBtn --> niets verandert normaal
        assertTrue(swEmpty.onCloseBtn(300,SubWindowWidget.getTitleHeight()));
        assertTrue(swEmpty.onCloseBtn(400, SubWindowWidget.getTitleHeight()));
        assertTrue(swEmpty.onCloseBtn(350, 10));
        assertFalse(swEmpty.onCloseBtn(300, SubWindowWidget.getTitleHeight() + 1));
    }


}