 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.shop.core.plugin.goods.GoodsDataFilterBundle;
 import com.enation.app.shop.core.plugin.goods.GoodsPluginBundle;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.eop.processor.core.UrlNotFoundException;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.ObjectNotFoundException;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;

 @Component
 public class B2b2cGoodsBaseDataTag extends BaseFreeMarkerTag
 {
   private IGoodsManager goodsManager;
   private GoodsDataFilterBundle goodsDataFilterBundle;
   private GoodsPluginBundle goodsPluginBundle;

   protected Object exec(Map params) throws TemplateModelException
   {
     try
     {
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       Integer goods_id = Integer.valueOf(Integer.parseInt(request.getParameter("goodsid")));
       if (goods_id == null) {
         throw new UrlNotFoundException();
       }
       Map goodsMap = this.goodsManager.get(goods_id);




       if (goodsMap == null) {
         throw new UrlNotFoundException();
       }



       if (goodsMap.get("market_enable").toString().equals("0")) {
         throw new UrlNotFoundException();
       }



       if (goodsMap.get("disabled").toString().equals("1")) {
         throw new UrlNotFoundException();
       }

       List<Map> goodsList = new ArrayList();
       goodsList.add(goodsMap);
       this.goodsDataFilterBundle.filterGoodsData(goodsList);

       getRequest().setAttribute("goods", goodsMap);
       this.goodsPluginBundle.onVisit(goodsMap);

       return goodsMap;
     }
     catch (ObjectNotFoundException e) {
       throw new UrlNotFoundException();
     }
   }

   public IGoodsManager getGoodsManager() { return this.goodsManager; }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }

   public GoodsDataFilterBundle getGoodsDataFilterBundle() { return this.goodsDataFilterBundle; }

   public void setGoodsDataFilterBundle(GoodsDataFilterBundle goodsDataFilterBundle) {
     this.goodsDataFilterBundle = goodsDataFilterBundle;
   }

   public GoodsPluginBundle getGoodsPluginBundle() { return this.goodsPluginBundle; }

   public void setGoodsPluginBundle(GoodsPluginBundle goodsPluginBundle) {
     this.goodsPluginBundle = goodsPluginBundle;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\B2b2cGoodsBaseDataTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */