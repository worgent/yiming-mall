package freemarker.ext.jsp;

import freemarker.template.TemplateModelException;

import javax.el.ELContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

/**
 * @author Attila Szegedi
 * @version $Id: FreeMarkerPageContext1.java,v 1.1.2.1 2006/07/08 14:45:34 ddekany Exp $
 */
class FreeMarkerPageContext1 extends FreeMarkerPageContext {

    private FreeMarkerPageContext1() throws TemplateModelException {
        super();
    }

    @Override
    public ExpressionEvaluator getExpressionEvaluator() {
        return null;
    }

    @Override
    public ELContext getELContext() {
        return null;
    }

    @Override
    public VariableResolver getVariableResolver() {
        return null;
    }

    static FreeMarkerPageContext create() throws TemplateModelException {
        return new FreeMarkerPageContext1();
    }

    public void include (String s, boolean b) {}

 }
