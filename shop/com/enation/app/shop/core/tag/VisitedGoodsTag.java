 package com.enation.app.shop.core.tag;

 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.directive.ImageUrlDirectiveModel;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;












 @Component
 @Scope("prototype")
 public class VisitedGoodsTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     WebSessionContext sessionContext = ThreadContextHolder.getSessionContext();
     List<Map> visitedGoods = (List)sessionContext.getAttribute("visitedGoods");
     if (visitedGoods == null) visitedGoods = new ArrayList();
     Map result = new HashMap();
     result.put("visitedGoods", visitedGoods);
     result.put("GoodsPic", new ImageUrlDirectiveModel());
     return result;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\VisitedGoodsTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */