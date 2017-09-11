 package com.enation.eop.sdk.webapp.taglib.html.support;

 import com.enation.eop.sdk.webapp.bean.Grid;
 import com.enation.eop.sdk.webapp.taglib.IListTaglibParam;
 import com.enation.framework.database.Page;
 import java.util.ArrayList;
 import java.util.List;
 import javax.servlet.ServletRequest;
 import javax.servlet.jsp.PageContext;

 public class GridBodyProvider implements com.enation.eop.sdk.webapp.taglib.IListTaglibProvider
 {
   public List getData(IListTaglibParam _param, PageContext pageContext)
   {
     GridBodyParam param = (GridBodyParam)_param;
     String from = param.getFrom();

     Object obj = pageContext.getAttribute(from);
     if (obj == null) {
       obj = pageContext.getRequest().getAttribute(from);
       if (obj == null) {
         return new ArrayList();
       }
     }

     Page page = null;
     List list = null;
     if ((obj instanceof Page)) {
       page = (Page)obj;
       list = (List)page.getResult();
     }
     else if ((obj instanceof Grid)) {
       page = ((Grid)obj).getWebpage();
       list = (List)page.getResult();
     } else if ((obj instanceof List)) {
       list = (List)obj;
     }

     return list;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\webapp\taglib\html\support\GridBodyProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */