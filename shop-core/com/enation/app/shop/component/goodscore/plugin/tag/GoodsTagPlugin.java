 package com.enation.app.shop.component.goodscore.plugin.tag;

 import com.enation.app.shop.core.model.Tag;
 import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
 import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
 import com.enation.app.shop.core.service.ITagManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;
















 @Component
 public class GoodsTagPlugin
   extends AbstractGoodsPlugin
   implements IGoodsTabShowEvent
 {
   private ITagManager tagManager;

   public void addTabs() {}

   public String getAddHtml(HttpServletRequest request)
   {
     List<Tag> taglist = this.tagManager.list();
     FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
     freeMarkerPaser.setPageName("tag");
     freeMarkerPaser.putData("tagList", taglist);
     return freeMarkerPaser.proessPageContent();
   }


   public String getEditHtml(Map goods, HttpServletRequest request)
   {
     List<Tag> taglist = this.tagManager.list();

     Integer goods_id = Integer.valueOf(goods.get("goods_id").toString());
     List<Integer> tagIds = this.tagManager.list(goods_id);


     FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
     freeMarkerPaser.setPageName("tag");
     freeMarkerPaser.putData("tagList", taglist);
     freeMarkerPaser.putData("tagRelList", tagIds);
     return freeMarkerPaser.proessPageContent();
   }







   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
     throws RuntimeException
   {
     save(goods, request);
   }



   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
   {
     save(goods, request);
   }

   private void save(Map goods, HttpServletRequest request)
   {
     Integer goods_id = Integer.valueOf(goods.get("goods_id").toString());

     String[] tagstr = request.getParameterValues("tag_id");
     Integer[] tagids = null;
     if (tagstr != null) {
       tagids = new Integer[tagstr.length];
       for (int i = 0; i < tagstr.length; i++) {
         tagids[i] = Integer.valueOf(tagstr[i]);
       }
     }
     this.tagManager.saveRels(goods_id, tagids);
   }







   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {}







   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {}







   public String getAuthor()
   {
     return "kingapex";
   }

   public String getId()
   {
     return "goodstag";
   }

   public String getName()
   {
     return "商品标签";
   }

   public String getType()
   {
     return null;
   }

   public String getVersion()
   {
     return "1.0";
   }



   public void perform(Object... params) {}


   public void setTagManager(ITagManager tagManager)
   {
     this.tagManager = tagManager;
   }







   public String getTabName()
   {
     return "标签";
   }







   public int getOrder()
   {
     return 13;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\goodscore\plugin\tag\GoodsTagPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */