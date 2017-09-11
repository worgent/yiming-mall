 package com.enation.framework.directive;

 import com.enation.framework.pager.AjaxPagerDirectiveModel;
 import com.enation.framework.pager.PagerDirectiveModel;
 import freemarker.template.TemplateDirectiveModel;
 import java.util.HashMap;
 import java.util.Map;










 public class DirectiveFactory
 {
   private static Map<String, TemplateDirectiveModel> directiveMap;

   public static Map<String, TemplateDirectiveModel> getCommonDirective()
   {
     if (directiveMap == null)
     {
       directiveMap = new HashMap(9);





       TemplateDirectiveModel dateformate = new DateformateDirective();
       directiveMap.put("dateformat", dateformate);






       TemplateDirectiveModel resource = new ResourceDirectiveModel();
       directiveMap.put("resource", resource);





       TemplateDirectiveModel script = new ScriptDirectiveModel();
       directiveMap.put("script", script);





       TemplateDirectiveModel css = new CssDirectiveModel();
       directiveMap.put("css", css);





       TemplateDirectiveModel image = new ImageDirectiveModel();
       directiveMap.put("image", image);





       TemplateDirectiveModel ajaxpager = new AjaxPagerDirectiveModel();
       directiveMap.put("ajaxpager", ajaxpager);






       TemplateDirectiveModel pager = new PagerDirectiveModel();
       directiveMap.put("pager", pager);





       TemplateDirectiveModel imgurl = new ImageUrlDirectiveModel();
       directiveMap.put("imgurl", imgurl);






       TemplateDirectiveModel substring = new SubStringDirectiveModel();
       directiveMap.put("substring", substring);
     }




     return directiveMap;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\directive\DirectiveFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */