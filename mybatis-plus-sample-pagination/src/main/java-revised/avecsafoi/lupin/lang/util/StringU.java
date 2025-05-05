package avecsafoi.lupin.lang.util;

import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class StringU {
    
    public static final MultilineRecursiveToStringStyle MULTILINE_RECURSIVE_TO_STRING_STYLE = new MultilineRecursiveToStringStyle();
    
    protected StringU() {}
    
    public static String toString(Object o) { return ToStringBuilder.reflectionToString(o, MULTILINE_RECURSIVE_TO_STRING_STYLE); }
}
