 package com.enation.app.cms.core.taglib;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.eop.sdk.webapp.taglib.BaseTaglibSupport;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.PageContext;

 public class ArticleFieldOutTaglib
   extends BaseTaglibSupport
 {
   public int doStartTag()
     throws JspException
   {
     Map article = (Map)this.pageContext.getAttribute("article");
     if (article != null) {
       List<DataField> fieldList = (List)this.pageContext.getAttribute("fieldList");
       if (fieldList != null) {
         for (DataField field : fieldList) {
           String name = field.getEnglish_name();
           Object value = article.get(name);
           print("<td>" + value + "</td>");
         }
       }
     }


     return 1;
   }


   public int doAfterBody()
   {
     return 6;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\taglib\ArticleFieldOutTaglib.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */