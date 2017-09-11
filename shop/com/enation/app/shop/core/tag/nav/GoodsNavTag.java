 package com.enation.app.shop.core.tag.nav;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;











 @Component
 @Scope("prototype")
 public class GoodsNavTag
   extends BaseFreeMarkerTag
 {
   private IGoodsCatManager goodsCatManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Map goods = (Map)ThreadContextHolder.getHttpRequest().getAttribute("goods");
     if (goods == null) { throw new RuntimeException("参数显示挂件必须和商品详细显示挂件同时存在");
     }
     StringBuffer navHtml = new StringBuffer();
     int catstr = ((Integer)goods.get("cat_id")).intValue();
     Integer brandstr = (Integer)goods.get("brand_id");

     String catname = this.goodsCatManager.getById(catstr).getName();
     String brandname = (String)goods.get("brand_name");
     Map result = new HashMap();

     navHtml.append("<span><a href=\"index.html\">首页</a></span>&gt;");


     navHtml.append("<span><a href='search-cat-");
     navHtml.append(catstr);
     navHtml.append(".html'>");
     navHtml.append(catname);
     navHtml.append("</a></span>");

     navHtml.append("&gt;");

     if (brandstr != null)
     {
       navHtml.append("<span><a href='search-cat-");
       navHtml.append(catstr);
       navHtml.append("-");
       navHtml.append(brandstr);
       navHtml.append(".html'>");
       navHtml.append(brandname);
       navHtml.append("</a></span>&gt;");
     }

     navHtml.append("<span class=\"last\">");
     navHtml.append(goods.get("name"));
     navHtml.append("</span>");

     result.put("navHtml", navHtml);
     return result;
   }


   public IGoodsCatManager getGoodsCatManager()
   {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) { this.goodsCatManager = goodsCatManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\nav\GoodsNavTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */