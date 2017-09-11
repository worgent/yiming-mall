package freemarker.ext.jsp;


import javax.servlet.ServletContext;
import javax.servlet.jsp.JspApplicationContext;

/**
 * @author Attila Szegedi
 * @version $Id: $
 */
class FreeMarkerJspFactory2 extends FreeMarkerJspFactory
{
    protected String getSpecificationVersion() {
        return "2.0";
    }

    @Override
    public JspApplicationContext getJspApplicationContext(ServletContext servletContext) {
        return null;
    }
}