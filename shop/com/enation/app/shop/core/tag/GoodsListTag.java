 package com.enation.app.shop.core.tag;

 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;



















 @Component
 @Scope("prototype")
 public class GoodsListTag
   extends BaseFreeMarkerTag
 {
   private IGoodsCatManager goodsCatManager;
   private IGoodsManager goodsManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     String catid = (String)params.get("catid");
     String tagid = (String)params.get("tagid");
     String goodsnum = (String)params.get("goodsnum");
     String uri;
     if ((catid == null) || (catid.equals(""))) {
       uri = ThreadContextHolder.getHttpRequest().getServletPath();
     }


     List goodsList = this.goodsManager.listGoods(catid, tagid, goodsnum);

     return goodsList;
   }

   public IGoodsCatManager getGoodsCatManager()
   {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) { this.goodsCatManager = goodsCatManager; }

   public IGoodsManager getGoodsManager() {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) { this.goodsManager = goodsManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\GoodsListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */