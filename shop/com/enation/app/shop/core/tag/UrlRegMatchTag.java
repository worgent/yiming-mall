 package com.enation.app.shop.core.tag;

 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;










 @Component
 @Scope("prototype")
 public class UrlRegMatchTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     String reg = (String)params.get("reg");

     HttpServletRequest request = getRequest();
     String url = request.getRequestURI();

     if (StringUtil.isEmpty(url)) {
       return null;
     }

     Pattern p = Pattern.compile(reg, 34);
     Matcher m = p.matcher(url);
     if (m.find()) {
       return m.group();
     }
     return null;
   }



   public static void main(String[] args)
   {
     Pattern p = Pattern.compile("(\\d+)", 34);
     Matcher m = p.matcher("/21-goods-12.html");
     if (m.find())
     {

       int count = m.groupCount();
       String[] ar = new String[count];
       for (int i = 0; i <= count; i++) {}
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\UrlRegMatchTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */