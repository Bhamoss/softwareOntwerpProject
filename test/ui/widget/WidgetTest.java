package ui.widget;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WidgetTest {

    Widget w300; // Widget with all of the values (x,y,w,h) = 100
    Widget wx0y0wh200; // Widget with (x,y) = (0,0) and w,h = 200

    @BeforeEach
    void setup() {
        w300 = new Widget(300,300,300,300,true);
        wx0y0wh200 = new Widget(200,200,true);
    }

    @Test
    void setX() {
        w300.setX(200);
        assertEquals(200, w300.getX());
    }

    @Test
    void setY() {
        w300.setY(200);
        assertEquals(200, w300.getY());
    }

    @Test
    void setWidth() {
        w300.setWidth(200);
        assertEquals(200, w300.getWidth());
    }

    @Test
    void setHeight() {
        w300.setHeight(200);
        assertEquals(200, w300.getHeight());
    }

    @Test
    void setPosition() {
        w300.setPosition(200,100);
        assertEquals(200, w300.getX());
        assertEquals(100, w300.getY());
    }
    @Test
    void containsPoint() {
        // eerst hoekpunten testen
        assertTrue(w300.containsPoint(300,300));
        assertTrue(w300.containsPoint(300,600));
        assertTrue(w300.containsPoint(600,300));
        assertTrue(w300.containsPoint(600,600));
        // random punt erin testen
        assertTrue(w300.containsPoint(301,569));
        // invalid punten testen
        assertFalse(w300.containsPoint(299, 300));
        assertFalse(w300.containsPoint(569, 900));


        // eerst hoekpunten testen
        assertTrue(wx0y0wh200.containsPoint(0,0));
        assertTrue(wx0y0wh200.containsPoint(200,0));
        assertTrue(wx0y0wh200.containsPoint(0,200));
        assertTrue(wx0y0wh200.containsPoint(200,200));
        // random punt erin testen
        assertTrue(wx0y0wh200.containsPoint(189,135));
        // invalid punten testen
        assertFalse(wx0y0wh200.containsPoint(200, 201));
        assertFalse(wx0y0wh200.containsPoint(900, 100));
    }


    @Test
    void handleKeyEvent() {
        assertFalse(w300.handleMouseEvent(502,200,200,1));
    }

    @Test
    void handleMouseEvent() {
        assertFalse(w300.handleMouseEvent(502,200,200,1));
    }


    @Test
    void update() {
    }

}