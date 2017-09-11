 package com.enation.app.shop.core.taglib.goodssearch;

 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import freemarker.core.Environment;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.io.Writer;
 import java.util.Map;






 public class GoodsSearchDirectiveModel
   implements TemplateDirectiveModel
 {
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Object obj = params.get("fieldname");
     if (obj == null) {
       obj = "goodsids";
     }


     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.setPageName("goods_search");
     freeMarkerPaser.putData("fieldname", obj);
     if (obj != null) {
       freeMarkerPaser.putData("showResult", obj);
     }
     String html = freeMarkerPaser.proessPageContent();
     env.getOut().write(html);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\taglib\goodssearch\GoodsSearchDirectiveModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */