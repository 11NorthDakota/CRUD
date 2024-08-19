package by.northdakota.Utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {
    private final static String JSP_FORMAT = "/WEB-INF/jsp/%s.jsp";
    public String getPath(String nameJSP) {
        return JSP_FORMAT.formatted(nameJSP);
    }
}
