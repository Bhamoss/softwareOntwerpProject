package tablr;

import com.sun.source.doctree.DocTree;
import tablr.OOPTaglet;
import com.sun.javadoc.Tag;

import javax.lang.model.element.Element;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ResponsibilityTaglet extends OOPTaglet {
    private static final String NAME = "resp";
    private static final String HEADER = "Responsibility";

    public ResponsibilityTaglet() {
        super("resp", "Responsibility", false);
    }

    public static void register(Map tagletMap) {
        OOPTaglet.register(tagletMap, new ResponsibilityTaglet());
    }

    public boolean inField() {
        return false;
    }

    public boolean inConstructor() {
        return false;
    }

    public boolean inMethod() {
        return false;
    }

    public boolean inOverview() {
        return false;
    }

    public boolean inPackage() {
        return false;
    }

    public boolean inType() {
        return true;
    }

    @Override
    public Set<Location> getAllowedLocations() {
        return null;
    }

    public boolean isInlineTag() {
        return false;
    }

    @Override
    public String toString(List<? extends DocTree> list, Element element) {
        return null;
    }

    protected String getFormattedInformal(Tag tag, String informalText) {
        return informalText;
    }
}