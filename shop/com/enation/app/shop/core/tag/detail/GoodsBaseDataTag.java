 package com.enation.app.shop.core.tag.detail;

 import com.enation.app.shop.core.plugin.goods.GoodsDataFilterBundle;
 import com.enation.app.shop.core.plugin.goods.GoodsPluginBundle;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.eop.processor.core.UrlNotFoundException;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.ObjectNotFoundException;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.RequestUtil;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;
















 @Component
 @Scope("prototype")
 public class GoodsBaseDataTag
   extends BaseFreeMarkerTag
 {
   private IGoodsManager goodsManager;
   private GoodsDataFilterBundle goodsDataFilterBundle;
   private GoodsPluginBundle goodsPluginBundle;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     try
     {
       Integer goods_id = (Integer)params.get("goodsid");
       if ((goods_id == null) || (goods_id.intValue() == 0)) {
         goods_id = getGoodsId();
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


   private Integer getGoodsId()
   {
     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
     String url = RequestUtil.getRequestUrl(httpRequest);
     String goods_id = paseGoodsId(url);

     return Integer.valueOf(goods_id);
   }

   private static String paseGoodsId(String url) {
     String pattern = "(-)(\\d+)";
     String value = null;
     Pattern p = Pattern.compile(pattern, 34);
     Matcher m = p.matcher(url);
     if (m.find()) {
       value = m.group(2);
     }
     return value;
   }

   public GoodsDataFilterBundle getGoodsDataFilterBundle()
   {
     return this.goodsDataFilterBundle;
   }


   public void setGoodsDataFilterBundle(GoodsDataFilterBundle goodsDataFilterBundle)
   {
     this.goodsDataFilterBundle = goodsDataFilterBundle;
   }


   public IGoodsManager getGoodsManager()
   {
     return this.goodsManager;
   }


   public void setGoodsManager(IGoodsManager goodsManager)
   {
     this.goodsManager = goodsManager;
   }


   public GoodsPluginBundle getGoodsPluginBundle()
   {
     return this.goodsPluginBundle;
   }


   public void setGoodsPluginBundle(GoodsPluginBundle goodsPluginBundle)
   {
     this.goodsPluginBundle = goodsPluginBundle;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\detail\GoodsBaseDataTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */