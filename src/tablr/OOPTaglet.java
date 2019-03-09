package tablr;


import com.sun.javadoc.Tag;
import jdk.javadoc.doclet.Taglet;

import java.util.Map;
import java.util.regex.Pattern;

public abstract class OOPTaglet implements Taglet {
    static final Pattern SPLIT = Pattern.compile("\n\\s+\\|");
    private final String name;
    private final String header;
    private final boolean split;

    protected OOPTaglet(String name, String header, boolean split) {
        this.name = name;
        this.header = header;
        this.split = split;
    }

    protected static void register(Map tagletMap, Object tag) {
        String name;
        try {
            name = ((Taglet)tag).getName();
        } catch (ClassCastException var4) {
            name = ((Taglet)tag).getName();
        }

        if (tagletMap.get(name) != null) {
            tagletMap.remove(name);
        }

        tagletMap.put(name, tag);
    }

    public String getName() {
        return this.name;
    }

    public String toString(Tag tag) {
        return this.toString(tag, tag.text());
    }

    public String toString(Tag[] tags) {
        if (tags.length == 0) {
            return null;
        } else {
            String result = "";
            Tag tag;
            int var4;
            int var5;
            Tag[] var6;
            if (this.split) {
                var6 = tags;
                var5 = tags.length;

                for(var4 = 0; var4 < var5; ++var4) {
                    tag = var6[var4];
                    result = result + this.toString(tag);
                }
            } else {
                if (tags.length == 0) {
                    return result;
                }

                if (tags.length == 1) {
                    return this.toString(tags[0]);
                }

                result = result + this.getPluralHeader() + "<DD>";
                var6 = tags;
                var5 = tags.length;

                for(var4 = 0; var4 < var5; ++var4) {
                    tag = var6[var4];
                    result = result + "<li>" + this.getSingleTag(tag, tag.text()) + "</li>\n";
                }

                result = result + "</DD>";
            }

            return result;
        }
    }

    protected abstract String getFormattedInformal(Tag var1, String var2);

    private String getSingleTag(Tag tag, String tagtext) {
        String result = this.getFormattedInformal(tag, getInformalPart(tagtext));
        String[] formals = getFormalParts(tagtext);
        if (formals.length == 0) {
            return result;
        } else {
            String formal = "";
            String[] var9 = formals;
            int var8 = formals.length;

            for(int var7 = 0; var7 < var8; ++var7) {
                String f = var9[var7];
                f = "<br>    " + f;
                f = f.trim();
                formal = formal + f;
            }

            result = result + "<CODE><B>" + formal.replaceAll(" ", "&nbsp;") + "</B></CODE>";
            return result;
        }
    }

    private String toString(Tag tag, String tagtext) {
        return this.getHeader() + "<DD>" + this.getSingleTag(tag, tagtext) + "</DD>\n";
    }

    static String getInformalPart(String tagtext) {
        String temp = SPLIT.split(tagtext)[0];
        return temp.startsWith("|") ? "" : temp;
    }

    static String[] getFormalParts(String tagtext) {
        String inf = getInformalPart(tagtext);
        tagtext = tagtext.substring(inf.length(), tagtext.length());
        if (tagtext.equals("")) {
            return new String[0];
        } else {
            int index1 = tagtext.indexOf("|");
            tagtext = tagtext.substring(index1 + 1, tagtext.length());
            int nbFirstSpaces = countLeadingSpaces(tagtext);
            String[] result = SPLIT.split(tagtext);

            for(int i = 0; i < result.length; ++i) {
                result[i] = stripLeadingSpaces(result[i], nbFirstSpaces);
            }

            return result;
        }
    }

    static int countLeadingSpaces(String string) {
        int result;
        for(result = 0; string.indexOf(" ") == 0; string = string.substring(1, string.length())) {
            ++result;
        }

        return result;
    }

    static String stripLeadingSpaces(String string, int nbSpaces) {
        for(int count = nbSpaces; string.indexOf(" ") == 0 && count != 0; --count) {
            string = string.substring(1, string.length());
        }

        return string;
    }

    protected String getHeader() {
        return "<DT><B>" + this.header + ":" + "</B>";
    }

    protected String getPluralHeader() {
        return "<DT><B>" + this.header + "s:" + "</B>";
    }
}