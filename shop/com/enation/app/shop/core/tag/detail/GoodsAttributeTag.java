 package com.enation.app.shop.core.tag.detail;

 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;






















 @Component
 @Scope("prototype")
 public class GoodsAttributeTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = getRequest();
     Map goods = (Map)request.getAttribute("goods");
     if (goods == null) { throw new TemplateModelException("调用商品属性标签前，必须先调用商品基本信息标签");
     }
     return goods.get("propMap");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\detail\GoodsAttributeTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */